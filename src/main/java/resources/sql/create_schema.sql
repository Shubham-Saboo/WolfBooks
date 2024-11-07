DROP SCHEMA IF EXISTS WolfBooks;
CREATE SCHEMA WolfBooks;
USE WolfBooks;

-- Drop tables in reverse order of dependencies
DROP TABLE IF EXISTS StudentGrades;
DROP TABLE IF EXISTS TeachingAssistants;
DROP TABLE IF EXISTS Questions;
DROP TABLE IF EXISTS StudentActivities;
DROP TABLE IF EXISTS Activities;
DROP TABLE IF EXISTS Blocks;
DROP TABLE IF EXISTS Sections;
DROP TABLE IF EXISTS Chapters;
DROP TABLE IF EXISTS Enrollments;
DROP TABLE IF EXISTS Notifications;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Textbooks;
DROP PROCEDURE IF EXISTS DeleteBlockAndActivity;

CREATE TABLE IF NOT EXISTS Textbooks (
    textbook_id VARCHAR(50),
    textbook_title VARCHAR(255) NOT NULL,
    PRIMARY KEY (textbook_id)
);

CREATE TABLE IF NOT EXISTS Users (
    user_id VARCHAR(50) PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_role VARCHAR(20) NOT NULL CHECK (user_role IN ('student', 'faculty', 'teaching_assistant', 'admin')),
    first_login BOOLEAN DEFAULT FALSE,
    INDEX (email)
);

CREATE TABLE IF NOT EXISTS Courses (
   course_id VARCHAR(50) PRIMARY KEY,
   course_title VARCHAR(255) NOT NULL,
   faculty_id VARCHAR(50),
   start_date DATE,
   end_date DATE,
   course_type VARCHAR(20) NOT NULL CHECK (course_type IN ('Active', 'Evaluation')),
   capacity INT DEFAULT 0,
   token VARCHAR(7),
   textbook_id VARCHAR(50),
   INDEX (faculty_id),
   INDEX (textbook_id),
   FOREIGN KEY (textbook_id) REFERENCES Textbooks(textbook_id) ON DELETE CASCADE,
   FOREIGN KEY (faculty_id) REFERENCES Users(user_id)
);

CREATE TABLE IF NOT EXISTS Notifications (
    notification_id VARCHAR(50),
    content TEXT NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (user_id, notification_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE IF NOT EXISTS Enrollments (
    user_id VARCHAR(50),
    course_id VARCHAR(50),
    user_status VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, course_id),
    INDEX (course_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

CREATE TABLE IF NOT EXISTS Chapters (
    chapter_id VARCHAR(50),
    chapter_title VARCHAR(255) NOT NULL,
    textbook_id VARCHAR(50),
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(50) NOT NULL,
    PRIMARY KEY(textbook_id, chapter_id),
    INDEX (chapter_id),
    FOREIGN KEY (textbook_id) REFERENCES Textbooks(textbook_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Sections (
    section_id VARCHAR(50),
    textbook_id VARCHAR(50),
    section_title VARCHAR(255) NOT NULL,
    chapter_id VARCHAR(50),
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(50) NOT NULL,
    PRIMARY KEY (textbook_id, chapter_id, section_id),
    INDEX (section_id),
    INDEX (chapter_id),
    INDEX (textbook_id),
    FOREIGN KEY (textbook_id, chapter_id) REFERENCES Chapters(textbook_id, chapter_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Blocks (
    section_id VARCHAR(50),
    textbook_id VARCHAR(50),
    block_id VARCHAR(50),
    chapter_id VARCHAR(50),
    content_type VARCHAR(50),
    content VARCHAR(500),
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(50) NOT NULL,
    PRIMARY KEY(textbook_id, chapter_id, section_id, block_id),
    INDEX (section_id),
    INDEX (chapter_id),
    INDEX (block_id),
    FOREIGN KEY (textbook_id, chapter_id, section_id)
        REFERENCES Sections(textbook_id, chapter_id, section_id) ON DELETE CASCADE,
    FOREIGN KEY (textbook_id, chapter_id)
        REFERENCES Chapters(textbook_id, chapter_id) ON DELETE CASCADE,
    FOREIGN KEY (textbook_id)
        REFERENCES Textbooks(textbook_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Activities (
    activity_id VARCHAR(50),
    section_id VARCHAR(50),
    textbook_id VARCHAR(50),
    chapter_id VARCHAR(50),
    block_id VARCHAR(50),
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(50) NOT NULL,
    PRIMARY KEY(textbook_id, chapter_id, section_id, block_id, activity_id),
    INDEX (activity_id),
    INDEX (block_id),
    INDEX (section_id),
    FOREIGN KEY (textbook_id, chapter_id, section_id, block_id)
        REFERENCES Blocks(textbook_id, chapter_id, section_id, block_id) ON DELETE CASCADE,
    FOREIGN KEY (textbook_id, chapter_id, section_id)
        REFERENCES Sections(textbook_id, chapter_id, section_id) ON DELETE CASCADE,
    FOREIGN KEY (textbook_id, chapter_id)
        REFERENCES Chapters(textbook_id, chapter_id) ON DELETE CASCADE,
    FOREIGN KEY (textbook_id)
        REFERENCES Textbooks(textbook_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Questions (
                                         textbook_id VARCHAR(50),
                                         chapter_id VARCHAR(50),
                                         section_id VARCHAR(50),
                                         block_id VARCHAR(50),
                                         activity_id VARCHAR(50),
                                         question_id VARCHAR(50),
                                         question VARCHAR(255) NOT NULL,
                                         explanation_one VARCHAR(255) NOT NULL,
                                         explanation_two VARCHAR(255) NOT NULL,
                                         explanation_three VARCHAR(255) NOT NULL,
                                         explanation_four VARCHAR(255) NOT NULL,
                                         answer_one VARCHAR(255) NOT NULL,
                                         answer_two VARCHAR(255) NOT NULL,
                                         answer_three VARCHAR(255) NOT NULL,
                                         answer_four VARCHAR(255) NOT NULL,
                                         answer_correct VARCHAR(1) NOT NULL,
                                         PRIMARY KEY (textbook_id, chapter_id, section_id, block_id, activity_id, question_id),
                                         INDEX (question_id),
                                         INDEX (activity_id),
                                         FOREIGN KEY (textbook_id) REFERENCES Textbooks(textbook_id) ON DELETE CASCADE,
                                         FOREIGN KEY (textbook_id, chapter_id) REFERENCES Chapters(textbook_id, chapter_id) ON DELETE CASCADE,
                                         FOREIGN KEY (textbook_id, chapter_id, section_id) REFERENCES Sections(textbook_id, chapter_id, section_id) ON DELETE CASCADE,
                                         FOREIGN KEY (textbook_id, chapter_id, section_id, block_id) REFERENCES Blocks(textbook_id, chapter_id, section_id, block_id) ON DELETE CASCADE,
                                         FOREIGN KEY (textbook_id, chapter_id, section_id, block_id, activity_id) REFERENCES Activities(textbook_id, chapter_id, section_id, block_id, activity_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS StudentActivities (
    student_id VARCHAR(50),
    course_id VARCHAR(50),
    textbook_id VARCHAR(50),
    chapter_id VARCHAR(50),
    section_id VARCHAR(50),
    block_id VARCHAR(50),
    question_id VARCHAR(50),
    unique_activity_id VARCHAR(50),
    score INT DEFAULT 0,
    sa_timestamp DATETIME NOT NULL,
    PRIMARY KEY (student_id, course_id, textbook_id, chapter_id, section_id, block_id, unique_activity_id, question_id),
    INDEX (student_id),
    INDEX (course_id),
    INDEX (question_id),
    FOREIGN KEY (student_id) REFERENCES Users(user_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id),
    FOREIGN KEY (textbook_id)
        REFERENCES Textbooks(textbook_id) ON DELETE CASCADE,
    FOREIGN KEY (textbook_id, chapter_id)
        REFERENCES Chapters(textbook_id, chapter_id) ON DELETE CASCADE,
    FOREIGN KEY (textbook_id, chapter_id, section_id)
        REFERENCES Sections(textbook_id, chapter_id, section_id) ON DELETE CASCADE,
    FOREIGN KEY (textbook_id, chapter_id, section_id, block_id)
        REFERENCES Blocks(textbook_id, chapter_id, section_id, block_id) ON DELETE CASCADE,
    FOREIGN KEY (textbook_id, chapter_id, section_id, block_id, unique_activity_id)
        REFERENCES Activities(textbook_id, chapter_id, section_id, block_id, activity_id) ON DELETE CASCADE,
    FOREIGN KEY (textbook_id, chapter_id, section_id, block_id, unique_activity_id, question_id)
        REFERENCES Questions(textbook_id, chapter_id, section_id, block_id, activity_id, question_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TeachingAssistants (
    user_id VARCHAR(50),
    course_id VARCHAR(50),
    faculty_id VARCHAR(50),
    PRIMARY KEY (user_id, course_id, faculty_id),
    INDEX (faculty_id),
    INDEX (course_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id),
    FOREIGN KEY (faculty_id) REFERENCES Users(user_id)
);

CREATE TABLE IF NOT EXISTS StudentGrades (
    student_id VARCHAR(50) NOT NULL,
    course_id VARCHAR(50) NOT NULL,
    total_points INT DEFAULT 0,
    total_activities INT DEFAULT 0,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES Users(user_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

DELIMITER $$
CREATE TRIGGER notify_enrollment_change
AFTER UPDATE ON Enrollments
FOR EACH ROW
BEGIN
    -- Check if the status changed from 'Pending' to 'Enrolled'
    IF OLD.user_status = 'Pending' AND NEW.user_status = 'Enrolled' THEN
        -- Insert a notification for the user
        INSERT INTO Notifications (notification_id, content, user_id, is_read)
        VALUES (UUID(), CONCAT('Your enrollment status for course ', NEW.course_id, ' has been updated to Enrolled.'), NEW.user_id, FALSE);
    END IF;
END$$
DELIMITER ;

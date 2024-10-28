DROP SCHEMA IF EXISTS WolfBooks;
CREATE SCHEMA WolfBooks;
USE WolfBooks;

DROP TABLE IF EXISTS TeachingAssistants;
DROP TABLE IF EXISTS Questions;
DROP TABLE IF EXISTS StudentActivity;
DROP TABLE IF EXISTS Activity;
DROP TABLE IF EXISTS Content;
DROP TABLE IF EXISTS Section;
DROP TABLE IF EXISTS Chapter;
DROP TABLE IF EXISTS Enrollment;
DROP TABLE IF EXISTS Notifications;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Textbooks;

CREATE TABLE IF NOT EXISTS Textbooks (
    textbook_id VARCHAR(255),
    textbook_title VARCHAR(255) NOT NULL,
    is_customized BOOLEAN NOT NULL DEFAULT FALSE,
	PRIMARY KEY (textbook_id)
);

CREATE TABLE IF NOT EXISTS Users (
    user_id VARCHAR(255) PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    pw VARCHAR(255) NOT NULL,
    user_role VARCHAR(50) NOT NULL CHECK (user_role IN ('student', 'faculty', 'teaching_assistant', 'admin')),
    first_login BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS Courses (
   course_id VARCHAR(255) PRIMARY KEY,
   course_title VARCHAR(255) NOT NULL,
   faculty_id VARCHAR(255),
   start_date DATE,
   end_date DATE,
   course_type VARCHAR(50) NOT NULL,
   capacity INT DEFAULT 0,
   token VARCHAR(7),
   textbook_id VARCHAR(255),
   FOREIGN KEY (textbook_id) REFERENCES Textbooks(textbook_id),
   FOREIGN KEY (faculty_id) REFERENCES Users(user_id)
);

CREATE TABLE IF NOT EXISTS Notifications (
    notification_id VARCHAR(255) PRIMARY KEY,
    content TEXT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE IF NOT EXISTS Enrollment (
	user_id VARCHAR(256),
	course_id VARCHAR(256),
	user_status VARCHAR(255) NOT NULL,
	PRIMARY KEY (user_id, course_id),
	FOREIGN KEY (user_id) REFERENCES Users(user_id),
	FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

CREATE TABLE IF NOT EXISTS Chapter (
    chapter_id VARCHAR(255),
    chapter_title VARCHAR(255) NOT NULL,
    textbook_id VARCHAR(255),
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(255) NOT NULL,
    PRIMARY KEY(chapter_id),
    FOREIGN KEY (textbook_id) REFERENCES Textbooks(textbook_id)
);

CREATE TABLE IF NOT EXISTS Section (
    section_id VARCHAR(255),
    section_title VARCHAR(255) NOT NULL,
    chapter_id VARCHAR(255),
    section_number INT,
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (section_id),
    FOREIGN KEY (chapter_id) REFERENCES Chapter(chapter_id)
);

CREATE TABLE IF NOT EXISTS Content (
    content_id VARCHAR(255),
    text_content TEXT,
    Image TEXT,
    section_id VARCHAR(255) NOT NULL,
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(255) NOT NULL,
    sequence_number  INT NOT NULL,
    PRIMARY KEY(content_id),
    FOREIGN KEY (section_id) REFERENCES Section (section_id)
);

CREATE TABLE IF NOT EXISTS Activity (
    activity_id VARCHAR(255),
    total_score INT CHECK (total_score >= 0),
    a_timestamp DATETIME NOT NULL,
    content_id VARCHAR(255) NOT NULL,
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(255) NOT NULL,
    PRIMARY KEY(activity_id),
    FOREIGN KEY (content_id) REFERENCES Content(content_id),
    UNIQUE (activity_id)
);

CREATE TABLE IF NOT EXISTS StudentActivity (
    activity_id VARCHAR(255),
    student_id VARCHAR(255),
    score INT CHECK (score >= 0) DEFAULT 0,
    sa_timestamp DATETIME NOT NULL,
    PRIMARY KEY (activity_id, student_id),
    FOREIGN KEY (activity_id) REFERENCES Activity(activity_id),
    FOREIGN KEY (student_id) REFERENCES Users(user_id)
);

CREATE TABLE IF NOT EXISTS Questions (
	question_id VARCHAR(256),
	activity_id VARCHAR(256),
	question VARCHAR(256) NOT NULL,
	explanation_one VARCHAR(256) NOT NULL,
	explanation_two VARCHAR(256) NOT NULL,
	explanation_three VARCHAR(256) NOT NULL,
	explanation_four VARCHAR(256) NOT NULL,
	answer_one VARCHAR(256) NOT NULL,
	answer_two VARCHAR(256) NOT NULL,
	answer_three VARCHAR(256) NOT NULL,
	answer_four VARCHAR(256) NOT NULL,
	answer_correct CHAR NOT NULL,
	PRIMARY KEY (question_id),
	FOREIGN KEY (activity_id) REFERENCES Activity(activity_id)
);

CREATE TABLE IF NOT EXISTS TeachingAssistants (
	user_id VARCHAR(256),
	course_id VARCHAR(256),
	PRIMARY KEY (user_id, course_id),
	FOREIGN KEY (user_id) REFERENCES Users(user_id),
	FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);
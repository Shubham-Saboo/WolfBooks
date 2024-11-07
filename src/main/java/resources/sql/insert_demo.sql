INSERT INTO Textbooks (textbook_id, textbook_title) VALUES
('101', 'Database Management Systems'),
('102', 'Fundamentals of Software Engineering'),
('103', 'Fundamentals of Machine Learning');


INSERT INTO Users (user_id, firstname, lastname, email, password, user_role) VALUES
('ErPe1024', 'Eric', 'Perrig', 'ez356@example.mail', 'qwdmq', 'student'),
('Admin1024', 'Admin01', 'admin', 'rc@example.mail', '@12345', 'admin'),
('AlAr1024', 'Alice', 'Artho', 'aa23@edu.mail', 'omdsws', 'student'),
('BoTe1024', 'Bob', 'Temple', 'bt163@template.mail', 'sak+=', 'student'),
('LiGa1024', 'Lily', 'Gaddy', 'li123@example.edu', 'cnaos', 'student'),
('ArMo1024', 'Aria', 'Morrow', 'am213@example.edu', 'jwocals', 'student'),
('KeRh1014', 'Kellan', 'Rhodes', 'kr21@example.edu', 'camome', 'student'),
('SiHa1024', 'Sienna', 'Hayes', 'sh13@example.edu', 'asdqm', 'student'),
('FiWi1024', 'Finn', 'Wilder', 'fw23@example.edu', 'f13mas', 'student'),
('LeMe1024', 'Leona', 'Mercer', 'lm56@example.edu', 'ca32', 'student'),
('JaWi1024', 'James', 'Williams', 'jwilliams@ncsu.edu', 'jwilliams@1234', 'teaching_assistant'),
('LiAl0924', 'Lisa', 'Alberti', 'lalberti@ncsu.edu', 'lalberti&5678@', 'teaching_assistant'),
('DaJo1024', 'David', 'Johnson', 'djohnson@ncsu.edu', 'djohnson%@1122', 'teaching_assistant'),
('ElCl1024', 'Ellie', 'Clark', 'eclark@ncsu.edu', 'eclark^#3654', 'teaching_assistant'),
('JeGi0924', 'Jeff', 'Gibson', 'jgibson@ncsu.edu', 'jgibson$#9877', 'teaching_assistant'),
('KeOg1024', 'Kemafor', 'Ogan', 'kogan@ncsu.edu', 'Ko2024!rpc', 'faculty'),
('JoDo1024', 'John', 'Doe', 'john.doe@example.com', 'Jd2024!abc', 'faculty'),
('SaMi1024', 'Sarah', 'Miller', 'sarah.miller@domain.com', 'Sm#Secure2024', 'faculty'),
('DaBr1024', 'David', 'Brown', 'david.b@webmail.com', 'DbPass123!', 'faculty'),
('EmDa1024', 'Emily', 'Davis', 'emily.davis@email.com', 'Emily#2024!', 'faculty'),
('MiWi1024', 'Michael', 'Wilson', 'michael.w@service.com', 'Mw987secure', 'faculty');

INSERT INTO Courses (course_id, course_title, textbook_id, course_type, faculty_id, start_date, end_date, token, capacity) VALUES
('NCSUOganCSC440F24', 'CSC440 Database Systems', '101', 'Active', 'KeOg1024', '2024-08-15', '2024-12-15', 'XYJKLM', 60),
('NCSUOganCSC540F24', 'CSC540 Database Systems', '101', 'Active', 'KeOg1024', '2024-08-17', '2024-12-15', 'STUKZT', 50),
('NCSUSaraCSC326F24', 'CSC326 Software Engineering', '102', 'Active', 'SaMi1024', '2024-08-23', '2024-10-23', 'LRUFND', 100),
('NCSUJegiCSC522F24', 'CSC522 Fundamentals of Machine Learning', '103', 'Evaluation', 'JoDo1024', '2025-08-25', '2025-12-18', NULL, NULL),
('NCSUSaraCSC326F25', 'CSC326 Software Engineering', '102', 'Evaluation', 'SaMi1024', '2025-08-27', '2025-12-19', NULL, NULL);

INSERT INTO Notifications (notification_id, content, user_id, is_read) VALUES
('notif001', 'Welcome to the course!', 'ErPe1024', FALSE),
('notif002', 'Your assignment is due soon.', 'AlAr1024', FALSE),
('notif003', 'Reminder: Midterm exam next week.', 'BoTe1024', TRUE),
('notif004', 'New material uploaded to your course.', 'LiGa1024', FALSE);

INSERT INTO Enrollments (user_id, course_id, user_status) VALUES
('ErPe1024', 'NCSUOganCSC440F24', 'enrolled'),
('ErPe1024', 'NCSUOganCSC540F24', 'enrolled'),
('ErPe1024', 'NCSUSaraCSC326F25', 'pending'),
('AlAr1024', 'NCSUOganCSC540F24', 'enrolled'),
('AlAr1024', 'NCSUOganCSC440F24', 'enrolled'),
('AlAr1024', 'NCSUSaraCSC326F25', 'pending'),
('BoTe1024', 'NCSUSaraCSC326F24', 'enrolled'),
('BoTe1024', 'NCSUOganCSC440F24', 'enrolled'),
('LiGa1024', 'NCSUOganCSC440F24', 'enrolled'),
('LiGa1024', 'NCSUOganCSC540F24', 'enrolled'),
('LiGa1024', 'NCSUJegiCSC522F24', 'pending'),
('ArMo1024', 'NCSUOganCSC540F24', 'enrolled'),
('ArMo1024', 'NCSUOganCSC440F24', 'enrolled'),
('ArMo1024', 'NCSUSaraCSC326F25', 'pending'),
('SiHa1024', 'NCSUOganCSC440F24', 'enrolled'),
('FiWi1024', 'NCSUSaraCSC326F24', 'enrolled'),
('FiWi1024', 'NCSUJegiCSC522F24', 'pending'),
('LeMe1024', 'NCSUJegiCSC522F24', 'enrolled'),
('LeMe1024', 'NCSUSaraCSC326F25', 'pending');


INSERT INTO Chapters (chapter_id, chapter_title, textbook_id, is_hidden, created_by) VALUES
('chap01', 'Introduction to Database', '101', FALSE, 'admin'),
('chap02', 'The Relational Model', '101', FALSE, 'admin'),
('chap01', 'Introduction to Software Engineering', '102', FALSE, 'admin'),
('chap02', 'Introduction to Software Development Life Cycle (SDLC)', '102', FALSE, 'admin'),
('chap01', 'Introduction to Machine Learning', '103', FALSE, 'admin');


INSERT INTO Sections (section_id, textbook_id, section_title, chapter_id, is_hidden, created_by) VALUES
('Sec01', '101', 'Database Management Systems (DBMS) Overview', 'chap01', FALSE, 'admin'),
('Sec02', '101', 'Data Models and Schemas', 'chap01', FALSE, 'admin'),
('Sec01', '101', 'Entities, Attributes, and Relationships', 'chap02', FALSE, 'admin'),
('Sec02', '101', 'Normalization and Integrity Constraints', 'chap02', FALSE, 'admin'),
('Sec01', '102', 'History and Evolution of Software Engineering', 'chap01', FALSE, 'admin'),
('Sec02', '102', 'Key Principles of Software Engineering', 'chap01', FALSE, 'admin'),
('Sec01', '102', 'Phases of the SDLC', 'chap02', TRUE, 'admin'),
('Sec02', '102', 'Agile vs. Waterfall Models', 'chap02', FALSE, 'admin'),
('Sec01', '103', 'Overview of Machine Learning', 'chap01', TRUE, 'admin'),
('Sec02', '103', 'Supervised vs Unsupervised Learning', 'chap01', FALSE, 'admin');


INSERT INTO Blocks (section_id, textbook_id, block_id, chapter_id, content_type, content, is_hidden, created_by) VALUES
('Sec01', '101', 'Block01', 'chap01', 'text', 'A Database Management System (DBMS) is software that enables users to efficiently create, manage, and manipulate databases. It serves as an interface between the database and end users, ensuring data is stored securely, retrieved accurately, and maintained consistently. Key features of a DBMS include data organization, transaction management, and support for multiple users accessing data concurrently.', FALSE, 'admin'),
('Sec02', '101', 'Block01', 'chap01', 'activity', 'ACT0', FALSE, 'admin'),
('Sec01', '101', 'Block01', 'chap02', 'text', 'DBMS systems provide structured storage and ensure that data is accessible through queries using languages like SQL. They handle critical tasks such as maintaining data integrity, enforcing security protocols, and optimizing data retrieval, making them essential for both small-scale and enterprise-level applications. Examples of popular DBMS include MySQL, Oracle, and PostgreSQL.', FALSE, 'admin'),
('Sec02', '101', 'Block01', 'chap02', 'picture', 'sample.png', FALSE, 'admin'),
('Sec01', '102', 'Block01', 'chap01', 'text', 'The history of software engineering dates back to the 1960s, when the "software crisis" highlighted the need for structured approaches to software development due to rising complexity and project failures. Over time, methodologies such as Waterfall, Agile, and DevOps evolved, transforming software engineering into a disciplined, iterative process that emphasizes efficiency, collaboration, and adaptability.', FALSE, 'admin'),
('Sec02', '102', 'Block01', 'chap01', 'activity', 'ACT0', FALSE, 'admin'),
('Sec01', '102', 'Block01', 'chap02', 'text', 'The Software Development Life Cycle (SDLC) consists of key phases including requirements gathering, design, development, testing, deployment, and maintenance. Each phase plays a crucial role in ensuring that software is built systematically, with feedback and revisions incorporated at each step to deliver a high-quality product.', FALSE, 'admin'),
('Sec02', '102', 'Block01', 'chap02', 'picture', 'sample2.png', FALSE, 'admin'),
('Sec01', '103', 'Block01', 'chap01', 'text', 'Machine learning is a subset of artificial intelligence that enables systems to learn from data, identify patterns, and make decisions with minimal human intervention. By training algorithms on vast datasets, machine learning models can improve their accuracy over time, driving advancements in fields like healthcare, finance, and autonomous systems.', FALSE, 'admin'),
('Sec02', '103', 'Block01', 'chap01', 'activity', 'ACT0', FALSE, 'admin');

INSERT INTO Activities (activity_id, section_id, textbook_id, chapter_id, block_id, is_hidden, created_by) VALUES
('ACT0', 'Sec02', '101', 'chap01', 'Block01', FALSE, 'admin'),
('ACT0', 'Sec02', '102', 'chap01', 'Block01', FALSE, 'admin'),
('ACT0', 'Sec02', '103', 'chap01', 'Block01', FALSE, 'admin');

INSERT INTO Questions (textbook_id, chapter_id, section_id, block_id, activity_id, question_id, question, explanation_one, explanation_two, explanation_three, explanation_four, answer_one, answer_two, answer_three, answer_four, answer_correct) VALUES
('101', 'chap01', 'Sec02', 'Block01', 'ACT0', 'Q1', 'What does a DBMS provide?', 'Incorrect: DBMS provides more than just storage', 'Correct: DBMS manages both storing and retrieving data', 'Incorrect: DBMS also handles other functions', 'Incorrect: DBMS does not manage network infrastructure', 'Data storage only', 'Data storage and retrieval', 'Only security features', 'Network management', '2'),
('101', 'chap01', 'Sec02', 'Block01', 'ACT0', 'Q2', 'Which of these is an example of a DBMS?', 'Incorrect: Excel is a spreadsheet application', 'Correct: MySQL is a popular DBMS', 'Incorrect: Chrome is a web browser', 'Incorrect: Windows is an operating system', 'Microsoft Excel', 'MySQL', 'Google Chrome', 'Windows 10', '2'),
('101', 'chap01', 'Sec02', 'Block01', 'ACT0', 'Q3', 'What type of data does a DBMS manage?', 'Correct: DBMS primarily manages structured data', 'Incorrect: While some DBMS systems can handle it, it''s not core', 'Incorrect: DBMS doesn''t manage network data', 'Incorrect: DBMS does not handle hardware usage data', 'Structured data', 'Unstructured multimedia', 'Network traffic data', 'Hardware usage statistics', '1'),
('102', 'chap01', 'Sec02', 'Block01', 'ACT0', 'Q1', 'What was the "software crisis"?', 'Incorrect: The crisis was related to software development issues', 'Correct: The crisis was due to the complexity and unreliability of software', 'Incorrect: It was not related to networking', 'Incorrect: The crisis was not about physical storage limitations', 'A hardware shortage', 'Difficulty in software creation', 'A network issue', 'Lack of storage devices', '2'),
('102', 'chap01', 'Sec02', 'Block01', 'ACT0', 'Q2', 'Which methodology was first introduced in software engineering?', 'Correct: Waterfall was the first formal software development model', 'Incorrect: Agile was introduced much later', 'Incorrect: DevOps is a more recent development approach', 'Incorrect: Scrum is a part of Agile, not the first methodology', 'Waterfall model', 'Agile methodology', 'DevOps', 'Scrum', '1'),
('102', 'chap01', 'Sec02', 'Block01', 'ACT0', 'Q3', 'What challenge did early software engineering face?', 'Incorrect: Programming languages existed but were difficult to manage', 'Correct: Early engineers struggled with managing large, complex systems', 'Incorrect: The issue was primarily with software, not hardware', 'Incorrect: Internet connectivity wasn''t a challenge in early software', 'Lack of programming languages', 'Increasing complexity of software', 'Poor hardware development', 'Internet connectivity issues', '2'),
('103', 'chap01', 'Sec02', 'Block01', 'ACT0', 'Q1', 'What is the primary goal of supervised learning?', 'Correct: The goal is to learn a mapping from inputs to outputs for prediction.', 'Incorrect: This is more aligned with unsupervised learning.', 'Incorrect: This is not the main goal of supervised learning.', 'Incorrect: This is not applicable to supervised learning.', 'Predict outcomes', 'Group similar data', 'Discover patterns', 'Optimize cluster groups', '1'),
('103', 'chap01', 'Sec02', 'Block01', 'ACT0', 'Q2', 'Which type of data is used in unsupervised learning?', 'Incorrect: Unsupervised learning uses unlabeled data.', 'Correct: It analyzes data without pre-existing labels.', 'Incorrect: Unlabeled data can be structured or unstructured.', 'Incorrect: Unsupervised learning does not specifically focus on time-series.', 'Labeled data', 'Unlabeled data', 'Structured data', 'Time-series data', '2'),
('103', 'chap01', 'Sec02', 'Block01', 'ACT0', 'Q3', 'In which scenario would you typically use supervised learning?', 'Incorrect: This is more relevant to unsupervised learning.', 'Correct: Supervised learning is ideal for predicting fraud based on labeled examples.', 'Incorrect: This is generally done using unsupervised methods.', 'Incorrect: While applicable, it is less common than fraud detection in supervised learning.', 'Customer segmentation', 'Fraud detection', 'Market basket analysis', 'Anomaly detection', '2');


INSERT INTO StudentActivities (student_id, course_id, textbook_id, chapter_id, section_id, block_id, question_id, unique_activity_id, score, sa_timestamp) VALUES
('ErPe1024', 'NCSUOganCSC440F24', '101', 'chap01', 'Sec02', 'Block01', 'Q1', 'ACT0', 3, '2024-08-01 11:10:00'),
('ErPe1024', 'NCSUOganCSC440F24', '101', 'chap01', 'Sec02', 'Block01', 'Q2', 'ACT0', 1, '2024-08-01 14:18:00'),
('ErPe1024', 'NCSUOganCSC540F24', '101', 'chap01', 'Sec02', 'Block01', 'Q1', 'ACT0', 1, '2024-08-02 19:06:00'),
('AlAr1024', 'NCSUOganCSC440F24', '101', 'chap01', 'Sec02', 'Block01', 'Q1', 'ACT0', 3, '2024-08-01 13:24:00'),
('BoTe1024', 'NCSUOganCSC440F24', '101', 'chap01', 'Sec02', 'Block01', 'Q1', 'ACT0', 0, '2024-08-01 09:24:00'),
('LiGa1024', 'NCSUOganCSC440F24', '101', 'chap01', 'Sec02', 'Block01', 'Q1', 'ACT0', 3, '2024-08-01 07:45:00'),
('LiGa1024', 'NCSUOganCSC440F24', '101', 'chap01', 'Sec02', 'Block01', 'Q2', 'ACT0', 3, '2024-08-01 12:30:00'),
('LiGa1024', 'NCSUOganCSC540F24', '101', 'chap01', 'Sec02', 'Block01', 'Q1', 'ACT0', 3, '2024-08-03 16:52:00'),
('ArMo1024', 'NCSUOganCSC440F24', '101', 'chap01', 'Sec02', 'Block01', 'Q1', 'ACT0', 1, '2024-08-01 21:18:00'),
('ArMo1024', 'NCSUOganCSC440F24', '101', 'chap01', 'Sec02', 'Block01', 'Q2', 'ACT0', 3, '2024-08-01 05:03:00'),
('FiWi1024', 'NCSUSaraCSC326F24', '102', 'chap01', 'Sec02', 'Block01', 'Q1', 'ACT0', 1, '2024-08-29 22:41:00'),
('LeMe1024', 'NCSUJegiCSC522F24', '103', 'chap01', 'Sec02', 'Block01', 'Q1', 'ACT0', 3, '2024-09-01 13:12:00'),
('LeMe1024', 'NCSUJegiCSC522F24', '103', 'chap01', 'Sec02', 'Block01', 'Q2', 'ACT0', 3, '2024-09-09 18:27:00');

INSERT INTO TeachingAssistants (user_id, course_id, faculty_id) VALUES
('JaWi1024', 'NCSUOganCSC440F24', 'KeOg1024'),
('LiAl0924', 'NCSUOganCSC540F24', 'KeOg1024'),
('DaJo1024', 'NCSUSaraCSC326F24', 'SaMi1024'),
('ElCl1024', 'NCSUJegiCSC522F24', 'JoDo1024'),
('JeGi0924','NCSUSaraCSC326F25','SaMi1024');

INSERT INTO StudentGrades (student_id, course_id, total_points, total_activities) VALUES
('ErPe1024', 'NCSUOganCSC440F24', 4, 2),
('ErPe1024', 'NCSUOganCSC540F24', 1, 1),
('AlAr1024', 'NCSUOganCSC440F24', 3, 1),
('BoTe1024', 'NCSUOganCSC440F24', 0, 1),
('LiGa1024', 'NCSUOganCSC440F24', 9, 3),
('LiGa1024', 'NCSUOganCSC540F24', 0, 0),
('ArMo1024', 'NCSUOganCSC540F24', 0, 0),
('ArMo1024', 'NCSUOganCSC440F24', 4, 2),
('SiHa1024', 'NCSUOganCSC440F24', 0, 0),
('FiWi1024', 'NCSUSaraCSC326F24', 1, 1),
('LeMe1024', 'NCSUJegiCSC522F24', 6, 2);

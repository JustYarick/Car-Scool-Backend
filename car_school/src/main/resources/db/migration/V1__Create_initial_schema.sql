-- Создание таблицы "groups"
CREATE TABLE groups (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Создание таблицы "subjects"
CREATE TABLE subjects (
    subject_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(255) NOT NULL,
    subject_description TEXT
);

-- Создание таблицы "schedules"
CREATE TABLE schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT,
    teacher_uuid UUID NOT NULL,
    lesson_date_start TIMESTAMP,
    lesson_date_end TIMESTAMP,
    subject_id BIGINT,
    FOREIGN KEY (group_id) REFERENCES groups(id),
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);

-- Создание таблицы "group_students"
CREATE TABLE group_students (
    group_id BIGINT,
    student_uuid UUID NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups(id)
);

-- Создание таблицы "users_requests"
CREATE TABLE users_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id UUID NOT NULL,
    telephone VARCHAR(15),
    create_request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    time_to_call TIMESTAMP,
    status VARCHAR(255) DEFAULT 'NEW'
);
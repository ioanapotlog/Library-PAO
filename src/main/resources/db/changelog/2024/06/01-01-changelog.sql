-- liquibase formatted sql

-- changeset ioana:1717230887398-1
CREATE TABLE AUDIO_BOOKS
(
    ID            BIGINT NOT NULL,
    FORMAT        VARCHAR(255),
    DOWNLOAD_LINK VARCHAR(255),
    NARRATOR      VARCHAR(255),
    LANGUAGE      VARCHAR(255),
    HOURS         INTEGER,
    CONSTRAINT pk_audio_books PRIMARY KEY (ID)
);

-- changeset ioana:1717230887398-2
CREATE TABLE BOOKS
(
    ID               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    DISCRIMINATOR    VARCHAR(31),
    TITLE            VARCHAR(255),
    AUTHOR           VARCHAR(255),
    PUBLICATION_YEAR INTEGER,
    GENRE            VARCHAR(255),
    CONSTRAINT pk_books PRIMARY KEY (ID)
);

-- changeset ioana:1717230887398-3
CREATE TABLE BORROWED_BOOKS
(
    BORROWING_ID       BIGINT NOT NULL,
    "borrowedBooks_ID" BIGINT NOT NULL
);

-- changeset ioana:1717230887398-4
CREATE TABLE BORROWINGS
(
    ID           BIGINT NOT NULL,
    BORROW_DATE  TIMESTAMP WITHOUT TIME ZONE,
    RETURN_DATE  TIMESTAMP WITHOUT TIME ZONE,
    LIBRARIAN_ID BIGINT,
    STATUS       VARCHAR(255),
    CONSTRAINT pk_borrowings PRIMARY KEY (ID)
);

-- changeset ioana:1717230887398-5
CREATE TABLE DIGITAL_BOOKS
(
    ID            BIGINT NOT NULL,
    FORMAT        VARCHAR(255),
    DOWNLOAD_LINK VARCHAR(255),
    LANGUAGE      VARCHAR(255),
    PAGE_COUNT    INTEGER,
    CONSTRAINT pk_digital_books PRIMARY KEY (ID)
);

-- changeset ioana:1717230887398-6
CREATE TABLE DOWNLOADS
(
    ID                 BIGINT NOT NULL,
    DOWNLOAD_DATE      TIMESTAMP WITHOUT TIME ZONE,
    DOWNLOADED_BOOK_ID BIGINT,
    CONSTRAINT pk_downloads PRIMARY KEY (ID)
);

-- changeset ioana:1717230887398-7
CREATE TABLE LIBRARIANS
(
    ID         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    LAST_NAME  VARCHAR(255),
    FIRST_NAME VARCHAR(255),
    CONSTRAINT pk_librarians PRIMARY KEY (ID)
);

-- changeset ioana:1717230887398-8
CREATE TABLE OPERATIONS
(
    ID      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    USER_ID BIGINT,
    CONSTRAINT pk_operations PRIMARY KEY (ID)
);

-- changeset ioana:1717230887398-9
CREATE TABLE PHYSICAL_BOOKS
(
    ID         BIGINT NOT NULL,
    LOCATION   VARCHAR(255),
    COPY_COUNT INTEGER,
    CONDITION  VARCHAR(255),
    LANGUAGE   VARCHAR(255),
    PAGE_COUNT INTEGER,
    CONSTRAINT pk_physical_books PRIMARY KEY (ID)
);

-- changeset ioana:1717230887398-10
CREATE TABLE REVIEWS
(
    ID          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    RATING      INTEGER,
    COMMENT     VARCHAR(255),
    REVIEWER_ID BIGINT,
    BOOK_ID     BIGINT,
    REVIEW_DATE TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_reviews PRIMARY KEY (ID)
);

-- changeset ioana:1717230887398-11
CREATE TABLE USERS
(
    ID           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    LAST_NAME    VARCHAR(255),
    FIRST_NAME   VARCHAR(255),
    PHONE_NUMBER VARCHAR(255),
    EMAIL        VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (ID)
);

-- changeset ioana:1717230887398-12
ALTER TABLE BORROWED_BOOKS
    ADD CONSTRAINT uc_borrowed_books_borrowedbooks UNIQUE ("borrowedBooks_ID");

-- changeset ioana:1717230887398-13
ALTER TABLE AUDIO_BOOKS
    ADD CONSTRAINT FK_AUDIO_BOOKS_ON_ID FOREIGN KEY (ID) REFERENCES BOOKS (ID);

-- changeset ioana:1717230887398-14
ALTER TABLE BORROWINGS
    ADD CONSTRAINT FK_BORROWINGS_ON_ID FOREIGN KEY (ID) REFERENCES OPERATIONS (ID);

-- changeset ioana:1717230887398-15
ALTER TABLE BORROWINGS
    ADD CONSTRAINT FK_BORROWINGS_ON_LIBRARIAN FOREIGN KEY (LIBRARIAN_ID) REFERENCES LIBRARIANS (ID);

-- changeset ioana:1717230887398-16
ALTER TABLE DIGITAL_BOOKS
    ADD CONSTRAINT FK_DIGITAL_BOOKS_ON_ID FOREIGN KEY (ID) REFERENCES BOOKS (ID);

-- changeset ioana:1717230887398-17
ALTER TABLE DOWNLOADS
    ADD CONSTRAINT FK_DOWNLOADS_ON_DOWNLOADED_BOOK FOREIGN KEY (DOWNLOADED_BOOK_ID) REFERENCES BOOKS (ID);

-- changeset ioana:1717230887398-18
ALTER TABLE DOWNLOADS
    ADD CONSTRAINT FK_DOWNLOADS_ON_ID FOREIGN KEY (ID) REFERENCES OPERATIONS (ID);

-- changeset ioana:1717230887398-19
ALTER TABLE OPERATIONS
    ADD CONSTRAINT FK_OPERATIONS_ON_USER FOREIGN KEY (USER_ID) REFERENCES USERS (ID);

-- changeset ioana:1717230887398-20
ALTER TABLE PHYSICAL_BOOKS
    ADD CONSTRAINT FK_PHYSICAL_BOOKS_ON_ID FOREIGN KEY (ID) REFERENCES BOOKS (ID);

-- changeset ioana:1717230887398-21
ALTER TABLE REVIEWS
    ADD CONSTRAINT FK_REVIEWS_ON_BOOK FOREIGN KEY (BOOK_ID) REFERENCES BOOKS (ID);

-- changeset ioana:1717230887398-22
ALTER TABLE REVIEWS
    ADD CONSTRAINT FK_REVIEWS_ON_REVIEWER FOREIGN KEY (REVIEWER_ID) REFERENCES USERS (ID);

-- changeset ioana:1717230887398-23
ALTER TABLE BORROWED_BOOKS
    ADD CONSTRAINT fk_borboo_on_book FOREIGN KEY ("borrowedBooks_ID") REFERENCES BOOKS (ID);

-- changeset ioana:1717230887398-24
ALTER TABLE BORROWED_BOOKS
    ADD CONSTRAINT fk_borboo_on_borrow FOREIGN KEY (BORROWING_ID) REFERENCES BORROWINGS (ID);

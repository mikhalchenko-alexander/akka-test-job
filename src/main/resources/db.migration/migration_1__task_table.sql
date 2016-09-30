CREATE TABLE TASK (
  ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  NAME VARCHAR(255),
  VALUE BOOLEAN
);

INSERT INTO TASK (NAME, VALUE) VALUES
  ('checkUserProfile',    true),
  ('checkUserConnection', true),
  ('checkProfilePicture', true);

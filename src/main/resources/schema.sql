DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks (
  id           INT          NOT NULL AUTO_INCREMENT,
  task_text    VARCHAR(100) NOT NULL,
  task_day     VARCHAR(50)  NOT NULL,
  set_reminder BOOLEAN NOT NULL,
  PRIMARY KEY (id)
);
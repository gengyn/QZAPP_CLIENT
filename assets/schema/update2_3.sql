CREATE TABLE APP_CACHE  (
   USERNAME             VARCHAR2(30)                    NOT NULL,
   USERPHONE            NUMBER(11)                      NOT NULL,
   CACHE_TYPE           CHAR(2)                         NOT NULL,
   CACHE_CONTENT        TEXT,
   CONSTRAINT PK_APP_CACHE PRIMARY KEY (USERNAME, USERPHONE, CACHE_TYPE)
);
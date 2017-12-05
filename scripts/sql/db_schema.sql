drop table DIMENSION;

create table DIMENSION (
  ID serial,
  CODE varchar(50),
  ALIAS varchar(50),
  MODE varchar(25),
  STRUCTURE varchar(25),
  primary key (ID)
);

ALTER TABLE DIMENSION ADD CONSTRAINT "UI_DIMENSION_CODE" UNIQUE (CODE);
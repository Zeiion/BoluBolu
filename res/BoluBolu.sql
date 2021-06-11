CREATE DATABASE bolubolu;
go

USE bolubolu;
go

CREATE TABLE dw_user (
  [user_id] int identity(1,1) primary key,
  [user_name] varchar(20) not null,
  user_password varchar(40) not null,
  user_avatar varchar(255),
  user_tag varchar(255),
);
go
CREATE TABLE dw_group (
  group_id int identity(1,1) primary key,
  group_name varchar(20) NOT NULL,
  group_master int NOT NULL references dw_user([user_id]) unique,
  group_tag varchar(max) NOT NULL,
  group_avatar varchar(max) NOT NULL
);
go
CREATE TABLE dw_groupchat (
  gchat_id int identity(1,1) primary key ,
  gchat_uid int NOT NULL references dw_user([user_id]),
  gchat_gid int NOT NULL,
  gchat_message varchar(max) NOT NULL,
  gchat_datetime datetime default getdate()
) ;
go
CREATE TABLE dw_userchat (
  uchat_id int identity(1,1) primary key,
  uchat_fromid int NOT NULL,
  uchat_toid int NOT NULL,
  uchat_message varchar(max) NOT NULL,
  uchat_datetime datetime default getdate()
) ;
go
CREATE TABLE dw_useruser (
  myself int NOT NULL,
  myfriend int NOT NULL
) ;
go
CREATE VIEW view_usergroup as
select user_id, group_id, group_name, group_avatar,group_tag from dw_group,dw_user;
go
CREATE VIEW view_useruser as
select myfriend,myself,user_name,user_avatar,user_tag from dw_useruser,dw_user where dw_user.user_id = dw_useruser.myfriend;
go


-- 密码为MD5加密后的123456
set identity_insert dw_user ON;
INSERT INTO  dw_user  ( user_id ,  user_name ,  user_password ,  user_avatar ,  user_tag ) VALUES
(101, 'Zeiion', 'e10adc3949ba59abbe56e057f20f883e', '', 'This is a tag.'),
(102, 'Naruto', 'e10adc3949ba59abbe56e057f20f883e', '', 'Sasuke!!!'),
(103, 'DIO', 'e10adc3949ba59abbe56e057f20f883e', '', '咋瓦鲁多!'),
(104, 'JoJo', 'e10adc3949ba59abbe56e057f20f883e', '', 'DIO!!­'),
(105, 'Luffy', 'e10adc3949ba59abbe56e057f20f883e', '', '(*^?^*)'),
(106, 'BoluBolu', 'e10adc3949ba59abbe56e057f20f883e', '', 'bolubolu');
set identity_insert dw_user OFF;

INSERT INTO  dw_useruser  ( myself ,  myfriend ) VALUES
(101, 102),
(101, 103),
(101, 104),
(102, 101),
(102, 103),
(103, 101),
(103, 102),
(104, 101);

set identity_insert dw_group ON;
INSERT INTO dw_group ( group_id ,  group_name ,  group_master ,  group_tag ,  group_avatar ) VALUES
(101, 'BoluBoluGroup', 101, 'Welcome to BoluBolu!',  ''),
(102, 'Java??', 102, 'This is a chatroom', '');
set identity_insert dw_group OFF;

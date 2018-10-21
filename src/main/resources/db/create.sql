/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/10/8 18:21:15                           */
/*==============================================================*/


drop table if exists pypzx_admin;

drop table if exists pypzx_cup;

drop table if exists pypzx_game;

drop table if exists pypzx_game_record;

drop table if exists pypzx_kick_day;

drop table if exists pypzx_player;

drop table if exists pypzx_player_info;

drop table if exists pypzx_player_rank;

drop table if exists pypzx_team;

drop table if exists pypzx_team_rank;

/*==============================================================*/
/* Table: pypzx_admin                                           */
/*==============================================================*/
create table pypzx_admin
(
   username             varchar(32),
   password             varchar(32)
);

/*==============================================================*/
/* Table: pypzx_cup                                             */
/*==============================================================*/
create table pypzx_cup
(
   cup_id               varchar(32) not null,
   cup_name             varchar(64),
   primary key (cup_id)
);

/*==============================================================*/
/* Table: pypzx_game                                            */
/*==============================================================*/
create table pypzx_game
(
   game_id              varchar(32) not null,
   game_date            timestamp,
   team_home            varchar(32),
   team_away            varchar(32),
   cup_id               varchar(32),
   primary key (game_id)
);

/*==============================================================*/
/* Table: pypzx_game_record                                     */
/*==============================================================*/
create table pypzx_game_record
(
   game_id              varchar(32),
   player_id            varchar(32),
   type                 smallint,
   time                 timestamp
);

/*==============================================================*/
/* Table: pypzx_kick_day                                        */
/*==============================================================*/
create table pypzx_kick_day
(
   date                 date,
   num                  int
);

/*==============================================================*/
/* Table: pypzx_player                                          */
/*==============================================================*/
create table pypzx_player
(
   player_id            varchar(32) not null,
   player_name          varchar(32),
   player_num           smallint,
   team_id              varchar(32),
   primary key (player_id)
);

/*==============================================================*/
/* Table: pypzx_player_info                                     */
/*==============================================================*/
create table pypzx_player_info
(
   player_id            varchar(32),
   player_stuno         varchar(10),
   player_depart        varchar(32),
   player_tel           varchar(11),
   key AK_uqi_stuno (player_stuno),
   key AK_uqi_id (player_id)
);

/*==============================================================*/
/* Table: pypzx_player_rank                                     */
/*==============================================================*/
create table pypzx_player_rank
(
   player_id            varchar(32),
   goals                int,
   penalty              smallint
);

/*==============================================================*/
/* Table: pypzx_team                                            */
/*==============================================================*/
create table pypzx_team
(
   team_id              varchar(32) not null,
   team_name            varchar(64),
   cup_id               varchar(32),
   leader_id            varchar(32),
   vaild_code           varchar(4),
   primary key (team_id)
);

/*==============================================================*/
/* Table: pypzx_team_rank                                       */
/*==============================================================*/
create table pypzx_team_rank
(
   team_id              varchar(32),
   points               int,
   goals                int,
   yellow               smallint
);

alter table pypzx_game add constraint FK_fk_game_cup foreign key (cup_id)
      references pypzx_cup (cup_id) on delete restrict on update restrict;

alter table pypzx_game_record add constraint FK_fk_record_game foreign key (game_id)
      references pypzx_game (game_id) on delete restrict on update restrict;

alter table pypzx_game_record add constraint FK_fk_record_player foreign key (player_id)
      references pypzx_player (player_id) on delete restrict on update restrict;

alter table pypzx_player add constraint FK_fk_player_team foreign key (team_id)
      references pypzx_team (team_id) on delete restrict on update restrict;

alter table pypzx_player_info add constraint FK_fk_player_info foreign key (player_id)
      references pypzx_player (player_id) on delete restrict on update restrict;

alter table pypzx_player_rank add constraint FK_fk_player_rank foreign key (player_id)
      references pypzx_player (player_id) on delete restrict on update restrict;

alter table pypzx_team add constraint FK_fk_team_cup foreign key (cup_id)
      references pypzx_cup (cup_id) on delete restrict on update restrict;

alter table pypzx_team_rank add constraint FK_fk_team_rank foreign key (team_id)
      references pypzx_team (team_id) on delete restrict on update restrict;


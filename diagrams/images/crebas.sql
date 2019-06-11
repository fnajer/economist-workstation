/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     27.05.2019 22:14:22                          */
/*==============================================================*/


drop table if exists Entity_14;

drop table if exists Entity_16;

drop table if exists Entity_17;

drop table if exists contract;

drop table if exists employee;

drop table if exists period;

drop table if exists renter;

drop table if exists "Платеж на аренду оборудования";

drop table if exists "Платеж на аренду помещения";

drop table if exists "Платеж на земельный налог";

drop table if exists "Платеж на пени";

drop table if exists "Платеж на услуги";

drop table if exists Территория;

drop table if exists заключает;

/*==============================================================*/
/* Table: Entity_14                                             */
/*==============================================================*/
create table Entity_14
(
   id_post              int not null,
   name                 text,
   primary key (id_post)
);

/*==============================================================*/
/* Table: Entity_16                                             */
/*==============================================================*/
create table Entity_16
(
   id_extra_cost        int not null,
   id_period            int not null,
   extra_cost_rent      float,
   extra_cost_fine      float,
   extra_cost_services  float,
   extra_cost_tax_land  float,
   extra_cost_equipment float,
   primary key (id_extra_cost)
);

/*==============================================================*/
/* Table: Entity_17                                             */
/*==============================================================*/
create table Entity_17
(
   id_balance           int not null,
   id_period            int not null,
   debit_rent           float,
   credit_rent          float,
   debit_fine           float,
   credit_fine          float,
   debit_tax_land       float,
   credit_tax_land      float,
   debit_equipment      float,
   credit_equipment     float,
   debit_services       float,
   credit_services      float,
   primary key (id_balance)
);

/*==============================================================*/
/* Table: contract                                              */
/*==============================================================*/
create table contract
(
   id_contract          int not null,
   id_employee          int not null,
   date_start           date,
   date_end             date,
   primary key (id_contract)
);

/*==============================================================*/
/* Table: employee                                              */
/*==============================================================*/
create table employee
(
   id_employee          int not null,
   id_post              int not null,
   name_employee        text,
   surname_employee     text,
   patronymic_employee  text,
   primary key (id_employee)
);

/*==============================================================*/
/* Table: period                                                */
/*==============================================================*/
create table period
(
   id_period            int not null,
   id_rent              int,
   id_services          int,
   id_contract          int not null,
   id_extra_cost        int,
   id_fine              int,
   id_equipment         int,
   id_tax_land          int,
   id_balance           int,
   number               int,
   number_rent_acc      int,
   number_services_acc  int,
   date_end_period      date,
   primary key (id_period)
);

/*==============================================================*/
/* Table: renter                                                */
/*==============================================================*/
create table renter
(
   id_renter            int not null,
   first_name           text,
   last_name            text,
   patronymic           text,
   address              text,
   birthday             date,
   subject              text,
   primary key (id_renter)
);

/*==============================================================*/
/* Table: "Платеж на аренду оборудования"                       */
/*==============================================================*/
create table "Платеж на аренду оборудования"
(
   id_equipment         int not null,
   id_period            int not null,
   cost_equipment       float,
   paid_equipment       float,
   date_paid_equipment  date,
   primary key (id_equipment)
);

/*==============================================================*/
/* Table: "Платеж на аренду помещения"                          */
/*==============================================================*/
create table "Платеж на аренду помещения"
(
   id_rent              int not null,
   id_period            int not null,
   cost                 float,
   index_cost           float,
   paid_rent            float,
   date_paid_rent       date,
   primary key (id_rent)
);

/*==============================================================*/
/* Table: "Платеж на земельный налог"                           */
/*==============================================================*/
create table "Платеж на земельный налог"
(
   id_tax_land          int not null,
   id_period            int not null,
   tax_land             float,
   paid_tax_land        float,
   date_paid_tax_land   date,
   primary key (id_tax_land)
);

/*==============================================================*/
/* Table: "Платеж на пени"                                      */
/*==============================================================*/
create table "Платеж на пени"
(
   id_fine              int not null,
   id_period            int not null,
   fine                 float,
   paid_fine            float,
   date_paid_fine       date,
   primary key (id_fine)
);

/*==============================================================*/
/* Table: "Платеж на услуги"                                    */
/*==============================================================*/
create table "Платеж на услуги"
(
   id_services          int not null,
   id_period            int not null,
   count_water          float,
   tariff_water         float,
   count_electricity    float,
   tariff_electricity   float,
   cost_meter_heading   float,
   cost_meter_garbage   float,
   cost_internet        float,
   cost_telephone       float,
   paid_services        float,
   date_paid_services   date,
   primary key (id_services)
);

/*==============================================================*/
/* Table: Территория                                            */
/*==============================================================*/
create table Территория
(
   id_building          int not null,
   id_contract          int,
   type                 text,
   square               float,
   cost_balance         float,
   cost_residue         float,
   primary key (id_building)
);

/*==============================================================*/
/* Table: заключает                                             */
/*==============================================================*/
create table заключает
(
   id_contract          int not null,
   id_renter            int not null,
   primary key (id_contract, id_renter)
);

alter table Entity_16 add constraint FK_determines3 foreign key (id_period)
      references period (id_period) on delete restrict on update restrict;

alter table Entity_17 add constraint FK_contains5 foreign key (id_period)
      references period (id_period) on delete restrict on update restrict;

alter table contract add constraint FK_contributes foreign key (id_employee)
      references employee (id_employee) on delete restrict on update restrict;

alter table employee add constraint FK_occupies foreign key (id_post)
      references Entity_14 (id_post) on delete restrict on update restrict;

alter table period add constraint FK_contains2 foreign key (id_extra_cost)
      references Entity_16 (id_extra_cost) on delete restrict on update restrict;

alter table period add constraint FK_contains3 foreign key (id_tax_land)
      references "Платеж на земельный налог" (id_tax_land) on delete restrict on update restrict;

alter table period add constraint FK_contains6 foreign key (id_services)
      references "Платеж на услуги" (id_services) on delete restrict on update restrict;

alter table period add constraint FK_determines foreign key (id_rent)
      references "Платеж на аренду помещения" (id_rent) on delete restrict on update restrict;

alter table period add constraint FK_determines1 foreign key (id_contract)
      references contract (id_contract) on delete restrict on update restrict;

alter table period add constraint FK_determines2 foreign key (id_fine)
      references "Платеж на пени" (id_fine) on delete restrict on update restrict;

alter table period add constraint FK_determines4 foreign key (id_equipment)
      references "Платеж на аренду оборудования" (id_equipment) on delete restrict on update restrict;

alter table period add constraint FK_determines5 foreign key (id_balance)
      references Entity_17 (id_balance) on delete restrict on update restrict;

alter table "Платеж на аренду оборудования" add constraint FK_contains4 foreign key (id_period)
      references period (id_period) on delete restrict on update restrict;

alter table "Платеж на аренду помещения" add constraint FK_contains foreign key (id_period)
      references period (id_period) on delete restrict on update restrict;

alter table "Платеж на земельный налог" add constraint FK_determines7 foreign key (id_period)
      references period (id_period) on delete restrict on update restrict;

alter table "Платеж на пени" add constraint FK_contains1 foreign key (id_period)
      references period (id_period) on delete restrict on update restrict;

alter table "Платеж на услуги" add constraint FK_determines6 foreign key (id_period)
      references period (id_period) on delete restrict on update restrict;

alter table Территория add constraint FK_indicates foreign key (id_contract)
      references contract (id_contract) on delete restrict on update restrict;

alter table заключает add constraint FK_concludes foreign key (id_renter)
      references renter (id_renter) on delete restrict on update restrict;

alter table заключает add constraint FK_concludes2 foreign key (id_contract)
      references contract (id_contract) on delete restrict on update restrict;


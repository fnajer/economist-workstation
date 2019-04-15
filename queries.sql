CREATE TABLE RENTER(ID INT PRIMARY KEY AUTO_INCREMENT,
   NAME VARCHAR(30) NOT NULL,
  SURNAME VARCHAR(30) NOT NULL,
  PATRONYMIC VARCHAR(30) NOT NULL,
ADDRESS  VARCHAR(255) NOT NULL,
BIRTHDAY VARCHAR(30) NOT NULL,
PERSON  VARCHAR(30) NOT NULL
);

DROP TABLE IF EXISTS RENTER;

CREATE TABLE RENTER(ID INT PRIMARY KEY AUTO_INCREMENT,
   NAME VARCHAR(30) NOT NULL
);

CREATE TABLE BUILDING(ID INT PRIMARY KEY AUTO_INCREMENT,
   "TYPE" VARCHAR(30) NOT NULL,
  SQUARE FLOAT NOT NULL,
  cost_balance FLOAT NOT NULL,
cost_residue  FLOAT NOT NULL
);

UPDATE RENTER
SET name=?, surname=?,name=?, patronymic=?, address=?, birthday=?, person=?
WHERE id=?;

CREATE TABLE CONTRACT(ID INT PRIMARY KEY AUTO_INCREMENT,
  date_start date NOT NULL,
  date_end date NOT NULL,
  id_renter int NOT NULL,
  id_building int NOT NULL,
  FOREIGN KEY (id_renter) REFERENCES RENTER(ID),
  FOREIGN KEY (id_building) REFERENCES BUILDING(ID)
);

INSERT INTO CONTRACT VALUES(null, '2018-11-01', '2019-11-01', 2, 2);

DROP TABLE IF EXISTS CONTRACT;

SELECT * FROM CONTRACT,RENTER, BUILDING WHERE CONTRACT.id=1 AND CONTRACT.id_renter=RENTER.id AND CONTRACT.id_building=BUILDING.id;

CREATE TABLE MONTH(ID INT PRIMARY KEY AUTO_INCREMENT,
  number int NOT NULL,
  date date NOT NULL,
  cost double NOT NULL,
  fine double NOT NULL,
  cost_water double NOT NULL,
  cost_electricity double NOT NULL,
  cost_heading double NOT NULL,
  paid_rent boolean NOT NULL,
  paid_communal boolean NOT NULL,
  id_contract int NOT NULL,
  index_water double NOT NULL,
  index_electricity double NOT NULL,
  index_heading double NOT NULL,
  FOREIGN KEY (id_contract) REFERENCES CONTRACT(ID)
);

ALTER TABLE Month RENAME COLUMN INDEX_HEADING to tariff_HEADING;

ALTER TABLE Month ADD INDEX_COST double not null default(0) AFTER cost;

ALTER TABLE Month DROP COLUMN INDEX_COST;

UPDATE MONTH
SET index_cost= 0;

ALTER TABLE MONTH 
ALTER COLUMN INDEX_COST double NOT NULL;

SELECT * FROM MONTH 
LEFT JOIN CONTRACT ON MONTH.ID_CONTRACT=CONTRACT.id 
LEFT JOIN RENTER ON CONTRACT.ID_RENTER=RENTER.id
LEFT JOIN BUILDING ON CONTRACT.ID_BUILDING=BUILDING.id
WHERE date >= '2019-05-01' AND date < '2019-06-01'
ORDER BY id_contract
тоже самое
SELECT * FROM MONTH "
+ "LEFT JOIN CONTRACT ON MONTH.ID_CONTRACT=CONTRACT.id "
+ "LEFT JOIN RENTER ON CONTRACT.ID_RENTER=RENTER.id "
+ "LEFT JOIN BUILDING ON CONTRACT.ID_BUILDING=BUILDING.id "
+ "WHERE date = '" + month + "' "
+ "OR date > '" + month + "' AND date < '" + nextMonth + "' "
+ "ORDER BY id_contract;);

SELECT * FROM MONTH 
LEFT JOIN CONTRACT ON MONTH.ID_CONTRACT=CONTRACT.id 
LEFT JOIN RENTER ON CONTRACT.ID_RENTER=RENTER.id
LEFT JOIN BUILDING ON CONTRACT.ID_BUILDING=BUILDING.id
WHERE date = '2019-05-01' OR date > '2019-06-01' AND date < '2019-07-01' 
ORDER BY id_contract

ALTER TABLE MONTH RENAME TO PERIOD

ALTER TABLE PERIOD DROP COLUMN NAME1, NAME2

CREATE TABLE RENT(id INT PRIMARY KEY AUTO_INCREMENT,
cost double,
index_cost double,
paid_rent double,
date_paid_rent date
);
CREATE TABLE FINE(id INT PRIMARY KEY AUTO_INCREMENT,
fine double,
paid_fine double,
date_paid_fine date
);
CREATE TABLE TAXLAND(id INT PRIMARY KEY AUTO_INCREMENT,
tax_land double,
paid_tax_land double,
date_paid_tax_land date
);
CREATE TABLE EQUIPMENT(id INT PRIMARY KEY AUTO_INCREMENT,
cost_equipment double,
paid_equipment double,
date_paid_equipment date
);
CREATE TABLE SERVICES(id INT PRIMARY KEY AUTO_INCREMENT,
count_water double,
tariff_water double,
count_electricity double,
tariff_electricity double,
cost_meter_heading double,
cost_meter_garbage double,
cost_internet double,
cost_telephone double,
paid_services double,
date_paid_services date
);
CREATE TABLE EXTRACOST(id INT PRIMARY KEY AUTO_INCREMENT,
extra_cost_rent double,
extra_cost_services double,
extra_cost_tax_land double,
extra_cost_equipment double,
);
CREATE TABLE BALANCE(id INT PRIMARY KEY AUTO_INCREMENT,
debit_rent double,
credit_rent double,
debit_fine double,
credit_fine double,
debit_tax_land double,
credit_tax_land double,
debit_equipment double,
credit_equipment double,
debit_services double,
credit_services double
);

SELECT * FROM PERIOD 
LEFT JOIN RENT ON PERIOD.ID_RENT=RENT.id
LEFT JOIN FINE ON PERIOD.ID_FINE=FINE.id
LEFT JOIN TAXLAND ON PERIOD.ID_TAX_LAND=TAXLAND.id
LEFT JOIN SERVICES ON PERIOD.ID_SERVICES=SERVICES.id
LEFT JOIN EQUIPMENT ON PERIOD.ID_EQUIPMENT=EQUIPMENT.id
WHERE id_contract=50 ORDER BY number

ALTER TABLE PERIOD 
DROP CONSTRAINT ID_RENT

ALTER TABLE PERIOD 
ADD CONSTRAINT ID_RENT
    FOREIGN KEY (`ID_RENT`) REFERENCES RENT (`id`) ON DELETE CASCADE; 

select distinct constraint_name from information_schema.constraints 
where table_name='PERIOD'  and column_list='ID_RENT'
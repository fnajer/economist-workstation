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

UPDATE CONTRACT SET NUM ='545' WHERE ID=52;

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
	
ALTER TABLE RENT
ADD CONSTRAINT ID_RENT
    FOREIGN KEY (`ID_RENT`) REFERENCES PERIOD(`id`) ON DELETE CASCADE; 

ALTER TABLE FINE  
DROP CONSTRAINT ID_FINE

select distinct constraint_name from information_schema.constraints 
where table_name='PERIOD'  and column_list='ID_RENT'

update RENT  
set RENT.index_inflation = 10
where exists
(select * from RENT 
LEFT JOIN PERIOD ON PERIOD.id=RENT.id_rent
WHERE date_end > '01-01-19' AND date_end <= '01-02-19')

select * from rent left join period on period.id=rent.id_rent LEFT JOIN CONTRACT ON CONTRACT.id=PERIOD.id_contract LEFT JOIN BUILDING ON BUILDING.id=CONTRACT.id_building

update RENT  
set RENT.index_inflation = 12
where exists
(select * from RENT 
LEFT JOIN PERIOD ON PERIOD.id=RENT.id_rent
LEFT JOIN CONTRACT ON CONTRACT.id=PERIOD.id_contract 
LEFT JOIN BUILDING ON BUILDING.id=CONTRACT.id_building
WHERE date_end > '2019-09-01' AND date_end <= '2019-10-01'
AND BUILDING.id = 2)

PreparedStatement ps = db.conn.prepareStatement("UPDATE " + typeTable
+ " SET " + typeField + "=? "
+ "WHERE EXISTS "
+ "(SELECT date_end FROM RENT "
+ "LEFT JOIN PERIOD ON PERIOD.id=RENT.id_rent "
+ "LEFT JOIN CONTRACT ON CONTRACT.id=PERIOD.id_contract "
+ "LEFT JOIN BUILDING ON BUILDING.id=CONTRACT.id_building "
+ "WHERE date_end > '" + dateStart + "' "
+ "AND date_end <= '"+ dateEnd + "' "
+ (idBuilding == -1 ? "" : "AND BUILDING.id=" + idBuilding) + ")",
Statement.RETURN_GENERATED_KEYS);

select * from period
LEFT JOIN CONTRACT ON PERIOD.id_contract=CONTRACT.id
LEFT JOIN RENT ON PERIOD.ID=RENT.id_rent
WHERE date_end > '2019-09-01' AND date_end <= '2019-11-01'

select CONTRACT.id, SUM(cost) from CONTRACT
LEFT JOIN PERIOD ON PERIOD.id_contract=CONTRACT.id
LEFT JOIN RENT ON PERIOD.ID=RENT.id_rent
WHERE date_end > '2019-09-01' AND date_end <= '2019-11-01'
GROUP BY CONTRACT.id


select CONTRACT.id, CONTRACT.date_start, num, PERIOD.date_end,
group_concat(cost) as cost, group_concat(index_inflation) as index_cost, SUM(fine) as fine, SUM(fine) as fine, SUM(tax_land) as tax_land, SUM(cost_equipment) as cost_equipment, 
SUM(cost_meter_heading) as cost_meter_heading, SUM(cost_meter_garbage) as cost_meter_garbage, SUM(cost_internet) as cost_internet, SUM(cost_telephone) as cost_telephone, 
group_concat(count_water) as count_water, group_concat(tariff_water) as tariff_water, group_concat(count_electricity) as count_electricity, group_concat(tariff_electricity) as tariff_electricity,
SUM(paid_rent) as paid_rent, SUM(paid_fine) as paid_fine, SUM(paid_tax_land) as paid_tax_land, SUM(paid_equipment) as paid_equipment, SUM(paid_services) as paid_services
from CONTRACT
LEFT JOIN PERIOD ON PERIOD.id_contract=CONTRACT.id
LEFT JOIN RENT ON PERIOD.ID=RENT.id_rent
LEFT JOIN FINE ON PERIOD.id=FINE.id_fine
LEFT JOIN TAXLAND ON PERIOD.id=TAXLAND.id_tax_land
LEFT JOIN EQUIPMENT ON PERIOD.id=EQUIPMENT.id_equipment
LEFT JOIN SERVICES ON PERIOD.id=SERVICES.id_services
WHERE date_end > '2019-09-01' AND date_end <= '2019-11-01'
GROUP BY CONTRACT.id, PERIOD.id
ORDER BY id_contract
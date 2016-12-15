SELECT *
  FROM 
-- warning: it does not reset sequence table, since its existence depends on the database engine and 
-- the chosen strategy by the JPA implementation
delete from SALEPRODUCT;
delete from SALE;
delete from CUSTOMER;
delete from PRODUCT;
delete from DISCOUNT;
delete from UNIT;
INSERT INTO DISCOUNT (ID, DTYPE, DESCRIPTION, PERCENTAGE, THRESHOLD) VALUES (1, 'NoDiscount', 'Sem desconto', 0, 0);
INSERT INTO DISCOUNT (ID, DTYPE, DESCRIPTION, PERCENTAGE, THRESHOLD) VALUES (2, 'ThresholdPercentageDiscount', 'Percentagem do Total (acima de limiar)', 0.1, 50);
INSERT INTO DISCOUNT (ID, DTYPE, DESCRIPTION, PERCENTAGE, THRESHOLD) VALUES (3, 'EligibleProductsDiscount', 'Percentagem do Total Elegivel', 0.1, 0);
INSERT INTO UNIT (ID, DESCRIPTION, ABBREVIATION) VALUES (1, 'Quilogramas', 'Kg');
INSERT INTO UNIT (ID, DESCRIPTION, ABBREVIATION) VALUES (2, 'Unidades', 'un');
INSERT INTO PRODUCT (ID, PRODCOD, DESCRIPTION, FACEVALUE, QTY, DISCOUNTELIGIBILITY, UNIT_ID) VALUES (1, 123, 'Prod 1', 100, 500, 0, 1);
INSERT INTO PRODUCT (ID, PRODCOD, DESCRIPTION, FACEVALUE, QTY, DISCOUNTELIGIBILITY, UNIT_ID) VALUES (2, 124, 'Prod 2', 35, 1000, 1, 2);
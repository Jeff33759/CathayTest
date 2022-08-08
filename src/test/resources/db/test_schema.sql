CREATE TABLE currency
(
	id IDENTITY PRIMARY KEY,
	code NVARCHAR NOT NULL UNIQUE ,
	chn_name NVARCHAR NOT NULL
);

INSERT INTO currency (code, chn_name) VALUES
	('EUR','歐元'),
	('GBP','英鎊'),
	('USD','美元')
;
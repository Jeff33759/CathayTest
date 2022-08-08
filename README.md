# CathayTest

## DEMO說明

預設RUN在8080Port。

為了方便DEMO，把資料存放於記憶體，因此測試端不需要自己新建任何表，直接啟動就能用，缺點就是關掉APP，資料會歸零。

H2-console訪問路徑：http://localhost:8080/h2-console



程式啟動時，會執行以下SQL：

CREATE TABLE currency

(

  id IDENTITY PRIMARY KEY,
	
  code NVARCHAR NOT NULL UNIQUE ,
	
  chn_name NVARCHAR NOT NULL

);

INSERT INTO currency (code, chn_name) VALUES

  ('EUR','歐元'),
	
  ('GBP','英鎊'),
	
  ('USD','美元');

SQL檔案於:

CathayTest/src/test/resources/db/test_schema.sql


<br><br>
## 我做了什麼

1. RESTful Api。
2. 幣別與中文名稱表的CRUD。
3. 指定API的呼叫，以及組成一支新的API，內有期望的時間格式與中文名稱。
4. 使用AOP將例外回應處理的邏輯從各業務邏輯中抽離出來，實現RESTful Api。
5. 使用MockMvc做整合測試(題目要求的六項測試)


package cathaybk.util;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import cathaybk.config.MyComponentConfig;
import cathaybk.exception.CyApiException;
import cathaybk.model.dto.receive.CoindeskData;
import cathaybk.model.dto.receive.CoindeskData.Bpi.EUR;
import cathaybk.model.dto.receive.CoindeskData.Bpi.GBP;
import cathaybk.model.dto.receive.CoindeskData.Bpi.USD;
import cathaybk.model.dto.send.QueryCyRes;
import cathaybk.model.dto.send.QueryCyRes.Currency;

@Component
public class MyUtil {

	@Autowired
	private DateTimeFormatter timeFormatter;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objMapper;
	
	/**
	 * 將ISO格式時間，轉換成預期的yyyy/MM/dd HH:mm:ss格式。
	 * 
	 * {@link MyComponentConfig #dateTimeFormatter}
	 * */
	public String formatISOTimeStrToExpected(String ISOStr) {
		Instant ins = Instant.parse(ISOStr);
		return timeFormatter.format(ins);
	}
	
	/**
	 * @throws CyApiException 當跟Coindesk Api要資料或處理資料的過程中出錯時拋出
	 * */
	public CoindeskData getDataFromCoindeskApiAndConvertToObj() 
			throws CyApiException {
		try {
			String coindeskApiUrl = 
					"https://api.coindesk.com/v1/bpi/currentprice.json";
			String data = 
					restTemplate.getForObject(coindeskApiUrl, String.class);
			return objMapper.readValue(data,CoindeskData.class);
		}catch(Exception e) {
			String msg = "Get data from Coindesk Api or format data to expected failed.";
			throw new CyApiException(msg,e);
		}
	}
	
	/**
	 * 生成轉換日期格式完成的回應物件。
	 * */
	public QueryCyRes genQueryCyResWithCoindeskData(CoindeskData data) {

		QueryCyRes res = new QueryCyRes();
		
		String expectedTime = 
				formatISOTimeStrToExpected(data.getTime().getUpdatedISO());
		res.setUpdated(expectedTime);
		
		List<Currency> cyArr = new ArrayList<QueryCyRes.Currency>();
		EUR eur = data.getBpi().getEur();
		QueryCyRes.Currency eurRes = res.new Currency();
		eurRes.setCode(eur.getCode());
		eurRes.setRate(eur.getRate());
		eurRes.setDescription(eur.getDescription());
		cyArr.add(eurRes);
		
		GBP gbp = data.getBpi().getGbp();
		QueryCyRes.Currency gbpRes = res.new Currency();
		gbpRes.setCode(gbp.getCode());
		gbpRes.setRate(gbp.getRate());
		gbpRes.setDescription(gbp.getDescription());
		cyArr.add(gbpRes);
		
		USD usd = data.getBpi().getUsd();
		QueryCyRes.Currency usdRes = res.new Currency();
		usdRes.setCode(usd.getCode());
		usdRes.setRate(usd.getRate());
		usdRes.setDescription(usd.getDescription());
		cyArr.add(usdRes);
		
		res.setCyArr(cyArr);
		
		return res;
	}
	
}

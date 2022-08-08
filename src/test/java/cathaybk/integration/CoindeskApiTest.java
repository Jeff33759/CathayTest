package cathaybk.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;

import cathaybk.model.dto.receive.CoindeskData;
import cathaybk.util.MyUtil;

public class CoindeskApiTest extends BaseTest{
	
	@Autowired
	protected MyUtil util;
	
	
	@BeforeEach
	protected void preprocessingForEachTestCase() {
    }
	
	/**
	 * 5.測試呼叫 coindesk API，並顯示其內容。
	 * */
	@Test
	public void testCallToTheCoindeskApi() {
		CoindeskData data = 
				util.getDataFromCoindeskApiAndConvertToObj();
		try {
			String print = objectMapper.writeValueAsString(data);
			System.out.println("CoindeskApi res:\n" + print);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 6.測試呼叫資料轉換的 API，並顯示其內容。
	 * */
	@Test
	public void readCurrency() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/cy")
                .headers(mockHttpHeaders);

		mockMvc.perform(requestBuilder)
		.andExpect(status().isOk())                
		.andExpect(jsonPath("$.updated").exists())
		.andExpect(jsonPath("$.cyArr[0].code").value("EUR"))
		.andExpect(jsonPath("$.cyArr[0].chn_name").value("歐元"))
		.andExpect(jsonPath("$.cyArr[0].description").value("Euro"))
		.andExpect(jsonPath("$.cyArr[0].rate").exists()) 
		.andExpect(jsonPath("$.cyArr[1].code").value("GBP"))
		.andExpect(jsonPath("$.cyArr[1].chn_name").value("英鎊"))
		.andExpect(jsonPath("$.cyArr[1].description").value("British Pound Sterling"))
		.andExpect(jsonPath("$.cyArr[1].rate").exists()) 
		.andExpect(jsonPath("$.cyArr[2].code").value("USD"))
		.andExpect(jsonPath("$.cyArr[2].chn_name").value("美元"))
		.andExpect(jsonPath("$.cyArr[2].description").value("United States Dollar"))
		.andExpect(jsonPath("$.cyArr[2].rate").exists()) 
		.andDo(print());
	}

	
	


}

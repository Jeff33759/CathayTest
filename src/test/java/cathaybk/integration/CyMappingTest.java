package cathaybk.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CyMappingTest extends BaseTest{

	/**
	 * 1.測試呼叫查詢幣別對應表資料 API，並顯示其內容。
	 * */
	@Test
	public void testReadCurrencyMapping() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/cy/mapping")
                .headers(mockHttpHeaders);

		mockMvc.perform(requestBuilder)
		.andExpect(status().isOk())                
		.andExpect(jsonPath("$.cyMapArr[0].id").value(1))
        .andExpect(jsonPath("$.cyMapArr[0].code").value("EUR"))
        .andExpect(jsonPath("$.cyMapArr[0].chn_name").value("歐元"))
		.andExpect(jsonPath("$.cyMapArr[1].id").value(2))
		.andExpect(jsonPath("$.cyMapArr[1].code").value("GBP"))
		.andExpect(jsonPath("$.cyMapArr[1].chn_name").value("英鎊"))
		.andExpect(jsonPath("$.cyMapArr[2].id").value(3))
		.andExpect(jsonPath("$.cyMapArr[2].code").value("USD"))
		.andExpect(jsonPath("$.cyMapArr[2].chn_name").value("美元"))
		.andDo(print());
		printCyMapping();
	}
	
	/**
	 * 2.測試呼叫新增幣別對應表資料 API。
	 * */
	@Test
	public void testCreateCurrencyMapping() throws Exception {
		
		JSONObject reqBody = new JSONObject()
                .put("code", "TWD")
                .put("chn_name", "新台幣");		
        
		RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/cy/mapping")
                .headers(mockHttpHeaders)
                .content(reqBody.toString());
		
	
		mockMvc.perform(requestBuilder)
		.andExpect(status().isCreated())                
		.andExpect(jsonPath("$.id").value(4))
        .andExpect(jsonPath("$.code").value("TWD"))
        .andExpect(jsonPath("$.chn_name").value("新台幣"))
		.andDo(print());
		
		printCyMapping();
	}
	
	/**
	 * 3.測試呼叫更新幣別對應表資料 API，並顯示其內容。
	 * */
	@Test
	public void testUpdateCurrencyMapping() throws Exception {
		
		
		JSONObject reqBody = new JSONObject()
				.put("code", "IDR")
				.put("chn_name", "印尼盾");		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/cy/mapping/3")
				.headers(mockHttpHeaders)
				.content(reqBody.toString());
		
		mockMvc.perform(requestBuilder)
		.andExpect(status().isOk())                
		.andExpect(jsonPath("$.id").value(3))
		.andExpect(jsonPath("$.code").value("IDR"))
		.andExpect(jsonPath("$.chn_name").value("印尼盾"))
		.andDo(print());
		
		printCyMapping();
	}
	
	/**
	 * 4.測試呼叫刪除幣別對應表資料 API。
	 * */
	@Test
	public void testDeleteCurrencyMapping() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/cy/mapping/2")
				.headers(mockHttpHeaders);
		
		mockMvc.perform(requestBuilder)
		.andExpect(status().isNoContent())
		.andDo(print());
		
		printCyMapping();
	}
	
	
	
}

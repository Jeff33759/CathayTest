package cathaybk.integration;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cathaybk.dao.MyCurrencyRepo;
import cathaybk.model.po.MyCurrency;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {
	
    @Autowired
    protected MyCurrencyRepo cyRepo;
    
    @Autowired
    protected MockMvc mockMvc;
    
    @Autowired
    protected ObjectMapper objectMapper;
    
    @Autowired
    protected Map<String,String> cyNameMap;
    

    protected HttpHeaders mockHttpHeaders;
	
    
	
	@BeforeEach
    public void initHttpHeader() {
		mockHttpHeaders = new HttpHeaders();
		mockHttpHeaders.add(HttpHeaders.CONTENT_TYPE, 
				MediaType.APPLICATION_JSON_VALUE);
		addDataIntoDBForDemo();
    }
	
	
    

	@AfterEach
	protected void postprocessingForEachTestCase() {
		cyRepo.truncateTable();
		cyNameMap.clear();
    }
	

	protected void addDataIntoDBForDemo() {
		MyCurrency cy1 = new MyCurrency();
		cy1.setCode("EUR");
		cy1.setChn_name("歐元");
		MyCurrency cy2 = new MyCurrency();
		cy2.setCode("GBP");
		cy2.setChn_name("英鎊");
		MyCurrency cy3 = new MyCurrency();
		cy3.setCode("USD");
		cy3.setChn_name("美元");
		
		cyRepo.save(cy1);
		cyRepo.save(cy2);
		cyRepo.save(cy3);
		cyNameMap.put(cy1.getCode(), cy1.getChn_name());
		cyNameMap.put(cy2.getCode(), cy2.getChn_name());
		cyNameMap.put(cy3.getCode(), cy3.getChn_name());
	}
	
	protected void printCyMapping() {
		List<MyCurrency> cyArr = cyRepo.findAll();
		try {
			String print = 
					objectMapper.writeValueAsString(cyArr);
			System.out.println("DB result:" + print);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

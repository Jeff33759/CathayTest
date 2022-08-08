package cathaybk.integration;

import java.util.List;

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
    }
	

	protected void addDataIntoDBForDemo() {
		MyCurrency cy1 = new MyCurrency();
		cy1.setId(1);
		cy1.setCode("EUR");
		cy1.setChn_name("歐元");
		MyCurrency cy2 = new MyCurrency();
		cy2.setId(2);
		cy2.setCode("GBP");
		cy2.setChn_name("英鎊");
		MyCurrency cy3 = new MyCurrency();
		cy3.setId(3);
		cy3.setCode("USD");
		cy3.setChn_name("美元");
		
		cyRepo.save(cy1);
		cyRepo.save(cy2);
		cyRepo.save(cy3);
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

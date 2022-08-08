package cathaybk.config;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class MyComponentConfig {

	@Bean
	public ObjectMapper objectMapperBean() {
		return new ObjectMapper();
	}
	
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
				.setConnectTimeout(Duration.ofSeconds(3)) 
				.setReadTimeout(Duration.ofSeconds(3))
				.build();
    }
    
    @Bean
    public DateTimeFormatter dateTimeFormatter() {
    	return DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
	            .withZone(ZoneId.from(ZoneOffset.UTC));
    }
    
    /**
     * 當APP啟動完畢時，將資料庫裡的幣別對應中文名稱放進此單例元件，
     * 以後取資料就拿這裡的，加快查詢速度。
     * 當有人使用了CUD的操作時，都要重新刷新此MAP。
     * */
    @Bean
    public Map<String,String> currencyNameMap(){
    	return new HashMap<String,String>();
    }
	
}

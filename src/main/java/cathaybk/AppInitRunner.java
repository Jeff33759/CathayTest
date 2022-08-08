package cathaybk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cathaybk.service.CurrencyService;
import cathaybk.serviceimpl.CurrencyServiceImpl;

@Component
public class AppInitRunner implements CommandLineRunner{
	
	private static final Logger log = 
			LoggerFactory.getLogger(AppInitRunner.class);
	
	private CurrencyService cyService;
	
	@Autowired
	public AppInitRunner(CurrencyServiceImpl cyServiceImpl) {
		this.cyService = cyServiceImpl;
	}

	@Override
	public void run(String... args) throws Exception {
		cyService.initCyNameMap();
		log.info("All my components have been initialized, "
				+ "App started successfully!");
	}
	
}

package cathaybk.service;

import java.util.List;
import java.util.Map;

import cathaybk.model.dto.receive.MyCyMappingReq;
import cathaybk.model.dto.send.QueryCyRes;
import cathaybk.model.po.MyCurrency;

public interface CurrencyService {
	
	public QueryCyRes readCurrency() throws Exception; 

	public Map<String,List<MyCurrency>> readCurrencyMapping() throws Exception; 

	public MyCurrency createCurrencyMapping(MyCyMappingReq newCy) throws Exception; 
	
	public MyCurrency updateCurrencyMapping(Integer id,MyCyMappingReq newCy) throws Exception; 

	public void deleteCurrencyMapping(Integer id) throws Exception; 

	public void initCyNameMap();
	
}

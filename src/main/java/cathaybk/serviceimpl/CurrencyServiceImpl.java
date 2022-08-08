package cathaybk.serviceimpl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cathaybk.dao.MyCurrencyRepo;
import cathaybk.exception.MyWriteDBException;
import cathaybk.model.dto.receive.CoindeskData;
import cathaybk.model.dto.receive.MyCyMappingReq;
import cathaybk.model.dto.send.QueryCyRes;
import cathaybk.model.po.MyCurrency;
import cathaybk.service.CurrencyService;
import cathaybk.util.MyDtoConverter;
import cathaybk.util.MyUtil;

@Service
public class CurrencyServiceImpl implements CurrencyService{
	

	@Autowired
	private MyUtil myUtil;
	
	@Autowired
	private MyDtoConverter myConverter;
	
	@Autowired
	private MyCurrencyRepo MyCyRepo;
	
	@Autowired
	private Map<String,String> cyNameMap;
	
	/**
	 * 幣別中文名稱，從cyNameMap取，不每次都從資料庫取，增加效率。
	 * */
	@Override
	public QueryCyRes readCurrency() throws Exception{
		CoindeskData dataObj = 
				myUtil.getDataFromCoindeskApiAndConvertToObj();
		QueryCyRes res = 
				myUtil.genQueryCyResWithCoindeskData(dataObj);
		res.getCyArr().forEach(cy->{
			String cyName = cy.getCode();
			if(cyNameMap.containsKey(cyName)) {
				cy.setChn_name(cyNameMap.get(cyName));
			}else {
				cy.setChn_name("unknown");
			}
		});
		
		return res;
	}
	

	@Override
	public Map<String,List<MyCurrency>> readCurrencyMapping() throws Exception {
		List<MyCurrency> cyMapArr = MyCyRepo.findAll();
		Map<String,List<MyCurrency>> res = 
				new HashMap<String,List<MyCurrency>>();
		res.put("cyMapArr", cyMapArr);
		return res;
	}
	
	
	/**
	 * 回應就不再用DTO了，節省時間；對於資料庫新增時發生的錯誤案例也不細分，
	 * 通通拋出同一例外，去做後續處理。
	 * */
	@Override
	public MyCurrency createCurrencyMapping(MyCyMappingReq newCy) 
			throws Exception {
		try {
			MyCurrency cyPO = myConverter.convertCyReqToPo(newCy);
			MyCurrency saveData = MyCyRepo.save(cyPO);
			initCyNameMap();
			return saveData;
		}catch(Exception e) {
			String msg = "Some error occurred when adding data to DB, "
					+ "maybe the 'code' field already has the same data in the DB.";
			throw new MyWriteDBException(msg,e);
		}
	}

	
	@Override
	public MyCurrency updateCurrencyMapping(Integer id, MyCyMappingReq newCy) 
			throws Exception {
		if(MyCyRepo.existsById(id)) {
			try {
				MyCurrency cyPO = myConverter.convertCyReqToPo(newCy);
				cyPO.setId(id);
				MyCurrency saveData = MyCyRepo.saveAndFlush(cyPO);
				initCyNameMap();
				return saveData;
			}catch(Exception e) {
				String msg = "Some error occurred when updating data to DB, "
						+ "maybe the 'code' field already has the same data in the DB.";
				throw new MyWriteDBException(msg,e);
			}
		}else {
			String msg = "The 'id' does not exist in database.";
			throw new MyWriteDBException(msg);
		}
	}
	
	@Override
	public void deleteCurrencyMapping(Integer id) throws Exception {
		if(MyCyRepo.existsById(id)) {
			MyCyRepo.deleteById(id);
			initCyNameMap();
		}else {
			String msg = "The 'id' does not exist in database.";
			throw new MyWriteDBException(msg);
		}		
	}
	
	
	@Override
	public void initCyNameMap() {
		cyNameMap.clear();
		List<MyCurrency> nameMappingList = MyCyRepo.findAll();
		nameMappingList.forEach(myCy->{
			cyNameMap.put(myCy.getCode(), myCy.getChn_name());
		});
	}

	
}

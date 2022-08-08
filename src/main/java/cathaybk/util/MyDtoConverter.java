package cathaybk.util;

import org.springframework.stereotype.Component;

import cathaybk.model.dto.receive.MyCyMappingReq;
import cathaybk.model.po.MyCurrency;

/**
 * 處理一些DTO與PO的轉換。
 * */
@Component
public class MyDtoConverter {
	
	public MyCurrency convertCyReqToPo(MyCyMappingReq req) {
		MyCurrency cyPO = new MyCurrency();
		cyPO.setChn_name(req.getChn_name());
		cyPO.setCode(req.getCode());
		return cyPO;
	}

}

package cathaybk.model.dto.receive;

import javax.validation.constraints.NotNull;

/**
 * 與PO的{@link MyCurrency}解耦。
 * */
public class MyCyMappingReq {
	
	@NotNull(message = "The 'code' field cannot be empty.")
	private String code;
	
	@NotNull(message = "The 'chn_name' field cannot be empty.")
	private String chn_name;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChn_name() {
		return chn_name;
	}

	public void setChn_name(String chn_name) {
		this.chn_name = chn_name;
	}
	

}

package cathaybk.model.dto.send;

import java.util.List;

public class QueryCyRes {
	
	private List<Currency> cyArr;
	
	private String updated;
	
	
	public List<Currency> getCyArr() {
		return cyArr;
	}


	public void setCyArr(List<Currency> cyArr) {
		this.cyArr = cyArr;
	}


	public String getUpdated() {
		return updated;
	}


	public void setUpdated(String updated) {
		this.updated = updated;
	}


	
	
	public class Currency{
		
		private String code;
		
		private String chn_name;
		
		private String description;
		
		private String rate;
		

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}


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

		public String getRate() {
			return rate;
		}

		public void setRate(String rate) {
			this.rate = rate;
		}
		
	}

}

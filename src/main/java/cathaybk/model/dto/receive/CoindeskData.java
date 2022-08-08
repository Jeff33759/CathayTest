package cathaybk.model.dto.receive;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 承接CoindeskApi的DTO。
 * */
public class CoindeskData {
	
	private String chartName;
	
	private String disclaimer;
	
	private Bpi bpi;
	
	private CoindeskTime time;
	
	
	public Bpi getBpi() {
		return bpi;
	}

	public void setBpi(Bpi bpi) {
		this.bpi = bpi;
	}

	public CoindeskTime getTime() {
		return time;
	}

	public void setTime(CoindeskTime time) {
		this.time = time;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}


	/**
	 * 因為原始資料bpi欄位的值並非JSON Array，而是JSON Obj，
	 * 所以不能保證USD、GBP、EUR三個幣別的欄位會永遠一樣，
	 * 因此這裡分別做成了三個內部類別。
	 * */
	public class Bpi {
		
		@JsonProperty("USD")
		private USD usd;
		
		@JsonProperty("GBP")
		private GBP gbp;

		@JsonProperty("EUR")
		private EUR eur;
		

		public USD getUsd() {
			return usd;
		}

		public void setUsd(USD usd) {
			this.usd = usd;
		}

		public GBP getGbp() {
			return gbp;
		}

		public void setGbp(GBP gbp) {
			this.gbp = gbp;
		}

		public EUR getEur() {
			return eur;
		}

		public void setEur(EUR eur) {
			this.eur = eur;
		}

		public class USD {
			
			private String code;
			
			private String symbol;
			
			private String rate;
			
			private String description;
			
			private float rate_float;
			

			public String getCode() {
				return code;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public String getSymbol() {
				return symbol;
			}

			public void setSymbol(String symbol) {
				this.symbol = symbol;
			}

			public String getRate() {
				return rate;
			}

			public void setRate(String rate) {
				this.rate = rate;
			}

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public float getRate_float() {
				return rate_float;
			}

			public void setRate_float(float rate_float) {
				this.rate_float = rate_float;
			}
		}
		
		public class GBP {
			
			private String code;
			
			private String symbol;
			
			private String rate;
			
			private String description;
			
			private float rate_float;
			
			
			public String getCode() {
				return code;
			}
			
			public void setCode(String code) {
				this.code = code;
			}
			
			public String getSymbol() {
				return symbol;
			}
			
			public void setSymbol(String symbol) {
				this.symbol = symbol;
			}
			
			public String getRate() {
				return rate;
			}
			
			public void setRate(String rate) {
				this.rate = rate;
			}
			
			public String getDescription() {
				return description;
			}
			
			public void setDescription(String description) {
				this.description = description;
			}
			
			public float getRate_float() {
				return rate_float;
			}
			
			public void setRate_float(float rate_float) {
				this.rate_float = rate_float;
			}
		}
		
		public class EUR {
			
			private String code;
			
			private String symbol;
			
			private String rate;
			
			private String description;
			
			private float rate_float;
			
			
			public String getCode() {
				return code;
			}
			
			public void setCode(String code) {
				this.code = code;
			}
			
			public String getSymbol() {
				return symbol;
			}
			
			public void setSymbol(String symbol) {
				this.symbol = symbol;
			}
			
			public String getRate() {
				return rate;
			}
			
			public void setRate(String rate) {
				this.rate = rate;
			}
			
			public String getDescription() {
				return description;
			}
			
			public void setDescription(String description) {
				this.description = description;
			}
			
			public float getRate_float() {
				return rate_float;
			}
			
			public void setRate_float(float rate_float) {
				this.rate_float = rate_float;
			}
		}
		
	} 
	
	
	public class CoindeskTime {
		
		private String updated;

		private String updatedISO;
		
		private String updateduk;
		

		public String getUpdated() {
			return updated;
		}

		public void setUpdated(String updated) {
			this.updated = updated;
		}

		public String getUpdatedISO() {
			return updatedISO;
		}

		public void setUpdatedISO(String updatedISO) {
			this.updatedISO = updatedISO;
		}

		public String getUpdateduk() {
			return updateduk;
		}

		public void setUpdateduk(String updateduk) {
			this.updateduk = updateduk;
		}

	}
	
}

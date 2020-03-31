package com.atpco.audit.model;

public class QueryTax {

	private String sales_curr;
	private String tax_code;
	private double BTW_AMT;

	public QueryTax() {

	}

	public QueryTax(String sales_curr, String tax_code, double BTW_AMT) {
		this.sales_curr = sales_curr;
		this.tax_code = tax_code;
		this.BTW_AMT = BTW_AMT;
	}

	public String getSales_curr() {
		return sales_curr;
	}

	public void setSales_curr(String salesCurr) {
		sales_curr = salesCurr;
	}

	public String getTax_code() {
		return tax_code;
	}

	public void setTax_code(String taxCode) {
		tax_code = taxCode;
	}

	public double getBTW_AMT() {
		return BTW_AMT;
	}

	public void setBTW_AMT(double bTWAMT) {
		BTW_AMT = bTWAMT;
	}

}

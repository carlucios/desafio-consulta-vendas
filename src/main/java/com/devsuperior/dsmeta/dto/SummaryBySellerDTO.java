package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SummaryBySellerProjection;

public class SummaryBySellerDTO {

	private String sellerName;
	private Double total;

	public SummaryBySellerDTO(String sellerName, Double total) {
		this.sellerName = sellerName;
		this.total = total;
	}

	public SummaryBySellerDTO(SummaryBySellerProjection projection) {
		sellerName = projection.getSellerName();
		total = projection.getTotal();
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}
}

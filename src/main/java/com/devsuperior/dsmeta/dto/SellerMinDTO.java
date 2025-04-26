package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

public class SellerMinDTO {
	
	private String sellerName;
	private Double total;
	
	SellerMinDTO() {
		
	}

	public SellerMinDTO(String sellerName, Double total) {
		this.sellerName = sellerName;
		this.total = total;
	}
	
	public SellerMinDTO(Sale entity) {
		sellerName = entity.getSeller().getName();
		total = entity.getAmount();
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}

}

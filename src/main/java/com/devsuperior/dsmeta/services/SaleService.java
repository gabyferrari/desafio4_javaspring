package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleMinDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) { 
		
		LocalDate initialDate = convertStringToLocalDate(minDate);
		LocalDate finalDate = convertStringToLocalDate(maxDate);
		
		if(initialDate == null) {
			initialDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		if(finalDate == null) {	
			finalDate = LocalDate.now().minusYears(1L);
		}
			
		Page<Sale> result = repository.searchBySale(initialDate, finalDate, name, pageable);
		return result.map(x -> new SaleMinDTO(x));
	}
	
	public List<SellerMinDTO> getSummary(String minDate, String maxDate) {
		
		LocalDate initialDate = convertStringToLocalDate(minDate);
		LocalDate finalDate = convertStringToLocalDate(maxDate);
		
		if(initialDate == null) {
			initialDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		if(finalDate == null) {	
			finalDate = LocalDate.now().minusYears(1L);
		}
		
		List<SellerMinDTO> result = repository.searchBySeller(initialDate, finalDate);
		return result;
		
	}
	
	public Page<SaleMinDTO> getReportTwelveMonth(Pageable pageable) {
		
		LocalDate twelveMonthsAgo = LocalDate.now().minusMonths(12);
        
		Page<Sale> result = repository.findByDateAfter(twelveMonthsAgo, pageable);
		return result.map(x -> new SaleMinDTO(x));
	}
	
	public List<SellerMinDTO> getSummaryTwelveMonth() {
		
		LocalDate twelveMonthsAgo = LocalDate.now().minusMonths(12);
		LocalDate today = LocalDate.now();
		
		return repository.searchBySeller(twelveMonthsAgo, today);
		
	}
	
	private LocalDate convertStringToLocalDate(String dateString) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         return LocalDate.parse(dateString, formatter);
    }

}

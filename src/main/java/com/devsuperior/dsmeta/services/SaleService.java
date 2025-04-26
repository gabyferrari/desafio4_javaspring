package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
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
		
		//LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		
		LocalDate initialDate = convertStringToLocalDate(minDate);
		LocalDate finalDate = convertStringToLocalDate(maxDate);
		
		if(initialDate == null) {
			initialDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		if(finalDate == null) {	
			finalDate = LocalDate.now().minusYears(1L);
		}
		
		//return  repository.searchBySale(initialDate, finalDate, name, pageable);
		
//		LocalDate finalDate = maxDate.equals("") ? today : LocalDate.parse(maxDate);
//		LocalDate initialDate = minDate.equals("") ? finalDate.minusYears(1L) : LocalDate.parse(minDate);
//		
		Page<Sale> result = repository.searchBySale(initialDate, finalDate, name, pageable);
		return result.map(x -> new SaleMinDTO(x));
	}
	
//	public List<SaleMinDTO> getReportTwelveMonth() {
//		
//		LocalDate twelveMonthsAgo = LocalDate.now().minusMonths(12);
//        
//		List<Sale> result = repository.findByDateAfter(twelveMonthsAgo);
//		return result.stream() 
//			   .map(x -> new SaleMinDTO(x)).collect(Collectors.toList());
//	}
	
	public Page<SaleMinDTO> getReportTwelveMonth(Pageable pageable) {
		
		LocalDate twelveMonthsAgo = LocalDate.now().minusMonths(12);
        
		Page<Sale> result = repository.findByDateAfter(twelveMonthsAgo, pageable);
		return result.map(x -> new SaleMinDTO(x));
	}
	
	private LocalDate convertStringToLocalDate(String dateString) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         return LocalDate.parse(dateString, formatter);
    }
	
//	LocalDate dataFinal = finalDate.equals("") ? today : LocalDate.parse(finalDate);
//	LocalDate dataInitial = initialDate.equals("") ? dataFinal.minusYears(1L) : LocalDate.parse(initialDate);
	
//	LocalDate dataFinal = (finalDate == null || finalDate.equals("")) ? today : LocalDate.parse(finalDate);
//	LocalDate dataInitial = (initialDate == null || initialDate.equals("")) ? dataFinal.minusYears(1L) : LocalDate.parse(initialDate);

}

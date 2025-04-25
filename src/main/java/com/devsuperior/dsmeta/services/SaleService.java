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
	
	public Page<SaleMinDTO> getReport(String initialDate, String finalDate, String name, Pageable pageable) { 
		
		LocalDate dataInitial = convertStringToLocalDate(initialDate);
		LocalDate dataFinal = convertStringToLocalDate(finalDate);
		
		if(dataInitial == null) {
			 dataInitial = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		if(dataFinal == null) {	
			dataFinal = LocalDate.now().minusYears(1L);
		}
			
		return  repository.searchBySale(dataInitial, dataFinal, name, pageable);
	}
	
	private LocalDate convertStringToLocalDate(String dateString) {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         return LocalDate.parse(dateString, formatter);
    }

}

package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {	
	
	@Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller "  
	        + "WHERE obj.date BETWEEN :minDate AND :maxDate " 
	        + "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))",
	        countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller " )
	Page<Sale> searchBySale(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
	
	Page<Sale> findByDateAfter(LocalDate date, Pageable pageable);
	
	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.SellerMinDTO(obj.seller.name, SUM(obj.amount)) "
			+ "FROM Sale obj "  
	        + "WHERE obj.date BETWEEN :minDate AND :maxDate "
	        + "GROUP BY obj.seller.name")
	List<SellerMinDTO> searchBySeller(LocalDate minDate, LocalDate maxDate);
	
	List<SellerMinDTO> findByDateAfter(LocalDate date);

	
}

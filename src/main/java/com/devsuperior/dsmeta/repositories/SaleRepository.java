package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
			+ "FROM Sale obj "
			+ "WHERE obj.date BETWEEN :initialDate AND :finalDate "
			+ "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%')) "
			+ "ORDER BY obj.date DESC")
	Page<SaleMinDTO> searchBySale(@Param("initialDate") LocalDate initialDate, 
								  @Param("finalDate") LocalDate finalDate, 
								  @Param("name") String name, Pageable pageable);

}

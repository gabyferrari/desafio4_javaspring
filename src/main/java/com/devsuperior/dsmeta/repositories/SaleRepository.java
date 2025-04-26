package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
//	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
//			+ "FROM Sale obj "
//			+ "WHERE obj.date BETWEEN :initialDate AND :finalDate "
//			+ "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%')) "
//			+ "ORDER BY obj.date DESC")
//	Page<SaleMinDTO> searchBySale(@Param("initialDate") LocalDate initialDate, 
//								  @Param("finalDate") LocalDate finalDate, 
//								  @Param("name") String name, Pageable pageable);
	
//	@Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller "  
//	        + "WHERE (:initialDate IS NULL OR obj.date >= :initialDate) " 
//	        + "AND (:finalDate IS NULL OR obj.date <= :finalDate) " 
//	        + "AND (:name IS NULL OR LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%')))",
//	        countQuery = "SELECT obj FROM Sale obj JOIN FETCH obj.seller " 
//	        		+ "WHERE (:initialDate IS NULL OR obj.date >= :initialDate) " 
//	        		+ "AND (:finalDate IS NULL OR obj.date <= :finalDate) "
//	        		+ "AND (:name IS NULL OR LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%')))")
//	Page<Sale> searchBySale(LocalDate initialDate, LocalDate finalDate, String name, Pageable pageable);

	
//	@Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller "  
//	        + "WHERE obj.date >= :initialDate " 
//	        + "AND obj.date <= :finalDate " 
//	        + "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))",
//	        countQuery = "SELECT obj FROM Sale obj JOIN FETCH obj.seller " 
//	        		+ "WHERE obj.date >= :initialDate "
//	        		+ "AND obj.date <= :finalDate "
//	        		+ "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))")
//	Page<Sale> searchBySale(LocalDate initialDate, LocalDate finalDate, String name, Pageable pageable);
	
	@Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller "  
	        + "WHERE obj.date BETWEEN :minDate AND :maxDate " 
	        + "AND LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))",
	        countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller " )
	Page<Sale> searchBySale(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
	
//	List<Sale> findByDateAfter(LocalDate date);
	
	Page<Sale> findByDateAfter(LocalDate date, Pageable pageable);

	
}

package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleWithSellerNameDTO;
import com.devsuperior.dsmeta.dto.SummaryBySellerDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleWithSellerNameDTO(obj.id, obj.date, obj.amount, obj.seller.name) " +
            "FROM Sale obj " +
            "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))" +
            "AND obj.date BETWEEN :minDate AND :maxDate")
    List<SaleWithSellerNameDTO> searchSaleWithFilters(LocalDate minDate, LocalDate maxDate, String name);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SummaryBySellerDTO(obj.seller.name, SUM(obj.amount)) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.seller.name ")
    List<SummaryBySellerDTO> searchSummaryBySeller(LocalDate minDate, LocalDate maxDate);
}

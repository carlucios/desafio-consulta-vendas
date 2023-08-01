package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleWithSellerNameDTO;
import com.devsuperior.dsmeta.dto.SummaryBySellerDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleWithSellerNameProjection;
import com.devsuperior.dsmeta.projections.SummaryBySellerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true,
            value = "SELECT tb_sales.id, tb_sales.date, tb_sales.amount, tb_seller.name AS sellerName " +
            "FROM tb_sales " +
            "LEFT JOIN tb_seller ON tb_seller.id = tb_sales.seller_id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(tb_seller.name) LIKE UPPER(CONCAT('%',:name,'%')) ",
            countQuery = "SELECT COUNT(1) " +
            "FROM tb_sales " +
            "LEFT JOIN tb_seller ON tb_seller.id = tb_sales.seller_id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(tb_seller.name) LIKE UPPER(CONCAT('%',:name,'%')) ")
    Page<SaleWithSellerNameProjection> searchSaleWithFilters(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query(nativeQuery = true,
            value="SELECT tb_seller.name AS sellerName, SUM(tb_sales.amount) AS total " +
            "FROM tb_sales " +
            "LEFT JOIN tb_seller ON tb_seller.id = tb_sales.seller_id " +
            "WHERE tb_sales.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY tb_seller.name ")
    List<SummaryBySellerProjection> searchSummaryBySeller(LocalDate minDate, LocalDate maxDate);
}

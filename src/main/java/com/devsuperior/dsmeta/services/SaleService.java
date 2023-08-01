package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleWithSellerNameDTO;
import com.devsuperior.dsmeta.dto.SummaryBySellerDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleWithSellerNameProjection;
import com.devsuperior.dsmeta.projections.SummaryBySellerProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<SaleWithSellerNameDTO> searchSaleWithFilters(String minDateStr, String maxDateStr, String name, Pageable pageable) {
		LocalDate minDate;
		LocalDate maxDate;
		Page<SaleWithSellerNameProjection> result;

		if (Objects.equals(maxDateStr, "")) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			maxDate = today;
		} else {
			maxDate = LocalDate.parse(maxDateStr);
		}
		if (Objects.equals(minDateStr, "")) {
			minDate = maxDate.minusYears(1L);
		} else {
			minDate = LocalDate.parse(minDateStr);
		}

		result = repository.searchSaleWithFilters(minDate, maxDate, name, pageable);

		return result.map(SaleWithSellerNameDTO::new);
	}

	@Transactional(readOnly = true)
	public List<SummaryBySellerDTO> searchSummaryBySeller(String minDateStr, String maxDateStr) {
		LocalDate minDate;
		LocalDate maxDate;
		if (Objects.equals(maxDateStr, "")) {
			LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			maxDate = today;
		} else {
			maxDate = LocalDate.parse(maxDateStr);
		}
		if (Objects.equals(minDateStr, "")) {
			minDate = maxDate.minusYears(1L);
		} else {
			minDate = LocalDate.parse(minDateStr);
		}

		List<SummaryBySellerProjection> result = repository.searchSummaryBySeller(minDate, maxDate);
		return result.stream().map(SummaryBySellerDTO::new).collect(Collectors.toList());
	}
}

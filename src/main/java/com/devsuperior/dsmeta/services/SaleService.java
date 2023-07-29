package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleWithSellerNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

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
	public List<SaleWithSellerNameDTO> searchSaleWithFilters(String minDateStr, String maxDateStr, String name) {
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

		return repository.searchSaleWithFilters(minDate, maxDate, name);
	}
}

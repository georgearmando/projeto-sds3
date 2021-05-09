package com.devsuperior.dsvendas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsvendas.dto.SaleDTO;
import com.devsuperior.dsvendas.entities.Sale;
import com.devsuperior.dsvendas.repositories.SaleRepository;
import com.devsuperior.dsvendas.repositories.SellerRepository;

@Service
public class SaleService {
	
	@Autowired // Faz com que a instância seja injectada automaticamente pelo framework
	private SaleRepository repository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	// Essa solução é particular para situações em que o número de 'sellers' é conhecido e muito pequeno
	// Para projectos maiores existem outras abordagens
	// Antes de fazer a busca pelas vendas, ele salva os sellers na memória
	// Isto para não fazer uma consulta na BD para pegar cada seller
	// Busca Paginada
	
	@Transactional(readOnly = true)
	public Page<SaleDTO> findAll(Pageable pageable) {
		sellerRepository.findAll();
		Page<Sale> result = repository.findAll(pageable);
		return result.map(x -> new SaleDTO(x));
	}
}

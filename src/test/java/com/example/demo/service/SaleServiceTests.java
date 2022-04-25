package com.example.demo.service;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.Sale;
import com.example.demo.entity.SaleItem;
import com.example.demo.model.CountSumSaleModel;
import com.example.demo.model.SaleByCustomerModel;
import com.example.demo.model.SaleByProductModel;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SaleItemRepository;
import com.example.demo.repository.SaleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
class SaleServiceTests {

	@InjectMocks
	SaleService saleService;

	@Mock
	SaleRepository saleRepository;

	@Mock
	SaleItemRepository saleItemRepository;

	@Mock
	ProductRepository productRepository;

	@Mock
	CustomerRepository customerRepository;

	private SaleByCustomerModel saleByCustomerModelDefault = DefaultClassesCreator.createDefaultSalesByCustomerModel();
	private SaleByProductModel saleByProductModelDefault = DefaultClassesCreator.createDefaultSaleByProductModel();
	private CountSumSaleModel countSumSaleModelDefault = DefaultClassesCreator.createDefaultCountSumSaleModel();

	private SaleItem saleItemDefault = DefaultClassesCreator.createDefaultSaleItem();
	private Sale saleDefault = DefaultClassesCreator.createDefaultSale(saleItemDefault);
	private Customer customerDefault = DefaultClassesCreator.createDefaultCustomer();
	private Product productDefault = DefaultClassesCreator.createDefaultProduct();

	@Test
	void getSalesByCustomersTest(){

		given(customerRepository.findAll()).willReturn(Arrays.asList(customerDefault));
		given(productRepository.findAll()).willReturn(Arrays.asList(productDefault));
		given(saleItemRepository.countSumSalesByProductCustomerAndBetween(productDefault, customerDefault,
				LocalDateTime.parse("2010-01-03T11:31:51"), LocalDateTime.parse("2010-01-03T11:31:51")))
				.willReturn(countSumSaleModelDefault);

		List<SaleByCustomerModel> saleByCustomerModels = saleService.getSalesByCustomers(LocalDateTime.parse("2010-01-03T11:31:51"), LocalDateTime.parse("2010-01-03T11:31:51"), 1l);
		assertEquals(saleByCustomerModels.get(0).getCustomerName(), saleByCustomerModelDefault.getCustomerName());
	}


}

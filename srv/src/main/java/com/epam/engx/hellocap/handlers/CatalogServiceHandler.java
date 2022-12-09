package com.epam.engx.hellocap.handlers;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.sap.cds.services.cds.CdsService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.ServiceName;

import cds.gen.catalogservice.CatalogService_;
import cds.gen.catalogservice.Books;

@Component
@ServiceName(CatalogService_.CDS_NAME)
public class CatalogServiceHandler implements EventHandler {

	@After(event = CdsService.EVENT_READ)
	public void discountBooks(Stream<Books> books) {
		books
				.filter(this::discountEligible)
				.forEach(this::discontBooks);
	}

	private boolean discountEligible(Books books) {
		return books.getTitle() != null
				&& books.getStock() != null
				&& books.getStock() > 200;
	}

	private void discontBooks(Books books) {
		books.setTitle(books.getTitle() + " (discounted)");
	}
}
package com.cart.model;

import java.util.ArrayList;

public class PaginationResponse {

	
	private int currentPage;
	private long totalElements;
	private int numberPages;
	private int sizePage;
	private Object response;
	
	
	
	public PaginationResponse(int currentPage, int sizePage) {
		super();
		this.currentPage = currentPage;
		this.sizePage = sizePage;
		this.totalElements = 0;
		this.numberPages = 0;
		this.response = new ArrayList<>();
	}

	public int getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public long getTotalElements() {
		return totalElements;
	}
	
	public void setTotalElements(long pageElements) {
		this.totalElements = pageElements;
	}
	
	public int getNumberPages() {
		return numberPages;
	}
	
	public void setNumberPages(int numberPages) {
		this.numberPages = numberPages;
	}
	
	public Object getResponse() {
		return response;
	}
	
	public void setResponse(Object response) {
		this.response = response;
	}

	public int getSizePage() {
		return sizePage;
	}

	public void setSizePage(int maxSizePage) {
		this.sizePage = maxSizePage;
	}	
}

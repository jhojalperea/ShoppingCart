package com.cart.enumerator;

public enum StatusEnum {

	USED("Usado"),
	NEW("Nuevo");
	
	private String status;

	private StatusEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public static StatusEnum getEnumByStatus( String status ){
		for( StatusEnum e :  StatusEnum.values() ) {
			if( e.getStatus().equalsIgnoreCase( status ) ) {
				return e;
			}
		}
		return null;
	}
}

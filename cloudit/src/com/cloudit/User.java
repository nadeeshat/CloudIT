package com.cloudit;

class User {
	
	String id;
	String name;
	String imageUrl;
	String email;
	String idToken;
	String qrCode;
	
	public User(String id, String name, String imageUrl, String email, String idToken, String qrCode) {

		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
		this.email = email;
		this.idToken = idToken;
		this.qrCode = qrCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdToken() {
		return idToken;
	}
	
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	
	public String getQrCode() {
		return qrCode;
	}

}

package com.wbs.ginos;

class Product {
	public boolean selected;
	public String productname;
	public Double price;

	public Product(String productname, Double price) {
		this.price = price;
		this.productname = productname;
		selected=false;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public void toggleChecked() {
		if(selected==true)
			selected=false;
		else
			selected=true;
	}
}
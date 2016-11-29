package entity;

import java.io.Serializable;

public class Good implements Serializable{
	private int id;
	private String name;
	private float price;
	private int amount;
	
	public Good(int id, String name, float price, int amount) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "购物车内商品：[id=" + id + ", name=" + name + ", price=" + price
				+ ", amount=" + amount + "]";
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}

	public int getAmount() {
		return amount;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	

}

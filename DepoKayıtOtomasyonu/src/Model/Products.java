package Model;

public class Products {
	
	private int ID;
	private String Name;
	private int Stock;
	private int Category;
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String Name) {
		this.Name = Name;
	}
	public int getStock() {
		return Stock;
	}
	public void setStock(int Stock) {
		this.Stock = Stock;
	}
	public int getCategory() {
		return Category;
	}
	public void setCategory(int Category) {
		this.Category = Category;
	}

}

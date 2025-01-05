package Model;

public class Categories {
	
	private int ID;
	private String Name;
	
	public Categories(int id, String name) {
		this.ID = id;
		this.Name = name;
	}
	
	public Categories() {
		
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	

}

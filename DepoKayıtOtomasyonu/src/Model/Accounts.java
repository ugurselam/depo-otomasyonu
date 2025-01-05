package Model;

public class Accounts {
	
	public static String[] loginUser = new String[6];
	
	private int ID;
	private String accountName;
	private String password;
	private String email;
	private int Role;
	
	
	public int getRole() {
		return Role;
	}
	public void setRole(int role) {
		Role = role;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}

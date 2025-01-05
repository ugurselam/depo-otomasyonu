package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Accounts;
import Utils.MessageModals;

public class AccountsDAL extends dbConnection {
	
	private Connection connection;
	
	public AccountsDAL() {
		this.connection = getConnection();
	}

	// Accounts Metotları
	
	public void insertAccount(Accounts account) {
        String sql = "INSERT INTO Accounts (AccountName, Password, Email, Role) values (?, ?, ?, ?)";
        try {
        	PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, account.getAccountName());
            pstmt.setString(2, account.getPassword());
            pstmt.setString(3, account.getEmail());
            pstmt.setInt(4, account.getRole());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	System.err.println("Hesap Ekleme Hatası Oluştu");
        	e.printStackTrace();
        }
	}
	
	public List<String[]> readAccount() {
		String sql = "SELECT * FROM Accounts"; 
		List<String[]> accounts = new ArrayList<>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String[] account = new String[5];
				account[0] = String.valueOf(rs.getInt("ID"));
				account[1] = rs.getString("AccountName");
				account[2] = rs.getString("Password");
				account[3] = rs.getString("Email");
				account[4] = rs.getString("Role");
				
				accounts.add(account);
			}
			
			return accounts;
			
		} catch (Exception e) {
			MessageModals.ErrorMessage("Hesap listelenirken hata oluştu!", "Sistem Hatası");
		}
		
		return null;
	}
	
	public Accounts loginAccount(String accountName, char[] charPassword) {
		String sql = "SELECT * FROM Accounts WHERE AccountName=? and Password=?";
		Accounts findAccount = new Accounts();
		String password = "";
		
		for (char item : charPassword) {
		  password += item;	
		}
		
		try {
        	PreparedStatement pstmt = connection.prepareStatement(sql);
        	pstmt.setString(1, accountName);
        	pstmt.setString(2, password);
        	
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				findAccount.setID(rs.getInt("ID"));
				findAccount.setAccountName(rs.getString("AccountName"));
				findAccount.setPassword(rs.getString("Password"));
				findAccount.setEmail(rs.getString("Email"));
				findAccount.setRole(rs.getInt("Role"));
				return findAccount;
			}
		} catch (Exception e) {
			MessageModals.ErrorMessage("Hesap bulunurken hata oluştu!", "Sistem Hatası");
		}
		
		return null;
	}
	
	public Accounts findAccount(String accountName) {
		String sql = "SELECT * FROM Accounts WHERE AccountName=?";
		Accounts findAccount = new Accounts();
		
		try {
        	PreparedStatement pstmt = connection.prepareStatement(sql);
        	pstmt.setString(1, accountName);
        	
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				findAccount.setID(rs.getInt("ID"));
				findAccount.setAccountName(rs.getString("AccountName"));
				findAccount.setPassword(rs.getString("Password"));
				findAccount.setEmail(rs.getString("Email"));
				findAccount.setRole(rs.getInt("Role"));
				return findAccount;
			}
			
		} catch (Exception e) {
			MessageModals.ErrorMessage("Hesap bulunurken hata oluştu!", "Sistem Hatası");
		}
		
		return null;
	}
	
	public void accountDelete(int id) {
		String sql = "DELETE FROM Accounts WHERE ID=?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		    pstmt.setInt(1, id); // AccountName parametresi
		    pstmt.executeUpdate();
		} catch (SQLException e) {
			MessageModals.ErrorMessage("Hesap silinirken hata oluştu!", "Sistem Hatası");
		}
	}
	
	public void accountUpdate(Accounts account) {
		String sql = "UPDATE Accounts SET AccountName=?, Password=?, Email=?, Role=? WHERE ID=?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		    pstmt.setString(1, account.getAccountName());
		    pstmt.setString(2, account.getPassword());
		    pstmt.setString(3, account.getEmail()); 
		    pstmt.setInt(4, account.getRole()); 
		    pstmt.setInt(5, Integer.valueOf(account.getID())); 
		    pstmt.executeUpdate();
		} catch (SQLException e) {
			MessageModals.ErrorMessage("Hesap güncellenirken hata oluştu!", "Sistem Hatası");
		}
	}
	
}

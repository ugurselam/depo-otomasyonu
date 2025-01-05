package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Roles;
import Utils.MessageModals;

public class RolesDAL extends dbConnection {

	private Connection connection;
	
	public RolesDAL() {
		this.connection = getConnection();
	}
	
	public void insertRole(Roles role) {
        String sql = "INSERT INTO Roles (Name, Permissions) values (?, ?)";
        try {
        	PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, role.getName());
            pstmt.setString(2, role.getPermissions());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	System.err.println("Rol Ekleme Hatası Oluştu");
            e.printStackTrace();
        }
	}
	
	public List<String[]> readRoles() {
		String sql = "SELECT * FROM Roles"; 
		List<String[]> roles = new ArrayList<>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String[] role = new String[3];
				role[0] = String.valueOf(rs.getInt("ID"));
				role[1] = rs.getString("Name");
				role[2] = rs.getString("Permissions");
				
				roles.add(role);
			}
			
			return roles;
			
		} catch (Exception e) {
			MessageModals.ErrorMessage("Roller listelenirken hata oluştu!", "Sistem Hatası");
		}
		
		return null;
	}
	
	public Roles findRole(int id) {
		String sql = "SELECT * FROM Roles WHERE ID=?";
		Roles findRole = new Roles();
		
		try {
        	PreparedStatement pstmt = connection.prepareStatement(sql);
        	pstmt.setInt(1, id);
        	
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				findRole.setID(rs.getInt("ID"));
				findRole.setName(rs.getString("Name"));
				findRole.setPermissions(rs.getString("Permissions"));
				return findRole;
			}
			
		} catch (Exception e) {
			MessageModals.ErrorMessage("Rol bulunurken hata oluştu!", "Sistem Hatası");
		}
		
		return null;
	}
	
	public void roleUpdate(Roles role) {
		String sql = "UPDATE Roles SET Name=?, Permissions=? WHERE ID=?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		    pstmt.setString(1, role.getName());
		    pstmt.setString(2, role.getPermissions());
		    pstmt.setInt(3, Integer.valueOf(role.getID())); 
		    pstmt.executeUpdate();
		} catch (SQLException e) {
			MessageModals.ErrorMessage("Rol güncellenirken hata oluştu!", "Sistem Hatası");
		}
	}
	
	public void roleDelete(int id) {
		String sql = "DELETE FROM Roles WHERE ID=?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		    pstmt.setInt(1, id); // AccountName parametresi
		    pstmt.executeUpdate();
		} catch (SQLException e) {
			MessageModals.ErrorMessage("Rol silinirken hata oluştu!", "Sistem Hatası");
		}
	}
	
}

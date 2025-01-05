package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Products;

public class ProductsDAL extends dbConnection {
	
	private Connection connection;
	
	public ProductsDAL() {
		this.connection = getConnection();
	}

	
	public void productDelete(int ID) {
		String sql = "DELETE FROM Products WHERE ID=?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		    pstmt.setInt(1, ID); // ProductsName parametresi
		    pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Ürün Silinirken Hata Oluştu.");
		}
	}

	public void insertProducts(Products products) {
	String sql = "INSERT INTO Products (Name, Stock, Category) values (?, ?, ?)";
    try {
    	PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, products.getName());
        pstmt.setInt(2, products.getStock());
        pstmt.setInt(3, products.getCategory());
        pstmt.executeUpdate();
    } catch (SQLException e) {
    	System.err.println("Ürün ekleme hatası oluştu.");
    }
}

	public void productsUpdate(Products Products) {
		String sql = "UPDATE Products SET Name=?, Stock=?, Category=? WHERE ID=?";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
		    pstmt.setString(1, Products.getName());
		    pstmt.setInt(2, Products.getStock());
		    pstmt.setInt(3, Products.getCategory()); 
		    pstmt.setInt(4, Integer.valueOf(Products.getID())); 
		    pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Hesap Güncellenirken Hata Oluştu - " + e.getMessage());
		    e.printStackTrace();
		}
		
	
	}
	
	public List<String[]> readProducts() {
		String sql = "SELECT * FROM Products"; 
		List<String[]> products = new ArrayList<>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String[] product = new String[4];
				product[0] = String.valueOf(rs.getInt("ID"));
				product[1] = rs.getString("Name");
				product[2] = rs.getString("Stock");
				product[3] = rs.getString("Category");
				
				products.add(product);
			}
		
			return products;
			
		} catch (Exception e) {
			System.err.println("Ürünler Listelenirken Hata Oluştu");
            return null;
		}
	}
}

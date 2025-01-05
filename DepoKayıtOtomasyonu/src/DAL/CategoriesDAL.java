package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Categories;

public class CategoriesDAL extends dbConnection {
	
		private Connection connection;
		
		
		public CategoriesDAL() {
			this.connection = getConnection();
		}
		
		public int AddCategories(Categories kategori) {
			String sql="INSERT INTO Categories (Name) values (?)";
			
			 try {
		        	PreparedStatement pstmt = connection.prepareStatement(sql);
		            pstmt.setString(1, kategori.getName());

		            
		            pstmt.executeUpdate();
		            return 1;
		        } catch (SQLException e) {
		        	System.err.println("Kategori ekleme hatası oluştu !! Lütfen tekrardan deneyiniz.");
		            e.printStackTrace();
		            return 0;
		        }
		}
		
		public int DeleteCategories(Categories kategori) {
			String sql="DELETE FROM Categories where Name=?";
			try {
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, kategori.getName());
				pstmt.executeUpdate();
				return 1;
			} catch (SQLException e) {
				System.err.println("Kategori silme hatası oluştu !! Lütfen tekrardan deneyiniz. ");
				e.printStackTrace();
				return 0;
			}
		}
		
		public List<String[]> readCategories(){
			String sql = "SELECT * FROM Categories"; 
			List<String[]> categories = new ArrayList<>();
			try {
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					String[] categorie = new String[2];
					categorie[0] = String.valueOf(rs.getInt("ID"));
					categorie[1] = rs.getString("Name");
					categories.add(categorie);
				}
			
				return categories;
				
			} catch (Exception e) {
				System.err.println("Kategoriler Listelenirken Hata Oluştu");
	            e.printStackTrace();
	            return null;
			}
		}
		
		public int UpdateCategories(Categories kategori) {
			String sql="UPDATE Categories set Name=? where ID=?";
			try {
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, kategori.getName());
				pstmt.setInt(2, kategori.getID());
				
				
				pstmt.executeUpdate();
				return 1;
			} catch (SQLException e) {
				System.err.println("Kategori güncelleme hatası oluştu !! Lütfen tekrardan deneyiniz.");
				e.printStackTrace();
				return 0;
			}
		}
}


package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
	
	private Connection connection;
    
    public Connection getConnection() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=DepoOtomasyonu; trustServerCertificate=true"; 

        try {
        	connection = DriverManager.getConnection(url, "sa", "147852369");
        	// sa ve yanındaki şifre bunlarıda doğru şekilde girmelisiniz.
            System.out.println("Bağlantı Başarılı!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Bağlantı Kapatıldı!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
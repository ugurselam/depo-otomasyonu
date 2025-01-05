package Utils;

import javax.swing.table.DefaultTableModel;

import DAL.AccountsDAL;
import DAL.CategoriesDAL;
import DAL.ProductsDAL;
import DAL.RolesDAL;

public class TableMetot {
	
	public DefaultTableModel table(String[] cols, int type) {
		DefaultTableModel model = new DefaultTableModel(cols, 0);		
		AccountsDAL accountList = new AccountsDAL();
		RolesDAL roleList = new RolesDAL();
		ProductsDAL productList = new ProductsDAL();
		CategoriesDAL categoryList = new CategoriesDAL();
        
		/* Diğer tabloya listeleme işlemleri için 
		 * 
		 case SayıYazın:
            for (String[] Değişkenİsmi : reader.ListelemeFonksiyonununİsmi()) {
                model.addRow(Değişkenİsmi);
            }
            break;
		 * 
		 * Her bir tablo listelemesi için case'i bir fazla yazın metotu çağırırkende 
		 * ona göre parametre vermeyi unutmayın.
		*/
		
		try {
	        switch (type) {
	        case 1:
	            for (String[] account : accountList.readAccount()) {
	                model.addRow(account);
	            }
	            break;
	        case 2: 
	            for (String[] role : roleList.readRoles()) {
	                model.addRow(role);
	            }
	            break;
	        case 3:
	            for (String[] product : productList.readProducts()) {
	                model.addRow(product);
	            }
	            break;
	        case 4:
	            for (String[] category : categoryList.readCategories()) {
	                model.addRow(category);
	            }
	            break;
	        default:
	            System.out.println("Geçersiz tip: " + type);
	        }
	        
	        return model;
		} catch (Exception e) {
			System.err.println("Veriler Tabloya Listelenirken Hata Oluştu");
            e.printStackTrace();
            return null;
		}
		
	}

}

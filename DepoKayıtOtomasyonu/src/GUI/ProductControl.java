package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import DAL.CategoriesDAL;
import DAL.ProductsDAL;
import Model.Products;
import Utils.MessageModals;
import Utils.TableMetot;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductControl {

	private JFrame frmrnYnetimi;
	private JTable table;
	private JTextField productNameTextField;
	private JTextField productStockTextField;

	/**
	 * Launch the application.
	 */
	
	public int id;

	/**
	 * Create the application.
	 */
	public ProductControl(JFrame menuForm) {
		initialize(menuForm);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame menuForm) {
		
		
		frmrnYnetimi = new JFrame();
		frmrnYnetimi.setTitle("Ürün Yönetimi");
		frmrnYnetimi.setBounds(100, 100, 485, 309);
		frmrnYnetimi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmrnYnetimi.getContentPane().setLayout(null);
		
        CategoriesDAL categoriesList = new CategoriesDAL();
		HashMap<Integer, String> categoryMap = new HashMap<>();
        
		JComboBox<Object> categoryComboBox = new JComboBox<Object>();
		categoryComboBox.setBounds(10, 144, 122, 22);
		
		for (String[] category : categoriesList.readCategories()) {
			categoryMap.put(Integer.valueOf(category[0]), category[1]);
		}
		
		for (String categoryName : categoryMap.values()) {
			categoryComboBox.addItem(categoryName);
		}
		
		frmrnYnetimi.getContentPane().add(categoryComboBox);
	
		
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
	                // Tıklanan hücrenin satır indekslerini al
	                int row = table.rowAtPoint(e.getPoint());

	                for(int i = 0; i < 4; i++) {
	                	Object value = table.getValueAt(row, i);
	                	if(i == 0) id = Integer.valueOf(value.toString());
	                	else if(i == 1) productNameTextField.setText(value.toString());
	                	else if(i == 2) productStockTextField.setText(value.toString());
	                	else if(i == 3) {
	                        Integer selectedID = Integer.valueOf(value.toString()); 
	                        String selectedName = categoryMap.get(selectedID);  
	                        categoryComboBox.setSelectedItem(selectedName); 
	                	}
	                	
	                }
	                
				} catch(Exception ex) {
					System.err.println("Tabloda tıklanan yerde veri yok!");
				}
			}
		});
		table.setBounds(164, 51, 276, 204);
		frmrnYnetimi.getContentPane().add(table);
		
        TableMetot productTable = new TableMetot();
		table.setModel(productTable.table(new String[] {"ID", "Name", "Stock", "Category"}, 3));
		
		JButton productsAddButton = new JButton("Ürün Ekle");
		productsAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductsDAL insertProduct = new ProductsDAL();
				boolean validation = true;
				
				if(productNameTextField.getText().length() == 0) {
					MessageModals.WarningMessage("Eklemek istediğiniz ürünün ismi boş olamaz!", "Doğrulama Hatası");
					validation = false;
				}
				
			    if(productStockTextField.getText().equals("") && validation == true) {
					MessageModals.WarningMessage("Eklemek istediğiniz ürünün stok sayısı 0 olamaz!", "Doğrulama Hatası");
					validation = false;
			    }
				
			    if(Integer.valueOf(productStockTextField.getText()) == 0 && validation == true) {
					MessageModals.WarningMessage("Eklemek istediğiniz ürünün stok sayısı 0 olamaz!", "Doğrulama Hatası");
					validation = false;
			    }
			    
			    if(categoryComboBox.getSelectedItem().toString() == "Kategori Seçiniz") {
					MessageModals.WarningMessage("Eklemek istediğiniz ürünün kategorisi olmalı!", "Doğrulama Hatası");
					validation = false;
			    }
			    
			    if(validation) {
					Products newProduct = new Products();
					
					// Kategori ID 'si bulma döngüsü
					String categoryName = (String) categoryComboBox.getSelectedItem();
					int categoryID = -1;
					
					for (Map.Entry<Integer, String> entry : categoryMap.entrySet()) {
					    if (entry.getValue().equals(categoryName)) {
					        categoryID = entry.getKey();  
					        break;  
					    }
					}
					  
					newProduct.setName(productNameTextField.getText());
					newProduct.setStock(Integer.valueOf(productStockTextField.getText()));
					newProduct.setCategory(categoryID);
					insertProduct.insertProducts(newProduct);
		    	    table.setModel(productTable.table(new String[] {"ID", "Name", "Stock", "Category"}, 3));
		            MessageModals.InformationMessage("Ürün ekleme işlemi başarılı bir şekilde gerçekleşti!", "İşlem Başarılı"); 
			    }
				
			}
		});
		productsAddButton.setBounds(10, 187, 122, 23);
		frmrnYnetimi.getContentPane().add(productsAddButton);
		
		JButton productDeleteButton = new JButton("Ürün Sil");
		productDeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ProductsDAL productDelete = new ProductsDAL();
					productDelete.productDelete(id);
					MessageModals.InformationMessage("Ürün başarıyla silindi!", "İşlem Başarılı");
				} catch (Exception e2) {
					MessageModals.ErrorMessage("Ürün silme işlemi başarısız oldu!", "Hata");
				}
			}
		});
		productDeleteButton.setBounds(10, 232, 122, 23);
		frmrnYnetimi.getContentPane().add(productDeleteButton);
		
		JButton productUpdateButton = new JButton("Ürün Güncelle");
		productUpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean updateValidation = true;
				
				if(productNameTextField.getText().length() == 0) {
					MessageModals.WarningMessage("Düzenlemek istediğiniz ürünün ismi boş olamaz", "Doğrulama Hatası");
					updateValidation = false;
				}
				
				try {
					Integer.valueOf(productStockTextField.getText());
				} catch (Exception e2) {
					MessageModals.ErrorMessage("Stok alanına harf yazmışsınız! lütfen sadece sayısal değer kullanınız!", "Doğrulama Hatası");
				}
				
				if(productStockTextField.getText().length() == 0 && updateValidation == true) {
					MessageModals.WarningMessage("Düzenlemek istediğiniz ürünün stoğu 0 olamaz", "Doğrulama Hatası");
					updateValidation = false;
				}
				
				if(Integer.valueOf(productStockTextField.getText()) == 0 && updateValidation == true) {
					MessageModals.WarningMessage("Düzenlemek istediğiniz ürünün stoğu 0 olamaz", "Doğrulama Hatası");
					updateValidation = false;
				}
				
				if(updateValidation) {
					
					// Kategori ID 'si bulma döngüsü
					String categoryName = (String) categoryComboBox.getSelectedItem();
					int categoryID = -1;
					
					for (Map.Entry<Integer, String> entry : categoryMap.entrySet()) {
					    if (entry.getValue().equals(categoryName)) {
					        categoryID = entry.getKey();  
					        System.out.println(entry.getValue() + " " + categoryName + " " + entry.getKey());
					        break;  
					    }
					}
					
					ProductsDAL productUpdate = new ProductsDAL();
				    Products product = new Products();
				    product.setID(id);
				    product.setName(productNameTextField.getText());
				    product.setStock(Integer.valueOf(productStockTextField.getText()));
				    product.setCategory(categoryID);
				    
				    productUpdate.productsUpdate(product);
				    
		    	    table.setModel(productTable.table(new String[] {"ID", "Name", "Stock", "Category"}, 3));
				    MessageModals.InformationMessage("Ürün güncelleme işlemi başarılı!", "İşlem Başarılı");
				}

			}
		});
		productUpdateButton.setBounds(10, 209, 122, 23);
		frmrnYnetimi.getContentPane().add(productUpdateButton);
		
		JLabel productAddHeaderLabel = new JLabel("Ürün Ekle");
		productAddHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		productAddHeaderLabel.setBounds(10, 11, 146, 14);
		frmrnYnetimi.getContentPane().add(productAddHeaderLabel);
		
		JLabel productNameLabel = new JLabel("Ürün Ad");
		productNameLabel.setBounds(10, 33, 80, 14);
		frmrnYnetimi.getContentPane().add(productNameLabel);
		
		productNameTextField = new JTextField();
		productNameTextField.setBounds(10, 48, 122, 20);
		frmrnYnetimi.getContentPane().add(productNameTextField);
		productNameTextField.setColumns(10);
		
		JLabel productStockLabel = new JLabel("Ürün Stok");
		productStockLabel.setBounds(10, 79, 80, 14);
		frmrnYnetimi.getContentPane().add(productStockLabel);
		
		productStockTextField = new JTextField();
		productStockTextField.setColumns(10);
		productStockTextField.setBounds(10, 94, 122, 20);
		frmrnYnetimi.getContentPane().add(productStockTextField);
		
		JLabel categoryLabel = new JLabel("Kategori");
		categoryLabel.setBounds(10, 125, 80, 14);
		frmrnYnetimi.getContentPane().add(categoryLabel);
		
		JButton goToMenuButton = new JButton("< Geri Dön");
		goToMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuForm.setVisible(true);
				frmrnYnetimi.dispose();
			}
		});
		goToMenuButton.setBounds(164, 17, 122, 23);
		frmrnYnetimi.getContentPane().add(goToMenuButton);
	}
	
	public void show(ProductControl window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frmrnYnetimi.setLocationRelativeTo(null);
					window.frmrnYnetimi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import DAL.CategoriesDAL;
import Model.Categories;
import Utils.MessageModals;
import Utils.TableMetot;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CategoriesControl {

	private JFrame frmKategoriYnetimi;
	private JTable table;
	private JTextField updateIdTextField;
	private JTextField categoryNameAddTextField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public CategoriesControl(JFrame menuForm) {
		initialize(menuForm);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame menuForm) {
		frmKategoriYnetimi = new JFrame();
		frmKategoriYnetimi.setTitle("Kategori Yönetimi");
		frmKategoriYnetimi.setBounds(100, 100, 627, 446);
		frmKategoriYnetimi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKategoriYnetimi.getContentPane().setLayout(null);
		
		
		table = new JTable();
		table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
	                // Tıklanan hücrenin satır indekslerini al
	                int row = table.rowAtPoint(e.getPoint());

	                // Döngü tıklanan satırdaki hücreleri tek tek gezer.
	                for(int i = 0; i < 2; i++) {
	                	Object value = table.getValueAt(row, i); // i ile bulunan indexteki veriyi value değişkenine atarız.
	                	if(i == 0) {
	                		updateIdTextField.setText(value.toString());
	                	}
	                	else if(i == 1) {
	                		categoryNameAddTextField.setText(value.toString());
	                	}
	                }
				} catch(Exception ex) {
					System.err.println("Tabloda tıklanan yerde veri yok!");
					
				}
			}
		});
		table.setBounds(297, 69, 272, 314);
		frmKategoriYnetimi.getContentPane().add(table);
		
		TableMetot categoriesTable = new TableMetot();
		table.setModel(categoriesTable.table(new String[] {"ID","Name"}, 4));
		
		JButton categoryAddButton = new JButton("Kategori Ekle");
		categoryAddButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		categoryAddButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean validation = true;
				
				if(categoryNameAddTextField.getText().length() == 0) {
					MessageModals.ErrorMessage("Eklemek istediğiniz kategorinin ismi boş olamaz!", "Doğrulama Hatası");
					validation = false;
				}
				
				if(validation) {
					CategoriesDAL kategoridal = new CategoriesDAL();
					Categories kategori = new Categories();
					
					kategori.setName(categoryNameAddTextField.getText());
					
					if(kategoridal.AddCategories(kategori) == 1) {
						MessageModals.InformationMessage("Kategori başarıyla eklendi", "Başarılı");
						table.setModel(categoriesTable.table(new String[] {"ID", "Name"}, 4));
	     			} else {
	     				MessageModals.ErrorMessage("Kategori eklenemedi", "Hata Mesajı");
	     			}	
				}
			}
		});
		categoryAddButton.setBounds(42, 225, 210, 29);
		frmKategoriYnetimi.getContentPane().add(categoryAddButton);
		
		JButton categoryDeleteButton = new JButton("Kategori Sil");
		categoryDeleteButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		categoryDeleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				boolean validation = true;
				
				if(categoryNameAddTextField.getText().length() == 0) {
					MessageModals.InformationMessage("Silmek istediğiniz kategoriyi tablodan seçmelisiniz!", "Doğrulama Hatası");
					validation = false;
				}
				
				if(validation) {
					CategoriesDAL kategoridal = new CategoriesDAL();
					Categories kategori = new Categories();
					
					kategori.setName(categoryNameAddTextField.getText());
					
					if(kategoridal.DeleteCategories(kategori) == 1) {
						MessageModals.InformationMessage("Kategori başarıyla silindi.", "Silme İşlemi Başarılı");
						table.setModel(categoriesTable.table(new String[] {"ID", "Name"}, 4));
						updateIdTextField.setText("");
						categoryNameAddTextField.setText("");
					} else {
						updateIdTextField.setText("");
						categoryNameAddTextField.setText("");
						MessageModals.ErrorMessage("Kategori silme işlemi başarısız oldu!! Lütfen tekrar deneyiniz. ", "Silme İşlemi Başarısız");
					}	
				}
			}
		});
		categoryDeleteButton.setBounds(42, 265, 210, 29);
		frmKategoriYnetimi.getContentPane().add(categoryDeleteButton);
		
		JButton updateCategories = new JButton("Kategori Güncelle");
		updateCategories.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean validation = true;
				
				if(updateIdTextField.getText().length() == 0) {
				   MessageModals.WarningMessage("Güncelleme işlemini gerçekleştirmeden önce tablodan veri seçmelisiniz!", "Uyarı");
				   validation = false;	
				}
				
				if(categoryNameAddTextField.getText().length() == 0 && validation == true) {
					   MessageModals.WarningMessage("Kategori güncelleme işlemini gerçekleştirmen için yeni bir kategori ismi girmelisin!", "Uyarı");
					   validation = false;	
				}
				
				if(validation) {
					CategoriesDAL kategoridal = new CategoriesDAL();
					Categories kategori = new Categories();
					kategori.setName(categoryNameAddTextField.getText());
					kategori.setID(Integer.valueOf(updateIdTextField.getText()));
					
					if(kategoridal.UpdateCategories(kategori) == 1) {
						MessageModals.InformationMessage("Kategori başarıyla güncellendi.", "Güncelleme İşlemi Başarılı");
						table.setModel(categoriesTable.table(new String[] {"ID", "Name"}, 4));
						updateIdTextField.setText("");
						categoryNameAddTextField.setText("");
					} else {
						updateIdTextField.setText("");
						categoryNameAddTextField.setText("");
						MessageModals.ErrorMessage("Kategori güncellenme işlemi başarısız oldu!! Lütfen tekrar deneyiniz. ", "Güncelleme İşlemi Başarısız");
					}					
				}

			}
		});
		updateCategories.setFont(new Font("Tahoma", Font.PLAIN, 16));
		updateCategories.setBounds(42, 305, 210, 29);
		frmKategoriYnetimi.getContentPane().add(updateCategories);
		
		JButton cancel = new JButton("< Geri Dön");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuForm.setVisible(true);
				frmKategoriYnetimi.dispose();
			}
		});
		cancel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cancel.setBounds(42, 345, 210, 29);
		frmKategoriYnetimi.getContentPane().add(cancel);
		
		updateIdTextField = new JTextField();
		updateIdTextField.setEnabled(false);
		updateIdTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		updateIdTextField.setBounds(42, 159, 210, 29);
		frmKategoriYnetimi.getContentPane().add(updateIdTextField);
		updateIdTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Tüm kategorilerin listesi aşağıdaki gibidir;");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(297, 23, 304, 35);
		frmKategoriYnetimi.getContentPane().add(lblNewLabel);
		
		categoryNameAddTextField = new JTextField();
		categoryNameAddTextField.setBounds(42, 94, 210, 29);
		frmKategoriYnetimi.getContentPane().add(categoryNameAddTextField);
		categoryNameAddTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Kategori Yönetimi");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(32, 11, 192, 19);
		frmKategoriYnetimi.getContentPane().add(lblNewLabel_1);
		
		JLabel categoryAddNameLabel = new JLabel("Kategori Ad");
		categoryAddNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		categoryAddNameLabel.setBounds(46, 69, 113, 14);
		frmKategoriYnetimi.getContentPane().add(categoryAddNameLabel);
		
		JLabel lblGncellenecekKategoriId = new JLabel("Güncellenecek Kategori ID");
		lblGncellenecekKategoriId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGncellenecekKategoriId.setBounds(42, 134, 210, 14);
		frmKategoriYnetimi.getContentPane().add(lblGncellenecekKategoriId);
		
	}
	
	public void show(CategoriesControl window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frmKategoriYnetimi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

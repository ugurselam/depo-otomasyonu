package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import DAL.RolesDAL;
import Model.Roles;
import Utils.MessageModals;
import Utils.TableMetot;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoleControl {

	private JFrame frmRolYnetimi;
	private JTable table;
	private JTextField roleNameTextField;

	public int id;
	
	/**
	 * Create the application.
	 */
	public RoleControl(JFrame menuForm) {
		initialize(menuForm);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame menuForm) {
		frmRolYnetimi = new JFrame();
		frmRolYnetimi.setTitle("Rol Yönetimi");
		frmRolYnetimi.setBounds(100, 100, 377, 303);
		frmRolYnetimi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRolYnetimi.getContentPane().setLayout(null);
		
		JCheckBox userControlCheckBox = new JCheckBox("Kullanıcı Yönetimi");
		userControlCheckBox.setBounds(20, 84, 149, 23);
		frmRolYnetimi.getContentPane().add(userControlCheckBox);
		
		JCheckBox roleControlCheckBox = new JCheckBox("Rol Yönetimi");
		roleControlCheckBox.setBounds(20, 110, 149, 19);
		frmRolYnetimi.getContentPane().add(roleControlCheckBox);
		
		JCheckBox categoryControlCheckBox = new JCheckBox("Kategori Yönetimi");
		categoryControlCheckBox.setBounds(20, 132, 149, 23);
		frmRolYnetimi.getContentPane().add(categoryControlCheckBox);
		
		JCheckBox productControlTextBox = new JCheckBox("Ürün Yönetimi");
		productControlTextBox.setBounds(20, 158, 149, 23);
		frmRolYnetimi.getContentPane().add(productControlTextBox);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String permissions = "";
				
				try {
	                // Tıklanan hücrenin satır indekslerini al
	                int row = table.rowAtPoint(e.getPoint());

	                // Döngü tıklanan satırdaki hücreleri tek tek gezer.
	                for(int i = 0; i < 3; i++) {
	                	Object value = table.getValueAt(row, i); // i ile bulunan indexteki veriyi value değişkenine atarız.
	                	if(i == 0) id = Integer.valueOf(value.toString());
	                	else if(i == 1) roleNameTextField.setText(value.toString());
	                	else if(i == 2) permissions = value.toString();
	                }
	                
	                userControlCheckBox.setSelected(false);
                	roleControlCheckBox.setSelected(false);
                	categoryControlCheckBox.setSelected(false);
                	productControlTextBox.setSelected(false);
	                
	                if(permissions.contains("A")) {
	                	userControlCheckBox.setSelected(true);
	                } 
	                
	                if(permissions.contains("B")) {
	                	roleControlCheckBox.setSelected(true);
	                } 
	                
	                if(permissions.contains("C")) {
	                	categoryControlCheckBox.setSelected(true);
	                }
	                
	                if(permissions.contains("D")) {
	                	productControlTextBox.setSelected(true);
	                }
	                
				} catch(Exception ex) {
					System.err.println("Tabloda tıklanan yerde veri yok!");
				}
			}
		});
		
        TableMetot roleTable = new TableMetot();
		table.setModel(roleTable.table(new String[] {"ID", "Name", "Permissions"}, 2));
		
		table.setBounds(175, 36, 152, 197);
		frmRolYnetimi.getContentPane().add(table);
		
		JLabel roleAddHeaderLabel = new JLabel("Rol Ekle");
		roleAddHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		roleAddHeaderLabel.setBounds(10, 11, 162, 19);
		frmRolYnetimi.getContentPane().add(roleAddHeaderLabel);
		
		JLabel roleNameLabel = new JLabel("Rol İsmi");
		roleNameLabel.setBounds(20, 41, 82, 14);
		frmRolYnetimi.getContentPane().add(roleNameLabel);
		
		roleNameTextField = new JTextField();
		roleNameTextField.setBounds(20, 57, 134, 20);
		frmRolYnetimi.getContentPane().add(roleNameTextField);
		roleNameTextField.setColumns(10);
		
		JButton roleAddBtn = new JButton("Rol Ekle");
		roleAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validation = true;
				String permissions = "";
				RolesDAL insertRole = new RolesDAL();
				
				if(roleNameTextField.getText().length() == 0) {
					MessageModals.ErrorMessage("Rol ismi boş olamaz lütfen tekrar deneyiniz!", "Doğrulama Hatası");
					validation = false;
				}
				
				if(userControlCheckBox.isSelected()) {
					permissions += "A";
				}
				
				if(roleControlCheckBox.isSelected()) {
					permissions += "B";
				}
				
				if(categoryControlCheckBox.isSelected()) {
					permissions += "C";
				}
				
				if(productControlTextBox.isSelected()) {
					permissions += "D";
				}
				
				if(permissions == "") {
					MessageModals.ErrorMessage("Yetkisiz rol oluşturamazsınız lütfen tekrar deneyiniz!", "Doğrulama Hatası");
					validation = false;
				}
				
				if(validation) {
				    Roles newRole = new Roles();
						  
				    newRole.setName(roleNameTextField.getText());
					newRole.setPermissions(permissions);
					insertRole.insertRole(newRole);
			        MessageModals.InformationMessage("Rol ekleme işlemi başarılı bir şekilde gerçekleşti!", "İşlem Başarılı"); 
					table.setModel(roleTable.table(new String[] {"ID", "Name", "Permissions"}, 2));
                }
			}
		});
		roleAddBtn.setBounds(20, 188, 108, 23);
		frmRolYnetimi.getContentPane().add(roleAddBtn);
		
		JButton goToMenuButton = new JButton("< Geri Dön");
		goToMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPenceresi menuForm = new MenuPenceresi();
				menuForm.show(menuForm);
				frmRolYnetimi.dispose();
			}
		});
		goToMenuButton.setBounds(175, 11, 152, 23);
		frmRolYnetimi.getContentPane().add(goToMenuButton);
		
		JButton btnRolGncelle = new JButton("Düzenle");
		btnRolGncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean updateValidation = true;
			    String permissions = "";
				
				if(roleNameTextField.getText().length() == 0) {
					MessageModals.ErrorMessage("Rol ismi boş bırakılamaz lütfen tekrar deneyiniz!", "Doğrulama Hatası");
					updateValidation = false;
				}
			    
				if(userControlCheckBox.isSelected()) {
					permissions += "A";
				}
				
				if(roleControlCheckBox.isSelected()) {
					permissions += "B";
				}
				
				if(categoryControlCheckBox.isSelected()) {
					permissions += "C";
				}
				
				if(productControlTextBox.isSelected()) {
					permissions += "D";
				}
			    
				if(permissions == "") {
					MessageModals.ErrorMessage("Yetkisiz rol olamaz lütfen tekrar deneyiniz!", "Doğrulama Hatası");
					updateValidation = false;
				}
				
				if(updateValidation) {
					RolesDAL roleUpdate = new RolesDAL();
				    Roles role = new Roles();

				    role.setID(Integer.valueOf(id));
				    role.setName(roleNameTextField.getText());
				    role.setPermissions(permissions);
				    
				    roleUpdate.roleUpdate(role);
					table.setModel(roleTable.table(new String[] {"ID", "Name", "Permissions"}, 2));
				   	MessageModals.InformationMessage("Rol bilgileri başarıyla güncellendi!", "İşlem Başarılı");
				}
			}
		});
		btnRolGncelle.setBounds(20, 210, 108, 23);
		frmRolYnetimi.getContentPane().add(btnRolGncelle);
		
		JButton btnSil = new JButton("Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(id != 0) {
					RolesDAL roleDelete = new RolesDAL();
					roleDelete.roleDelete(Integer.valueOf(id));
					table.setModel(roleTable.table(new String[] {"ID", "Name", "Permissions"}, 2));
					MessageModals.InformationMessage("Rol başarıyla silindi!", "İşlem Başarılı");
				} else {
					MessageModals.ErrorMessage("Silinmesi için tablodan bir rol seçmeniz gerekiyor!", "İşlem Hatası");
				}
			}
		});
		btnSil.setBounds(20, 230, 108, 23);
		frmRolYnetimi.getContentPane().add(btnSil);
		
	}
	
	public void show(RoleControl window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frmRolYnetimi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

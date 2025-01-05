package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import DAL.AccountsDAL;
import DAL.RolesDAL;
import Model.Accounts;
import Utils.MessageModals;
import Utils.OtherMetot;
import Utils.TableMetot;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

public class UserControl {

	private JFrame frmKullancYnetimi;
	private JTable table;
	private JTextField accountNameTextField;
	private JTextField emailTextField;
	private JTextField passwordTextField;
	private JTextField retryPasswordTextField;
	private JTextField controlNameTextField;
	private JTextField controlEmailTextField;
	private JTextField controlPasswordTextField;
	private JTextField controlRetryPasswordTextField;
	
	private int id;
	private String password;

	/**
	 * Create the application.
	 */
	public UserControl(JFrame menuForm) {
		initialize(menuForm);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame menuForm) {
		frmKullancYnetimi = new JFrame();
		frmKullancYnetimi.setTitle("Kullanıcı Yönetimi");
		frmKullancYnetimi.setBounds(100, 100, 551, 459);
		frmKullancYnetimi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKullancYnetimi.getContentPane().setLayout(null);
		
		HashMap<Integer, String> roleMap = new HashMap<>();
		RolesDAL roleList = new RolesDAL();
		
		JComboBox<Object> roleSelectComboBox = new JComboBox<Object>();
		roleSelectComboBox.setBounds(10, 149, 125, 22);
		
		for (String[] role : roleList.readRoles()) {
			roleMap.put(Integer.valueOf(role[0]), role[1]);
		}
		
		for (String roleName : roleMap.values()) {
			roleSelectComboBox.addItem(roleName);
		}
		
		frmKullancYnetimi.getContentPane().add(roleSelectComboBox);
		
		JComboBox<Object> controlRoleSelectComboBox = new JComboBox<Object>();
		controlRoleSelectComboBox.setBounds(10, 353, 125, 22);
		
		for (String roleName : roleMap.values()) {
			controlRoleSelectComboBox.addItem(roleName);
		}
		
		frmKullancYnetimi.getContentPane().add(controlRoleSelectComboBox);
				
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
	                // Tıklanan hücrenin satır indekslerini al
	                int row = table.rowAtPoint(e.getPoint());

	                // Döngü tıklanan satırdaki hücreleri tek tek gezer.
	                for(int i = 0; i < 5; i++) {
	                	Object value = table.getValueAt(row, i); // i ile bulunan indexteki veriyi value değişkenine atarız.
	                	if(i == 0) id = Integer.valueOf(value.toString());
	                	else if(i == 1) controlNameTextField.setText(value.toString());
	                	else if(i == 2) controlPasswordTextField.setText(value.toString());
	                	else if(i == 3) controlEmailTextField.setText(value.toString());
	                	else if(i == 4) {
	                        Integer selectedID = Integer.valueOf(value.toString()); 
	                        String selectedName = roleMap.get(selectedID);  
	                        controlRoleSelectComboBox.setSelectedItem(selectedName);
	                	}
	                }
	                
	                password = controlPasswordTextField.getText(); // Kullanıcı şifresini değiştirirse kontrolü yapmak için alıyoruz.
	         
				} catch(Exception ex) {
					System.err.println("Tabloda tıklanan yerde veri yok!");
					ex.printStackTrace();
				}
			}
		});
		table.setBounds(304, 11, 214, 398);
		frmKullancYnetimi.getContentPane().add(table);
		
        TableMetot accountTable = new TableMetot();
		table.setModel(accountTable.table(new String[] {"ID", "AccountName", "Password", "Email", "Role"}, 1));
		
		JLabel userAddHeaderLabel = new JLabel("Kullanıcı Ekle");
		userAddHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		userAddHeaderLabel.setBounds(10, 11, 182, 26);
		frmKullancYnetimi.getContentPane().add(userAddHeaderLabel);
		
		JLabel accountNameLabel = new JLabel("Kullanıcı Adı");
		accountNameLabel.setBounds(10, 41, 95, 14);
		frmKullancYnetimi.getContentPane().add(accountNameLabel);
		
		accountNameTextField = new JTextField();
		accountNameTextField.setBounds(10, 57, 125, 20);
		frmKullancYnetimi.getContentPane().add(accountNameTextField);
		accountNameTextField.setColumns(10);
		
		JLabel emailLabel = new JLabel("E-Mail");
		emailLabel.setBounds(151, 41, 95, 14);
		frmKullancYnetimi.getContentPane().add(emailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setColumns(10);
		emailTextField.setBounds(151, 57, 125, 20);
		frmKullancYnetimi.getContentPane().add(emailTextField);
		
		JLabel passwordLabel = new JLabel("Şifre");
		passwordLabel.setBounds(10, 88, 95, 14);
		frmKullancYnetimi.getContentPane().add(passwordLabel);
		
		passwordTextField = new JTextField();
		passwordTextField.setColumns(10);
		passwordTextField.setBounds(10, 104, 125, 20);
		frmKullancYnetimi.getContentPane().add(passwordTextField);
		
		JLabel retryPasswordLabel = new JLabel("Tekrar Şifre");
		retryPasswordLabel.setBounds(151, 88, 95, 14);
		frmKullancYnetimi.getContentPane().add(retryPasswordLabel);
		
		retryPasswordTextField = new JTextField();
		retryPasswordTextField.setColumns(10);
		retryPasswordTextField.setBounds(151, 104, 125, 20);
		frmKullancYnetimi.getContentPane().add(retryPasswordTextField);
		
		JButton accountAddBtn = new JButton("Hesap Ekle");
		accountAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				  
				  OtherMetot accountValidation = new OtherMetot();
				  boolean validation = true;
				  
				  if((accountNameTextField.getText().length() < 5 && accountNameTextField.getText().length() > 15) && (passwordTextField.getText().length() < 5 && passwordTextField.getText().length() > 15)) {
					  MessageModals.WarningMessage("Kullanıcı adı ve şifreniz 15 karakterden uzun 5 karakterden kısa olamaz!", "Uyarı");
					  validation = false;
				  }
				  
				  if(!passwordTextField.getText().equals(retryPasswordTextField.getText()) && validation == true) {
					  MessageModals.WarningMessage("Girdiğiniz şifreler birbiriyle aynı olmalı lütfen tekrar deneyiniz!", "Uyarı");
					  validation = false;  
				  }
				  
				  System.out.println((accountValidation.validation(accountNameTextField.getText()) && accountValidation.validation(passwordTextField.getText())));
				  System.out.println(accountValidation.validation(accountNameTextField.getText()));
				  System.out.println(accountValidation.validation(passwordTextField.getText()));
				  
				  if((accountValidation.validation(accountNameTextField.getText()) || accountValidation.validation(passwordTextField.getText())) && validation == true) {
					  MessageModals.WarningMessage("Kullanıcı adınız ve şifrenizde özel karakter bulunuyor lütfen sadece harf ve sayı kullanınız!", "Uyarı");
					  validation = false;
				  }
				  
				  if(accountValidation.mailValidation(emailTextField.getText()) && validation == true) {
					  validation = false;
				  }
				  
				  AccountsDAL insertAccount = new AccountsDAL();
				  
				  if(insertAccount.findAccount(accountNameTextField.getText()) != null) {
					  MessageModals.WarningMessage("Yazdığınız kullanıcı adına ait bir hesap bulunuyor!", "Uyarı");
					  validation = false;
				  }
				  
				   if(validation) {
					   
						// Rol ID 'si bulma döngüsü
						String roleName = (String) roleSelectComboBox.getSelectedItem();
						int roleID = -1;
						
						for (Map.Entry<Integer, String> entry : roleMap.entrySet()) {
						    if (entry.getValue().equals(roleName)) {
						        roleID = entry.getKey();  
						        break;  
						    }
						}
					   
						Accounts newAccount = new Accounts();
						  
						newAccount.setAccountName(accountNameTextField.getText());
						newAccount.setPassword(passwordTextField.getText());
						newAccount.setEmail(emailTextField.getText());
						newAccount.setRole(roleID);
			            insertAccount.insertAccount(newAccount);
			    		table.setModel(accountTable.table(new String[] {"ID", "AccountName", "Password", "Email", "Role"}, 1));
			            MessageModals.InformationMessage("Hesap ekleme işlemi başarılı bir şekilde gerçekleşti!", "İşlem Başarılı"); 
                   }
			}
		});
		accountAddBtn.setBounds(10, 186, 125, 23);
		frmKullancYnetimi.getContentPane().add(accountAddBtn);
		
		JLabel userControlHeaderLabel = new JLabel("Kullanıcıyı Yönet");
		userControlHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		userControlHeaderLabel.setBounds(10, 220, 206, 26);
		frmKullancYnetimi.getContentPane().add(userControlHeaderLabel);
		
		JLabel controlNameLabel = new JLabel("Kullanıcı Adı");
		controlNameLabel.setBounds(10, 250, 95, 14);
		frmKullancYnetimi.getContentPane().add(controlNameLabel);
		
		controlNameTextField = new JTextField();
		controlNameTextField.setColumns(10);
		controlNameTextField.setBounds(10, 266, 125, 20);
		frmKullancYnetimi.getContentPane().add(controlNameTextField);
		
		JLabel controlEmailLabel = new JLabel("E-Mail");
		controlEmailLabel.setBounds(151, 250, 95, 14);
		frmKullancYnetimi.getContentPane().add(controlEmailLabel);
		
		controlEmailTextField = new JTextField();
		controlEmailTextField.setColumns(10);
		controlEmailTextField.setBounds(151, 266, 125, 20);
		frmKullancYnetimi.getContentPane().add(controlEmailTextField);
		
		JLabel controlPasswordLabel = new JLabel("Şifre");
		controlPasswordLabel.setBounds(10, 297, 95, 14);
		frmKullancYnetimi.getContentPane().add(controlPasswordLabel);
		
		controlPasswordTextField = new JTextField();
		controlPasswordTextField.setColumns(10);
		controlPasswordTextField.setBounds(10, 313, 125, 20);
		frmKullancYnetimi.getContentPane().add(controlPasswordTextField);
		
		JLabel controlRetryPasswordLabel = new JLabel("Tekrar Şifre");
		controlRetryPasswordLabel.setBounds(151, 297, 95, 14);
		frmKullancYnetimi.getContentPane().add(controlRetryPasswordLabel);
		
		controlRetryPasswordTextField = new JTextField();
		controlRetryPasswordTextField.setColumns(10);
		controlRetryPasswordTextField.setBounds(151, 313, 125, 20);
		frmKullancYnetimi.getContentPane().add(controlRetryPasswordTextField);
		
		JButton accountEditBtn = new JButton("Hesabı Düzenle");
		accountEditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OtherMetot accountValidation = new OtherMetot();
				AccountsDAL updateAccount = new AccountsDAL();
				
				boolean updateValidation = true;

				
				  if((controlNameTextField.getText().length() < 5 && controlNameTextField.getText().length() > 15) && (controlPasswordTextField.getText().length() < 5 && controlPasswordTextField.getText().length() > 15)) {
					  MessageModals.WarningMessage("Kullanıcı adı ve şifreniz 15 karakterden uzun 5 karakterden kısa olamaz!", "Uyarı");
					  updateValidation = false;
				  }
				  
				  if((accountValidation.validation(controlNameTextField.getText()) || accountValidation.validation(controlPasswordTextField.getText())) && updateValidation == true) {
					  MessageModals.WarningMessage("Kullanıcı adınız ve şifrenizde özel karakter bulunuyor lütfen sadece harf ve sayı kullanınız!", "Uyarı");
					  updateValidation = false;
				  }
				  
				  if(accountValidation.mailValidation(controlEmailTextField.getText()) && updateValidation == true) {
					  updateValidation = false;
				  }
				  
				  if(updateAccount.findAccount(controlNameTextField.getText()) != null && (updateAccount.findAccount(controlNameTextField.getText()).getID() != id)) {
					  MessageModals.WarningMessage("Yazdığınız kullanıcı adına ait bir hesap bulunuyor!", "Uyarı");
					  updateValidation = false;
				  }
				  
				  System.out.println(password);
				  
				  if(!password.equals(controlPasswordTextField.getText()) && !controlPasswordTextField.getText().equals(controlRetryPasswordTextField.getText())) {
					  MessageModals.WarningMessage("Yazdığınız şifreler birbiriyle aynı değil!", "Uyarı");
					  updateValidation = false;
				  }
				  
				  if(Integer.valueOf(MenuPenceresi.userLogin[0]) == id && updateValidation == true) {
						  int response = MessageModals.ConfirmMessage("Şu an giriş yapmış olduğunuz hesap bilgilerini düzenliyorsunuz. Bu işleme devam etmek istiyor musunuz?", "Uyarı");
						  
						  if(response == JOptionPane.YES_OPTION) {
							  updateValidation = true;
						  } else {
							  updateValidation = false;
						  }
					} 
				  
				  if(updateValidation) {		  
					  
						// Rol ID 'si bulma döngüsü
						String roleName = (String) controlRoleSelectComboBox.getSelectedItem();
						int roleID = -1;
						
						for (Map.Entry<Integer, String> entry : roleMap.entrySet()) {
						    if (entry.getValue().equals(roleName)) {
						    	roleID = entry.getKey();  
						        break;  
						    }
						}
					  
						Accounts account = new Accounts();
						
						MenuPenceresi.userLogin[1] = controlNameTextField.getText();
						MenuPenceresi.userLogin[3] = controlEmailTextField.getText();
						MenuPenceresi.userLogin[2] = controlRetryPasswordTextField.getText().equals("") ? MenuPenceresi.userLogin[2] : controlRetryPasswordTextField.getText();  
						
						account.setID(Integer.valueOf(id));
						account.setAccountName(controlNameTextField.getText());
						account.setPassword(controlPasswordTextField.getText());
						account.setEmail(controlEmailTextField.getText());
						account.setRole(roleID);
								    
						updateAccount.accountUpdate(account);
						table.setModel(accountTable.table(new String[] {"ID", "AccountName", "Password", "Email", "Role"}, 1));
						MessageModals.InformationMessage("Hesap bilgileri başarıyla güncellendi!", "İşlem Başarılı");
				 }
				
			}
		});
		accountEditBtn.setBounds(10, 386, 125, 23);
		frmKullancYnetimi.getContentPane().add(accountEditBtn);
		
		JButton accountDeleteBtn = new JButton("Hesabı Sil");
		accountDeleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    boolean deleteValidation = true;
			    boolean nowAccount = false;
				
				if(id != 0) {
					
					if(Integer.valueOf(MenuPenceresi.userLogin[0]) == id) {
						  int response = MessageModals.ConfirmMessage("Şu an giriş yapmış olduğunuz hesabı siliyorsunuz, işleme devam ederseniz hesaptan otomatik olarak çıkış yapacaksınız. Bu işleme devam etmek istiyor musunuz?", "Uyarı");
						  
						  if(response == JOptionPane.YES_OPTION) {
							  nowAccount = true;
						  } else {
							  deleteValidation = false;
						  }
					}
					
					if(deleteValidation) {
						AccountsDAL accountDelete = new AccountsDAL();
						accountDelete.accountDelete(Integer.valueOf(id));
						
						table.setModel(accountTable.table(new String[] {"ID", "AccountName", "Password", "Email", "Role"}, 1));
						MessageModals.InformationMessage("Hesap başarıyla silindi!", "İşlem Başarılı");
						
                        if(nowAccount) {
    						LoginPanel loginPanel = new LoginPanel();
    						loginPanel.show(loginPanel);
    						menuForm.dispose(); // Visible özelliği kapatılmış menüyü komple kapatıyoruz. Arkada açık kalmaması için!
    						frmKullancYnetimi.dispose();
                        }
					}
					
				} else {
					MessageModals.ErrorMessage("Silinmesi için tablodan bir hesap seçmeniz gerekiyor!", "İşlem Hatası");
				}
				
			}
		});
		accountDeleteBtn.setBounds(151, 386, 125, 23);
		frmKullancYnetimi.getContentPane().add(accountDeleteBtn);
		
		JButton goToMenuButton = new JButton("< Geri Dön");
		goToMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuForm.setVisible(true);
				frmKullancYnetimi.dispose();
			}
		});
		goToMenuButton.setBounds(172, 11, 104, 23);
		frmKullancYnetimi.getContentPane().add(goToMenuButton);
		
		JLabel controlRoleLabel = new JLabel("Rol");
		controlRoleLabel.setBounds(10, 339, 131, 14);
		frmKullancYnetimi.getContentPane().add(controlRoleLabel);
		
		JLabel roleSelectLabel = new JLabel("Rol");
		roleSelectLabel.setBounds(10, 135, 131, 14);
		frmKullancYnetimi.getContentPane().add(roleSelectLabel);
	}
	
	public void show(UserControl window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frmKullancYnetimi.setLocationRelativeTo(null);
					window.frmKullancYnetimi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

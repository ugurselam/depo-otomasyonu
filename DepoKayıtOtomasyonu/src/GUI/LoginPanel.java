package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import DAL.AccountsDAL;
import Model.Accounts;
import Utils.MessageModals;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPanel {

	private JFrame frmLoginPanel;
	private JPasswordField password;
	private JTextField accountName;
	

	/*
	 * Main
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {				    
					try {
						AccountsDAL findAccount = new AccountsDAL();
						findAccount.readAccount().get(0);
						
						LoginPanel window = new LoginPanel();
						window.frmLoginPanel.setLocationRelativeTo(null);
						window.frmLoginPanel.setVisible(true);
					} catch (Exception e) {
						FirstEntry firstEntry = new FirstEntry();
						firstEntry.show(firstEntry);
					}
				} catch (Exception e) {
					MessageModals.ErrorMessage("Giriş penceresi açılamadı, sistem hatası oluştu!", "Sistem Hatası");
					e.printStackTrace();
				}
			}
		});
	}

	public LoginPanel() {
		initialize();
	}

	private void initialize() {
		
		frmLoginPanel = new JFrame();
		frmLoginPanel.setResizable(false);
		frmLoginPanel.setTitle("Depo Otomasyonu - Giriş Paneli");
		frmLoginPanel.setBounds(100, 100, 296, 234);
		frmLoginPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLoginPanel.getContentPane().setLayout(null);
		frmLoginPanel.setLocationRelativeTo(null);
		
		JLabel accountNameLbl = new JLabel("Kullanıcı Adı");
		accountNameLbl.setBounds(45, 30, 89, 14);
		frmLoginPanel.getContentPane().add(accountNameLbl);
		
		JLabel passwordLbl = new JLabel("Şifre");
		passwordLbl.setBounds(45, 76, 76, 14);
		frmLoginPanel.getContentPane().add(passwordLbl);
		
		JButton girisYapBtn = new JButton("Giriş Yap");
		girisYapBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				AccountsDAL find = new AccountsDAL();
				Accounts loginAccount = find.loginAccount(accountName.getText(), password.getPassword());
				
				// findAccount() metotu read sınıfından çağırılır ve gönderilen parametreler ile
				// textbox'lara girilen bilgilere ait hesap olup olmadığını kontrol eder.
				if(loginAccount != null) {
					
					// Kullanıcının giriş bilgilerini menüye gönderiyoruz.
					MenuPenceresi.userLogin[0] = String.valueOf(loginAccount.getID());
					MenuPenceresi.userLogin[1] = loginAccount.getAccountName();
					MenuPenceresi.userLogin[2] = loginAccount.getPassword();
					MenuPenceresi.userLogin[3] = loginAccount.getEmail();
					MenuPenceresi.userLogin[4] = String.valueOf(loginAccount.getRole());
					
					MenuPenceresi menu = new MenuPenceresi();
					menu.show(menu); // Eğer geriye null dönmezse doğru girmiş demektir admin panel açılır.
					frmLoginPanel.dispose();
				} else {
					MessageModals.ErrorMessage("Kullanıcı adı veya şifreyi yanlış girdiniz!", "Giriş Hatası"); // eğer null dönerse bilgiler yanlış demektir.
				}
			}
		});
		girisYapBtn.setBounds(65, 121, 150, 23);
		frmLoginPanel.getContentPane().add(girisYapBtn);
		
		password = new JPasswordField();
		password.setBounds(45, 90, 182, 20);
		frmLoginPanel.getContentPane().add(password);
		
		accountName = new JTextField();
		accountName.setBounds(45, 45, 182, 20);
		frmLoginPanel.getContentPane().add(accountName);
		accountName.setColumns(10);
		
		JButton forgotPasswordTextField = new JButton("Şifremi Unuttum");
		forgotPasswordTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountsDAL findAccountName = new AccountsDAL();
				if(findAccountName.findAccount(accountName.getText()) != null) {
					CodeDialog forgotPassword = new CodeDialog();
					forgotPassword.type = 2;
					forgotPassword.accountName = accountName.getText();
					forgotPassword.show(forgotPassword);
					frmLoginPanel.dispose();
				} else {
					MessageModals.ErrorMessage("Kullanıcı adınıza ait bir hesap bulunamadı, lütfen kullanıcı adınızı doğru giriniz!", "Hata Mesajı");
				}
			}
		});
		forgotPasswordTextField.setBounds(65, 150, 150, 23);
		frmLoginPanel.getContentPane().add(forgotPasswordTextField);
	}
	
	public void show(LoginPanel window) {
		try {				    
			window.frmLoginPanel.setVisible(true);
		} catch (Exception e) {
			MessageModals.ErrorMessage("Giriş penceresi açılamadı, sistem hatası oluştu!", "Sistem Hatası");
			e.printStackTrace();
		}
	}
}

package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import Utils.MessageModals;
import Utils.OtherMetot;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import DAL.AccountsDAL;
import Model.Accounts;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PersonalInformation {

	private JFrame frmKiiselBilgilerim;
	private JTextField accountNameTextField;
	private JTextField emailTextField;
	private JTextField passwordTextField;
	private JTextField retryPasswordTextField;

	/**
	 * Launch the application.
	 */
	String[] personalInformation = new String[5];

	/**
	 * Create the application.
	 */
	public PersonalInformation(JFrame menuForm) {
		initialize(menuForm);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame menuForm) {
		
		if(MenuPenceresi.userLogin != null) {
			personalInformation = MenuPenceresi.userLogin;	
		} else {
			MessageModals.ErrorMessage("Kişisel verileriniz alınamadı, sistem hatası oluştu!", "Sistem Hatası");
			menuForm.setVisible(true);
			frmKiiselBilgilerim.dispose();
		}
		
		frmKiiselBilgilerim = new JFrame();
		frmKiiselBilgilerim.setTitle("Kişisel Bilgilerim");
		frmKiiselBilgilerim.setBounds(100, 100, 254, 322);
		frmKiiselBilgilerim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKiiselBilgilerim.getContentPane().setLayout(null);
		
		JLabel personalInformationHeaderLabel = new JLabel("Kişisel Bilglerim");
		personalInformationHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		personalInformationHeaderLabel.setBounds(39, 11, 186, 14);
		frmKiiselBilgilerim.getContentPane().add(personalInformationHeaderLabel);
		
		JLabel accountNameLabel = new JLabel("Kullanıcı Adı");
		accountNameLabel.setBounds(39, 36, 127, 14);
		frmKiiselBilgilerim.getContentPane().add(accountNameLabel);
		
		accountNameTextField = new JTextField();
		accountNameTextField.setBounds(39, 52, 148, 20);
		frmKiiselBilgilerim.getContentPane().add(accountNameTextField);
		accountNameTextField.setColumns(10);
		accountNameTextField.setText(personalInformation[1] != null ? personalInformation[1] : "Veri Alınamadı!");
		
		JLabel emailLabel = new JLabel("E-Mail");
		emailLabel.setBounds(39, 83, 70, 14);
		frmKiiselBilgilerim.getContentPane().add(emailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(39, 98, 148, 20);
		frmKiiselBilgilerim.getContentPane().add(emailTextField);
		emailTextField.setColumns(10);
		emailTextField.setText(personalInformation[3] != null ? personalInformation[3] : "Veri Alınamadı!");
		
		JLabel passwordLabel = new JLabel("Şifre");
		passwordLabel.setBounds(39, 129, 46, 14);
		frmKiiselBilgilerim.getContentPane().add(passwordLabel);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(39, 145, 148, 20);
		frmKiiselBilgilerim.getContentPane().add(passwordTextField);
		passwordTextField.setColumns(10);
		
		JLabel retryPasswordLabel = new JLabel("Tekrar Şifre");
		retryPasswordLabel.setBounds(39, 176, 127, 14);
		frmKiiselBilgilerim.getContentPane().add(retryPasswordLabel);
		
		retryPasswordTextField = new JTextField();
		retryPasswordTextField.setBounds(39, 191, 148, 20);
		frmKiiselBilgilerim.getContentPane().add(retryPasswordTextField);
		retryPasswordTextField.setColumns(10);
		
		JButton updatePersonalInformationButton = new JButton("Değişiklikleri Kaydet");
		updatePersonalInformationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountsDAL updateAccount = new AccountsDAL();
				Accounts account = updateAccount.findAccount(personalInformation[1]);
				OtherMetot accountValidation = new OtherMetot();
				
				boolean validation = true;
				
				  if((accountNameTextField.getText().length() < 5 && accountNameTextField.getText().length() > 15)) {
					  MessageModals.WarningMessage("Kullanıcı adınız 15 karakterden uzun 5 karakterden kısa olamaz!", "Uyarı");
					  validation = false;
				  }
				  
				  if(accountValidation.validation(accountNameTextField.getText()) && validation == true) {
					  MessageModals.WarningMessage("Kullanıcı adınızda özel karakter bulunuyor lütfen sadece harf ve sayı kullanınız!", "Uyarı");
					  validation = false;
				  }
				  
				  if(accountValidation.mailValidation(emailTextField.getText()) && validation == true) {
					  validation = false;
				  }
				  
				  if(passwordTextField.getText().length() > 0 && validation == true) {
					  if(!passwordTextField.getText().equals(retryPasswordTextField.getText()) && validation == true) {
						  MessageModals.WarningMessage("Girdiğiniz şifreler birbiriyle aynı değil, şifrenizi değiştirmek istemiyorsanız lütfen alanı boş bırakınız!", "Uyarı");
						  validation = false;
					  }
					  
					  if((retryPasswordTextField.getText().length() < 5 && retryPasswordTextField.getText().length() > 15) && validation == true) {
						  MessageModals.WarningMessage("Şifreniz 15 karakterden uzun 5 karakterden kısa olamaz!", "Uyarı");
						  validation = false;
					  }
					  
					  if(accountValidation.validation(retryPasswordTextField.getText()) && validation == true) {
						  MessageModals.WarningMessage("Şifrenizde özel karakterler bulunmamalı lütfen sadece harf ve sayı kullanınız!", "Uyarı");
						  validation = false;
					  }
				  }
				  
				  if(updateAccount.findAccount(accountNameTextField.getText()) != null && (updateAccount.findAccount(accountNameTextField.getText()).getID() != Integer.valueOf(personalInformation[0]))) {
					  MessageModals.WarningMessage("Yazdığınız kullanıcı adına ait bir hesap bulunuyor!", "Uyarı");
					  validation = false;
				  }
				  
				  if(validation) {
					    account.setAccountName(accountNameTextField.getText());
					    account.setPassword(retryPasswordTextField.getText().equals("") ? personalInformation[2] : retryPasswordTextField.getText());
					    account.setEmail(emailTextField.getText());
					    
					    MenuPenceresi.userLogin[1] = account.getAccountName();
					    MenuPenceresi.userLogin[2] = retryPasswordTextField.getText().equals("") ? personalInformation[2] : retryPasswordTextField.getText();
					    MenuPenceresi.userLogin[3] = account.getEmail();
					    
					    updateAccount.accountUpdate(account);
					   	MessageModals.InformationMessage("Hesap bilgileri başarıyla güncellendi!", "İşlem Başarılı");
				  }
				
			}
		});
		updatePersonalInformationButton.setBounds(39, 222, 148, 23);
		frmKiiselBilgilerim.getContentPane().add(updatePersonalInformationButton);
		
		JButton goToMenuButton = new JButton("< Geri Dön");
		goToMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuForm.setVisible(true);
				frmKiiselBilgilerim.dispose();
			}
		});
		goToMenuButton.setBounds(39, 249, 148, 23);
		frmKiiselBilgilerim.getContentPane().add(goToMenuButton);
	}
	
	public void show(PersonalInformation window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frmKiiselBilgilerim.setLocationRelativeTo(null);
					window.frmKiiselBilgilerim.setVisible(true);
				} catch (Exception e) {
					MessageModals.ErrorMessage("Kişisel bilgilerim penceresi açılamadı, sistem hatası oluştu!", "Sistem Hatası");
					e.printStackTrace();
				}
			}
		});
	}
}

package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Utils.MessageModals;
import Utils.OtherMetot;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FirstEntry {

	private JFrame frmDepoOtomasyonu;
	private JTextField accountNameTextField;
	private JTextField passwordTextField;
	private JTextField confirmPasswordTextField;
	private JTextField emailTextField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public FirstEntry() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDepoOtomasyonu = new JFrame();
		frmDepoOtomasyonu.setTitle("Depo Otomasyonu");
		frmDepoOtomasyonu.setBounds(100, 100, 283, 332);
		frmDepoOtomasyonu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDepoOtomasyonu.getContentPane().setLayout(null);
		
		JLabel infoLabel = new JLabel("Merhaba, programı ilk defa çalıştırdığın için");
		infoLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setBounds(10, 11, 247, 14);
		frmDepoOtomasyonu.getContentPane().add(infoLabel);
		
		JLabel infoLabel2 = new JLabel("bir yönetici hesabı oluşturman gerekiyor!");
		infoLabel2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		infoLabel2.setBounds(33, 21, 252, 14);
		frmDepoOtomasyonu.getContentPane().add(infoLabel2);
		
		accountNameTextField = new JTextField();
		accountNameTextField.setBounds(43, 61, 182, 20);
		frmDepoOtomasyonu.getContentPane().add(accountNameTextField);
		accountNameTextField.setColumns(10);
		
		JLabel accountNameLbl = new JLabel("Kullanıcı Adı");
		accountNameLbl.setBounds(43, 46, 69, 14);
		frmDepoOtomasyonu.getContentPane().add(accountNameLbl);
		
		JLabel passwordLbl = new JLabel("Şifre");
		passwordLbl.setBounds(43, 92, 46, 14);
		frmDepoOtomasyonu.getContentPane().add(passwordLbl);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(43, 108, 182, 20);
		frmDepoOtomasyonu.getContentPane().add(passwordTextField);
		passwordTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Şifreni Doğrula");
		lblNewLabel.setBounds(43, 139, 86, 14);
		frmDepoOtomasyonu.getContentPane().add(lblNewLabel);
		
		confirmPasswordTextField = new JTextField();
		confirmPasswordTextField.setBounds(43, 156, 182, 20);
		frmDepoOtomasyonu.getContentPane().add(confirmPasswordTextField);
		confirmPasswordTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("E-Mail");
		lblNewLabel_1.setBounds(43, 187, 46, 14);
		frmDepoOtomasyonu.getContentPane().add(lblNewLabel_1);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(43, 201, 182, 20);
		frmDepoOtomasyonu.getContentPane().add(emailTextField);
		emailTextField.setColumns(10);
		
		JButton createAccountBtn = new JButton("Hesabımı Oluştur");
		createAccountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					// Validation İşlemleri
					boolean status = true;
					OtherMetot accountValidation = new OtherMetot();
					
					if(accountValidation.validation(accountNameTextField.getText()) && accountNameTextField.getText().length() < 5) {
						 MessageModals.ErrorMessage("Kullanıcı adınızda özel karakterler bulunamaz ve kullanıcı adınızın uzunluğu 5 karakterden fazla olmalı!", "Kullanıcı Adı Hatası");
						 status = false;
					} else if(accountValidation.validation(passwordTextField.getText()) && passwordTextField.getText().length() < 5) {
						 MessageModals.ErrorMessage("Şifrenizde özel karakterler bulunamaz, şifrenizin uzunluğu 5 karakterden fazla olmalı ve 15 karakterden kısa olmalı!", "Şifre Hatası");
						 status = false;
					} else if(!passwordTextField.getText().equals(confirmPasswordTextField.getText())) {
						 MessageModals.ErrorMessage("Girdiğiniz şifre birbiriyle aynı değil lütfen tekrar deneyiniz!", "Şifre Hatası");
						 status = false;
					} else if(accountValidation.mailValidation(emailTextField.getText())) {
						status = false;
					}
						
					if(status) {
					    CodeDialog verifactDialog = new CodeDialog();
					    verifactDialog.type = 1;
					    verifactDialog.email = emailTextField.getText();
					    verifactDialog.accountName = accountNameTextField.getText();
					    verifactDialog.password = passwordTextField.getText();
					    verifactDialog.show(verifactDialog);
					    frmDepoOtomasyonu.dispose();
					} 
				
			} catch (Exception mailError) {
				MessageModals.ErrorMessage("Mail gönderilirken hata oluştu, girdiğiniz bilgiler hatalı olabilir lütfen kontrol ediniz!", "Hata");
				mailError.printStackTrace();
			}
			}
		});
		createAccountBtn.setBounds(43, 234, 182, 23);
		frmDepoOtomasyonu.getContentPane().add(createAccountBtn);
	}
	
    public void show(FirstEntry window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frmDepoOtomasyonu.setLocationRelativeTo(null);
					window.frmDepoOtomasyonu.setVisible(true);
				} catch (Exception e) {
					MessageModals.ErrorMessage("Hesap oluşturma penceresi açılamadı, sistem hatası oluştu!", "Sistem Hatası");
					e.printStackTrace();
				}
			}
		});
    }
}

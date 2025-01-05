package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import DAL.AccountsDAL;
import Model.Accounts;
import Utils.MessageModals;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewPassword {

	private JFrame frmifreGncelle;
	private JTextField passwordTextField;
	private JTextField retryPasswordTextField;

	/**
	 * Launch the application.
	 */
	
	public String accountName;

	/**
	 * Create the application.
	 */
	public NewPassword() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmifreGncelle = new JFrame();
		frmifreGncelle.setTitle("Şifre Güncelle");
		frmifreGncelle.setBounds(100, 100, 250, 211);
		frmifreGncelle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmifreGncelle.getContentPane().setLayout(null);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(34, 42, 163, 20);
		frmifreGncelle.getContentPane().add(passwordTextField);
		passwordTextField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Şifre");
		passwordLabel.setBounds(35, 25, 72, 14);
		frmifreGncelle.getContentPane().add(passwordLabel);
		
		retryPasswordTextField = new JTextField();
		retryPasswordTextField.setBounds(34, 89, 163, 20);
		frmifreGncelle.getContentPane().add(retryPasswordTextField);
		retryPasswordTextField.setColumns(10);
		
		JLabel retryPasswordLabel = new JLabel("Şifrenizi Doğrulayın");
		retryPasswordLabel.setBounds(34, 73, 123, 14);
		frmifreGncelle.getContentPane().add(retryPasswordLabel);
		
		JButton updatePasswordButton = new JButton("Şifreni Güncelle");
		updatePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountsDAL updatePassword = new AccountsDAL();
				System.out.println(accountName);
				if(passwordTextField.getText().length() != 0 && (passwordTextField.getText().equals(retryPasswordTextField.getText()) )) {
					Accounts newPassword = updatePassword.findAccount(accountName);
					newPassword.setPassword(passwordTextField.getText());
					updatePassword.accountUpdate(newPassword);
					
					MessageModals.InformationMessage("Şifreniz başarıyla güncellendi, hesabınıza giriş yapabilirsiniz!", "Şifre Değiştirildi");
					LoginPanel loginPanel = new LoginPanel();
					loginPanel.show(loginPanel);
					frmifreGncelle.dispose();
				} else {
					MessageModals.ErrorMessage("Girdiğiniz şifreler birbirinden farklı lütfen tekrar deneyiniz!", "Doğrulama Hatası");
				}
			}
		});
		updatePasswordButton.setBounds(44, 120, 142, 23);
		frmifreGncelle.getContentPane().add(updatePasswordButton);
	}
	
	public void show(NewPassword window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//NewPassword window = new NewPassword();
					window.frmifreGncelle.setLocationRelativeTo(null);
					window.frmifreGncelle.setVisible(true);
				} catch (Exception e) {
					MessageModals.ErrorMessage("Şifre değiştirme penceresi açılamadı, sistem hatası oluştu!", "Sistem Hatası");
					e.printStackTrace();
				}
			}
		});
	}
}

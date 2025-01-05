package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import DAL.AccountsDAL;
import Model.Accounts;
import Utils.MessageModals;
import Utils.OtherMetot;

import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CodeDialog {

	private JFrame frmDorulamaPenceresi;
	private JTextField codeOne;
	private JTextField codeTwo;
	private JTextField codeThree;
	private JTextField codeFour;
	private JTextField codeFive;
	private JLabel infoLabel3;

	/**
	 * Create the application.
	 */
	
	public int type;
	public String accountName;
	public String password;
	public String email;
	public String code;
	
	public CodeDialog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDorulamaPenceresi = new JFrame();
		frmDorulamaPenceresi.setTitle("Doğrulama Penceresi");
		frmDorulamaPenceresi.setBounds(100, 100, 318, 209);
		frmDorulamaPenceresi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDorulamaPenceresi.getContentPane().setLayout(null);
		
		JLabel infoLabel = new JLabel("Mail adresinize doğrulama kodu gönderdik.");
		infoLabel.setBounds(37, 11, 246, 14);
		frmDorulamaPenceresi.getContentPane().add(infoLabel);
		
		JLabel infoLabel2 = new JLabel("Doğrulama kodunu girerek işleminizi");
		infoLabel2.setBounds(37, 23, 322, 14);
		frmDorulamaPenceresi.getContentPane().add(infoLabel2);
		
		codeFive = new JTextField();
		codeFive.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(codeFive.getText().length() == 1) {
					e.consume();
				} else if(codeFive.getText().length() > 1) {
					codeFive.setText("");
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_BACK_SPACE) {
					if(codeFive.getText().length() == 1) {
						codeFour.requestFocus();
					}
				} 
			}
		});
		codeFive.setBounds(206, 62, 25, 30);
		frmDorulamaPenceresi.getContentPane().add(codeFive);
		codeFive.setColumns(10);
		
		codeFour = new JTextField();
		codeFour.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(codeFour.getText().length() == 1) {
					e.consume();
				} else if(codeFour.getText().length() > 1) {
					codeFour.setText("");
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_BACK_SPACE) {
					if(codeFour.getText().length() == 1) {
						codeThree.requestFocus();
					}
				} else {
					codeFive.requestFocus();
				}
			}
		});
		codeFour.setBounds(170, 62, 25, 30);
		frmDorulamaPenceresi.getContentPane().add(codeFour);
		codeFour.setColumns(10);
		
		codeThree = new JTextField();
		codeThree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(codeThree.getText().length() == 1) {
					e.consume();
				} else if(codeThree.getText().length() > 1) {
					codeThree.setText("");
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_BACK_SPACE) {
					if(codeThree.getText().length() == 1) {
						codeTwo.requestFocus();
					}
				} else {
					codeFour.requestFocus();
				}
			}
		});
		codeThree.setBounds(135, 62, 25, 30);
		frmDorulamaPenceresi.getContentPane().add(codeThree);
		codeThree.setColumns(10);
		
		codeTwo = new JTextField();
		codeTwo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(codeTwo.getText().length() == 1) {
					e.consume();
				} else if(codeTwo.getText().length() > 1) {
					codeTwo.setText("");
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_BACK_SPACE) {
					if(codeTwo.getText().length() == 1) {
						codeOne.requestFocus();
					}
				} else {
					codeThree.requestFocus();
				}
			}
		});
		codeTwo.setBounds(101, 62, 25, 30);
		frmDorulamaPenceresi.getContentPane().add(codeTwo);
		codeTwo.setColumns(10);
		
		codeOne = new JTextField();
		codeOne.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(codeOne.getText().length() == 1) {
					e.consume();
				} else if(codeOne.getText().length() > 1) {
					codeOne.setText("");
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == e.VK_BACK_SPACE) {
					
				} else {
					codeTwo.requestFocus();
				}
			}
		});
		codeOne.setBounds(67, 62, 25, 30);
		frmDorulamaPenceresi.getContentPane().add(codeOne);
		codeOne.setColumns(10);
		
		JButton verifactionButton = new JButton("Doğrula");
		verifactionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(type);
				String userCode = codeOne.getText() + codeTwo.getText() + codeThree.getText() + codeFour.getText() + codeFive.getText();
					if(userCode.equals(code)) {
						if(type == 1) {
							Accounts newAccount = new Accounts();
							newAccount.setAccountName(accountName);
							newAccount.setPassword(password);
							newAccount.setEmail(email);
							newAccount.setRole(1);
							AccountsDAL createAccount = new AccountsDAL();
							createAccount.insertAccount(newAccount);
							
							MessageModals.InformationMessage("Hesabınız başarı ile oluşturuldu lütfen hesabınıza giriş yapınız!", "Bilgilendirme");
							LoginPanel loginPanel = new LoginPanel();
							loginPanel.show(loginPanel);	
							frmDorulamaPenceresi.dispose();
						} else if(type == 2) {
							NewPassword newPassword = new NewPassword();
							newPassword.accountName = accountName;
							newPassword.show(newPassword);
							frmDorulamaPenceresi.dispose();
						}
					} else {
						MessageModals.ErrorMessage("Girdiğiniz doğrulama kodu hatalı lütfen tekrar deneyiniz!", "Kod Hatası");
					}
			}
		});
		verifactionButton.setBounds(77, 113, 154, 23);
		frmDorulamaPenceresi.getContentPane().add(verifactionButton);
		
		JButton retryButton = new JButton("Kodu Tekrar Gönder");
		retryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OtherMetot retrySend = new OtherMetot();
				code = retrySend.sendCode(email);	
			}
		});
		retryButton.setBounds(77, 136, 154, 23);
		frmDorulamaPenceresi.getContentPane().add(retryButton);
		
		infoLabel3 = new JLabel("tamamlayabilirsiniz.");
		infoLabel3.setBounds(37, 37, 123, 14);
		frmDorulamaPenceresi.getContentPane().add(infoLabel3);
	}
	
	public void show(CodeDialog window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//CodeDialog window = new CodeDialog();
					window.frmDorulamaPenceresi.setLocationRelativeTo(null);
					window.frmDorulamaPenceresi.setVisible(true);
					if(type == 2) {
						AccountsDAL findEmail = new AccountsDAL();
						email = findEmail.findAccount(accountName).getEmail();
					}
					
					OtherMetot retrySend = new OtherMetot(); // Form ilk açıldığında mail'e kod gönderilecek.
					code = retrySend.sendCode(email);	
				} catch (Exception e) {
					MessageModals.ErrorMessage("Kod doğrulama penceresi açılamadı, sistem hatası oluştu!", "Sistem Hatası");
					e.printStackTrace();
				}
			}
		});
	}
}

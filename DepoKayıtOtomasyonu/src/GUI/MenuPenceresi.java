package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import DAL.RolesDAL;
import Model.Roles;
import Utils.MessageModals;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuPenceresi {

	private JFrame frmMen;
	
	
	public static String[] userLogin = new String[5];

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public MenuPenceresi() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		RolesDAL findRole = new RolesDAL();
		Roles perm = findRole.findRole(Integer.valueOf(userLogin[4]));
		String permissions = perm.getPermissions();
		
		frmMen = new JFrame();
		frmMen.setTitle("Menü");
		frmMen.setBounds(100, 100, 215, 296);
		frmMen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMen.getContentPane().setLayout(null);
		
		JButton userControlBtn = new JButton("Kullanıcı Yönetimi");
		userControlBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserControl userControl = new UserControl(frmMen);
				frmMen.setVisible(false);
				userControl.show(userControl);
			}
		});
		userControlBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userControlBtn.setBounds(10, 11, 179, 36);
		
		if(!permissions.contains("A")) {
			userControlBtn.setEnabled(false); // Kullanıcının yetkilerinde A harfi yoksa butonu kullanılamaz hale getiriyor.
		}
		
		frmMen.getContentPane().add(userControlBtn);
		
		JButton categoriesControlBtn = new JButton("Kategori Yönetimi");
		categoriesControlBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CategoriesControl categories = new CategoriesControl(frmMen);
				categories.show(categories);
				frmMen.setVisible(false);
			}
		});
		categoriesControlBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		categoriesControlBtn.setBounds(10, 91, 179, 36);
		
		if(!permissions.contains("C")) {
			categoriesControlBtn.setEnabled(false);
		}
		
		frmMen.getContentPane().add(categoriesControlBtn);
		
		JButton productsControlBtn = new JButton("Ürün Yönetimi");
		productsControlBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductControl productControl = new ProductControl(frmMen);
				productControl.show(productControl);
			}
		});
		productsControlBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		productsControlBtn.setBounds(10, 128, 179, 36);
		
		if(!permissions.contains("D")) {
			productsControlBtn.setEnabled(false);
		}
		
		frmMen.getContentPane().add(productsControlBtn);
		
		JButton personalInformationBtn = new JButton("Kişisel Bilgilerim");
		personalInformationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonalInformation personalForm = new PersonalInformation(frmMen);
				personalForm.show(personalForm);
				frmMen.setVisible(false);
			}
		});
		personalInformationBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		personalInformationBtn.setBounds(10, 169, 179, 36);
		frmMen.getContentPane().add(personalInformationBtn);
		
		JButton logoutBtn = new JButton("Çıkış Yap");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPanel loginPanel = new LoginPanel();
				loginPanel.show(loginPanel);
				MenuPenceresi.userLogin[0] = "";
				MenuPenceresi.userLogin[1] = "";
				MenuPenceresi.userLogin[2] = "";
				MenuPenceresi.userLogin[3] = "";
				MenuPenceresi.userLogin[4] = "";
				frmMen.dispose();
			}
		});
		logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logoutBtn.setBounds(10, 210, 179, 36);
		frmMen.getContentPane().add(logoutBtn);
		
		JButton roleControlBtn = new JButton("Rol Yönetimi");
		roleControlBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RoleControl roleControl = new RoleControl(frmMen);
				roleControl.show(roleControl);
				frmMen.dispose();
			}
		});
		roleControlBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		roleControlBtn.setBounds(10, 50, 179, 36);
		
		if(!permissions.contains("B")) {
			roleControlBtn.setEnabled(false);
		}
		
		frmMen.getContentPane().add(roleControlBtn);
	}
	
	public void show(MenuPenceresi window) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frmMen.setLocationRelativeTo(null);
					window.frmMen.setVisible(true);
				} catch (Exception e) {
					MessageModals.ErrorMessage("Menü penceresi açılamadı, sistem hatası oluştu!", "Sistem Hatası");
					e.printStackTrace();
				}
			}
		});
	}
}

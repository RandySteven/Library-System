import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame{
	
	JPanel northPanel, centerPanel, southPanel;
	
	public void initiallize() {
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		north();
		center();
		south();
	}
	
	JLabel lblTitle;
	public void north() {
		lblTitle = new JLabel("Login Form");
		northPanel.add(lblTitle);
		lblTitle.setHorizontalTextPosition(JLabel.CENTER);
	}
	
	JLabel lblEmail, lblPass;
	JTextField txtEmail;
	JPasswordField txtPass;
	public void center() {
		centerPanel.setLayout(new GridLayout(2, 2, 30, 70));
		lblEmail = new JLabel("Email");
		lblPass = new JLabel("Password");
		txtEmail = new JTextField();
		txtPass = new JPasswordField();
		centerPanel.add(lblEmail);
		centerPanel.add(txtEmail);
		centerPanel.add(lblPass);
		centerPanel.add(txtPass);
	}
	
	JButton btnLogin, btnRegister, btnClear;
	String email, password;
	public void south() {
		southPanel.setLayout(new FlowLayout());
		btnLogin = new JButton("Login");
		btnRegister = new JButton("Register");
		btnClear = new JButton("Clear");
		southPanel.add(btnLogin);
		southPanel.add(btnRegister);
		southPanel.add(btnClear);
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Register register = new Register();
				register.setVisible(true);
				setVisible(false);
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				email = txtEmail.getText();
				password = txtPass.getText();
				//Admin
				//User
				try {
					String query = "SELECT * FROM users WHERE email='"+email+"' AND password='"+password+"' ";
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(query);
					if(rs.next()) {
//						//Ambil dulu data roleId
						if(rs.getInt(7)==1) {
							JOptionPane.showMessageDialog(null, "Welcome admin");
						}else {							
							JOptionPane.showMessageDialog(null, "Welcome users");
						}
						Main main = new Main();
						main.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Data user tidak ditemukan");
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
				}
				
			}
		});
	}
	
	Connection con;
	public Login() {
		con = sqlConnector.connector();
		setLayout(new BorderLayout());
		initiallize();
		setVisible(true);
		setSize(600, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Login();
	}

	
	
	
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Register extends JFrame{
	//nama
	//email
	//password
	//gender
	//dob
	JPanel northPanel, centerPanel, southPanel;
	
	public void initiallize() {
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
//		northPanel.setBackground(Color.blue);
//		centerPanel.setBackground(Color.GREEN);
//		southPanel.setBackground(Color.CYAN);
	
		north();
		center();
		south();
	}
	JLabel lblTitle;
	public void north() {
		lblTitle = new JLabel("Register form");
		northPanel.add(lblTitle);
		lblTitle.setHorizontalTextPosition(JLabel.CENTER);
	}
	
	JLabel lblName, lblEmail, lblPassword, lblGender, lblDoB;
	JTextField txtName, txtEmail;
	JPasswordField txtPass;
	JRadioButton btnMale;
	JRadioButton btnFemale;
	ButtonGroup bgGender;
	JComboBox cmbDate, cmbMonth, cmbYear;
	JPanel panelGender, panelDoB;
	public void center() {
		centerPanel.setLayout(new GridLayout(5, 2, 10, 30));
		lblName = new JLabel("Name");
		lblEmail = new JLabel("Email");
		lblPassword = new JLabel("Password");
		lblGender = new JLabel("Gender");
		lblDoB = new JLabel("DoB");
		txtName = new JTextField();
		txtEmail = new JTextField();
		txtPass = new JPasswordField();
		btnMale = new JRadioButton("Male");
		btnFemale = new JRadioButton("Female");
		bgGender = new ButtonGroup();
		panelGender = new JPanel();
		panelDoB = new JPanel();
		panelGender.setLayout(new FlowLayout());
		panelDoB.setLayout(new FlowLayout());
		bgGender.add(btnFemale);
		bgGender.add(btnMale);
		
		panelGender.add(btnMale);
		panelGender.add(btnFemale);
		
		cmbDate = new JComboBox<Integer>();
		cmbMonth = new JComboBox<Integer>();
		cmbYear = new JComboBox<Integer>();
		for(int date = 1 ; date <= 31 ; date++) {
			if(date<10) {				
				cmbDate.addItem("0" + date);
			}else {				
				cmbDate.addItem(date);
			}
		}
		for(int month = 1 ; month <= 12 ; month++) {
			if(month<10) {				
				cmbMonth.addItem("0"+month);
			}else {
				cmbMonth.addItem(month);
			}
		}
		for(int year = 1975 ; year <= 2020 ; year++) {
			cmbYear.addItem(year);
		}
		panelDoB.add(cmbDate);
		panelDoB.add(cmbMonth);
		panelDoB.add(cmbYear);
		centerPanel.add(lblName);
		centerPanel.add(txtName);
		centerPanel.add(lblEmail);
		centerPanel.add(txtEmail);
		centerPanel.add(lblPassword);
		centerPanel.add(txtPass);
		centerPanel.add(lblGender);
		centerPanel.add(panelGender);
		centerPanel.add(lblDoB);
		centerPanel.add(panelDoB);
	}
	
	JButton btnRegister, btnClear;
	String gender, date, month, year, name, email, password;
	String DoB1;
	Date DoB2;
	public void south() {
		southPanel.setLayout(new FlowLayout());
		btnRegister = new JButton("Register");
		btnClear = new JButton("Clear");
		southPanel.add(btnRegister);
		southPanel.add(btnClear);
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				date = String.valueOf(cmbDate.getSelectedItem());
				month = String.valueOf(cmbMonth.getSelectedItem());
				year = String.valueOf(cmbYear.getSelectedItem());
				//YYYY-MM-DD
				DoB1 = year + "-" + month + "-" + date;
				DoB2 = Date.valueOf(DoB1);
				if(btnMale.isSelected()) {
					gender = "Male";
				}else {
					gender = "Female";
				}
				name = txtName.getText();
				email = txtEmail.getText();
				password = txtPass.getText();
				int count = 0;
				if(name.length()<5||name.length()>20) {
					JOptionPane.showMessageDialog(null, "Name harus antara 5 - 20");
				}else {					
					//Try ini adalah kondisi yang dimana semua kondisinya sukses dan bisa di eksekusi
					try {
						String query2 = "SELECT * FROM users";
						Statement st2 = con.createStatement();
						ResultSet rs = st2.executeQuery(query2);
						while(rs.next()) {
							count += count;
						}
						String query = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement pst = con.prepareStatement(query);
						pst.setInt(1, count);
						pst.setString(2, name);
						pst.setString(3, email);
						pst.setString(4, password);
						pst.setDate(5, DoB2);
						pst.setString(6, gender);
						pst.setInt(7, 1);
						pst.execute();
						JOptionPane.showMessageDialog(null, "Data users added success");
						pst.close();
						Login login = new Login();
						login.setVisible(true);
						setVisible(false);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
					}
				}
			}
		});
	}
	
	Connection con;
	public Register() {
		con = sqlConnector.connector();
		setLayout(new BorderLayout());
		initiallize();
//		setVisible(true);
		setSize(600, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Register();
	}

}

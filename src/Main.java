import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Main extends JFrame{

	JPanel northPanel, centerPanel, southPanel;
	public void initiallize() {
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
//		northPanel.setBackground(Color.red);
//		centerPanel.setBackground(Color.blue);
		
		north();
		center();
		south();
	}
	
	JLabel lblTitle;
	public void north() {
		lblTitle = new JLabel("Library");
		northPanel.add(lblTitle);
		lblTitle.setHorizontalTextPosition(JLabel.CENTER);
	}
	
	JTable tblBook;
	JScrollPane scpBook;
	JPanel bottomPanel;
	public void center() {
		centerPanel.setLayout(new GridLayout(2, 0));
		tblBook = new JTable();
		scpBook = new JScrollPane();
		scpBook.getViewport().add(tblBook);
		viewTable();
		centerPanel.add(scpBook);
		bottomPanel = new JPanel();
		centerPanel.add(bottomPanel);
		bottomPanel.setBackground(Color.PINK);
		tblBook.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tblBook.rowAtPoint(e.getPoint());
				String name = tblBook.getValueAt(row, 1).toString();
				String writter = tblBook.getValueAt(row, 4).toString();
				String pages = tblBook.getValueAt(row, 3).toString();
				String id = tblBook.getValueAt(row, 0).toString();
				String genre = tblBook.getValueAt(row, 2).toString();
				String status = tblBook.getValueAt(row, 5).toString();
				txtName.setText(name);
				txtWritter.setText(writter);
				txtId.setText(id);
				txtPages.setText(pages);
				if(status.equalsIgnoreCase("Borrowed")) {					
					btnBack.setEnabled(true);
					btnBorrow.setEnabled(false);
					btnEdit.setEnabled(false);
					btnDelete.setEnabled(false);
				}
				if(status.equalsIgnoreCase("Aviabled")) {
					btnBorrow.setEnabled(true);
					btnBack.setEnabled(false);
					btnEdit.setEnabled(true);
					btnClear.setEnabled(true);
					btnDelete.setEnabled(true);					
				}
				cmbGenre.setSelectedItem(genre);
				

			}
		});
		bottom();
	}
	
	JTextField txtSearch;
	JButton btnSearch;
	public void south() {
		txtSearch = new JTextField();
		btnSearch = new JButton("Search");
		southPanel.setLayout(new GridLayout(0, 2));
		southPanel.add(txtSearch);
		southPanel.add(btnSearch);
		
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "SELECT * FROM books WHERE BookName LIKE '%"+txtName.getText()+"%' ";
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(query);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}
	
	public void viewTable() {
		DefaultTableModel model = new DefaultTableModel();
	/*0*/	model.addColumn("No"); 
	/*1*/	model.addColumn("Book Name");
	/*2*/	model.addColumn("Book Genre");
	/*3*/	model.addColumn("Book Pages");
	/*4*/	model.addColumn("Book writter");
	/*5*/	model.addColumn("Status");
		
		try {
			String query = "SELECT books.BookId, books.BookName, genres.genre, books.BookPages, books.BookWritter, books.BookStatus FROM "
					+ "books JOIN genres ON books.GenreId = genres.GenreId "
					+ "ORDER BY books.BookId ASC";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6)
				});
			}
			tblBook.setModel(model);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
		}
		
	}
	
	JPanel left, right;
	public void bottom() {
		bottomPanel.setLayout(new GridLayout(0, 2));
		left = new JPanel();
		right = new JPanel();
		bottomPanel.add(left);
		bottomPanel.add(right);
//		left.setBackground(Color.DARK_GRAY);
		right.setBackground(Color.orange);
		
		left();
		right();
	}
	
	JLabel lblId, lblName, lblGenre, lblPages, lblWritter;
	JTextField txtId, txtName, txtPages, txtWritter;
	JComboBox cmbGenre;
	public void left() {
		left.setLayout(new GridLayout(5, 2));
		lblId = new JLabel("Id");
		lblName = new JLabel("Name");
		lblGenre = new JLabel("Genre");
		lblPages = new JLabel("Pages");
		lblWritter = new JLabel("Writter");
		txtId = new JTextField();
		txtName = new JTextField();
		txtPages = new JTextField();
		txtWritter = new JTextField();
		cmbGenre = new JComboBox<String>();
//		cmbGenre.addItem("Science");
//		cmbGenre.addItem("Business");
//		cmbGenre.addItem("History");
		
		try {
			String query = "SELECT * FROM genres";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				cmbGenre.addItem(rs.getString(2));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		left.add(lblId);
		left.add(txtId);
		left.add(lblName);
		left.add(txtName);
		left.add(lblPages);
		left.add(txtPages);
		left.add(lblWritter);
		left.add(txtWritter);
		left.add(lblGenre);
		left.add(cmbGenre);
	}
	
	JButton btnAdd, btnEdit, btnDelete, btnClear, btnBorrow, btnBack;
	String bookName,  bookWritter, genre;
	Integer bookPages, genreId;
	int count=0;
	Integer bookId;
	public void right() {
		right.setLayout(new GridLayout(6, 0));
		btnAdd = new JButton("Add");
		btnEdit = new JButton("Edit");
		btnDelete = new JButton("Delete");
		btnClear = new JButton("Clear");
		btnBorrow = new JButton("Borrow");
		btnBack = new JButton("Back");
		right.add(btnAdd);
		right.add(btnBack);
		right.add(btnBorrow);
		right.add(btnClear);
		right.add(btnDelete);
		right.add(btnEdit);
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					bookName = txtName.getText();
					bookPages = Integer.parseInt(txtPages.getText());
					bookWritter = txtWritter.getText();
					genre = String.valueOf(cmbGenre.getSelectedItem());
					String searchGenre = "SELECT * FROM genres WHERE genre='"+genre+"' ";
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(searchGenre);
					if(rs.next()) {
						genreId = rs.getInt(1);
					}
					String query = "INSERT INTO books VALUES (?, ?, ?, ?, ?, ?)";
					String query2 = "SELECT * FROM books";
					Statement st2 = con.createStatement();
					ResultSet rs2 = st2.executeQuery(query2);
					while(rs2.next()) {
						count += count;
					}
					PreparedStatement pst = con.prepareStatement(query);
					pst.setInt(1, count);
					pst.setString(2, bookName);
					pst.setInt(3, bookPages);
					pst.setString(4, bookWritter);
					pst.setString(5, "Aviabled");
					pst.setInt(6, genreId);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Success add book");
					pst.close();
					viewTable();
					clear();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
				}
			}
		});

		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bookId = Integer.parseInt(txtId.getText());
				bookName = txtName.getText();
				bookPages = Integer.parseInt(txtPages.getText());
				bookWritter = txtWritter.getText();
				genre = String.valueOf(cmbGenre.getSelectedItem());
				String searchGenre = "SELECT * FROM genres WHERE genre='"+genre+"' ";
				try {
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(searchGenre);
					if(rs.next()) {
						genreId = rs.getInt(1);
					}
					String query = "UPDATE books SET BookName='"+bookName+"', "
							+ "BookPages="+bookPages+", BookWritter='"+bookWritter+"', GenreId="+genreId+" WHERE BookId="+bookId+" ";
					Statement st2 = con.createStatement();
					st2.execute(query);
					JOptionPane.showMessageDialog(null, "Edit data success");
					st2.close();
					viewTable();
					clear();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
				}
			}
		});
		
		btnBorrow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bookId = Integer.parseInt(txtId.getText());
				String bookStatus = "Borrowed";
				try {
					String query = "UPDATE books SET BookStatus='"+bookStatus+"' WHERE BookId="+bookId+" ";
					Statement st = con.createStatement();
					st.execute(query);
					JOptionPane.showMessageDialog(null, "Book borrowed");
					st.close();
					viewTable();
					clear();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
				}
			}
		});
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				bookId = Integer.parseInt(txtId.getText());
				String bookStatus = "Aviabled";
				try {
					String query = "UPDATE books SET BookStatus='"+bookStatus+"' WHERE BookId="+bookId+" ";
					Statement st = con.createStatement();
					st.execute(query);
					JOptionPane.showMessageDialog(null, "Book back");
					st.close();
					viewTable();
					clear();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				bookId = Integer.parseInt(txtId.getText());
				try {
					String query = "DELETE FROM books WHERE BookId="+bookId+" ";
					Statement st = con.createStatement();
					st.execute(query);
					JOptionPane.showMessageDialog(null, "Delete success");
					st.close();
					viewTable();
					clear();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		
		btnBack.setEnabled(false);
		btnEdit.setEnabled(false);
		btnBorrow.setEnabled(false);
		btnClear.setEnabled(false);
		btnDelete.setEnabled(false);
	}
	
	public void clear() {
		txtId.setText(null);
		txtName.setText(null);
		txtPages.setText(null);
		txtWritter.setText(null);
	}
	
	Connection con;
	public Main() {
		con = sqlConnector.connector();
		setLayout(new BorderLayout());
		initiallize();
//		setVisible(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		new Main();
	}

}

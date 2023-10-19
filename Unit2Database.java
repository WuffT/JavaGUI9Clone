import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Unit2Database {

    static JTextArea output; static JTextArea customerNum;  static JTextField usernameField;  static JPasswordField passwordField;
    static Border customBlackLine = BorderFactory.createLineBorder(new Color(0, 0, 0), 3);
    
    static JButton conButton; static JButton showCustomers; static JButton showOrders; static JButton showEmployees; static JButton loginB;
    
    public static final String SELECT_CUSTOMERS = "SELECT * FROM Customers";
    public static final String COUNT_CUSTOMERS = "SELECT COUNT(*) FROM Customers";
    public static final String SELECT_EMPLOYEES = "SELECT * FROM Employees";
    public static final String SELECT_ORDERS = "SELECT * FROM Orders";
  
    
    public static void main(String[] args) {    
        uiFrame(); //pretty much calls the entire uiFrame method to display everything
    }
    
    
    

    public static void uiFrame() {
        JFrame myFrame = new JFrame();

        myFrame.setTitle("DatabaseGui");
        myFrame.setLayout(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.setSize(840, 840);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);

        JPanel mainPanel = new JPanel();

        mainPanel.setBounds(35, 100, 750, 635);
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);
        mainPanel.setBackground(new Color(0, 0, 0, 20));
        myFrame.add(mainPanel);

        conButton = new JButton("Connect");
        conButton.setBounds(20, 150, 120, 60);
	    conButton.addActionListener(new ConnectButtonListener());
	    conButton.setFocusable(false);
	    conButton.setEnabled(true);
	    conButton.setHorizontalTextPosition(JButton.CENTER);
	    conButton.setBorder(customBlackLine);
	    mainPanel.add(conButton);
        
	    showCustomers = new JButton("Show Customers");
	    showCustomers.setBounds(20, 240, 120, 60);
	    showCustomers.addActionListener(new ConnectButtonListener());
	    showCustomers.setFocusable(false);
	    showCustomers.setEnabled(false);
	    showCustomers.setHorizontalTextPosition(JButton.CENTER);
	    showCustomers.setBorder(customBlackLine);
	    mainPanel.add(showCustomers);
	    
	    showEmployees = new JButton("Show Employees");
	    showEmployees.setBounds(20, 310, 120, 60);
	    showEmployees.addActionListener(new ConnectButtonListener());
	    showEmployees.setFocusable(false);
	    showEmployees.setEnabled(false);
	    showEmployees.setHorizontalTextPosition(JButton.CENTER);
	    showEmployees.setBorder(customBlackLine);
	    mainPanel.add(showEmployees);
	    
	    showOrders = new JButton("Show Orders");
	    showOrders.setBounds(20, 380, 120, 60);
	    showOrders.addActionListener(new ConnectButtonListener());
	    showOrders.setFocusable(false);
	    showOrders.setEnabled(false);
	    showOrders.setHorizontalTextPosition(JButton.CENTER);
	    showOrders.setBorder(customBlackLine);
	    mainPanel.add(showOrders);
	    
	    loginB = new JButton("Login");
	    loginB.setBounds(440, 80, 80, 40);
	    loginB.addActionListener(new ConnectButtonListener());
	    loginB.setFocusable(false);
	    loginB.setEnabled(false);
	    loginB.setHorizontalTextPosition(JButton.CENTER);
	    loginB.setBorder(customBlackLine);
	    mainPanel.add(loginB);
	   
	    
	     customerNum = new JTextArea();
	     customerNum.setToolTipText("Output area of information");
	     customerNum.setEditable(false);
	     customerNum.setBounds(150, 560, 500, 50);
	     customerNum.setBorder(customBlackLine);
	     customerNum.setFont(new Font("Consolas", Font.PLAIN, 15));
	     customerNum.setVisible(true);
	     customerNum.setLineWrap(true);
	     customerNum.setWrapStyleWord(true);
	     mainPanel.add(customerNum);
	     
	     JLabel usernameLabel = new JLabel("Username:");
	     usernameLabel.setBounds(160, 70, 100, 30);
	     usernameLabel.setVisible(true);
	     mainPanel.add(usernameLabel);
	     
	     JLabel passwordLabel = new JLabel("Password:");
	     passwordLabel.setBounds(160, 100, 100, 30);
	     passwordLabel.setVisible(true);
	     mainPanel.add(passwordLabel);
	     
	     	usernameField = new JTextField();
	        usernameField.setBounds(230, 70, 200, 30); // Adjust the size and position as needed
	        usernameField.setBorder(customBlackLine);
	        usernameField.setVisible(true);
	        mainPanel.add(usernameField);
	        

	        passwordField = new JPasswordField();
	        passwordField.setBounds(230, 100, 200, 30); // Adjust the size and position as needed
	        passwordField.setBorder(customBlackLine);
	        passwordField.setVisible(true);
	        mainPanel.add(passwordField);
	        
	        
        output = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBounds(150, 150, 500, 380);  // Adjust the size and position as needed
        output.setToolTipText("Output area of information");
        output.setEditable(false);
        output.setBorder(customBlackLine);
        output.setFont(new Font("Consolas", Font.PLAIN, 15));
        output.setVisible(true);
        output.setWrapStyleWord(true);
        mainPanel.add(scrollPane);
      
   //These two methods are just here to make sure all the GUI/Components load correctly    
   myFrame.revalidate();
   myFrame.repaint();
        
    }
   
   static class ConnectButtonListener implements ActionListener {
		 static Connection connection;
		 
    public void actionPerformed(ActionEvent e) {
    
    	//Each button triggers an action, Connection button will make other buttons enabled once it connects successfully.
    	if(e.getSource() == conButton) {
    		   try {
    	            System.out.println("command success");
    	            JOptionPane.showMessageDialog(null, "Connected");
    	            connection = DatabaseCon.getConnection();
    	            output.setText("Connection Successful....\nPlease login to enable certain actions");
    	            customerNum.setText("");
    	            loginB.setEnabled(true);
    	        } catch (Exception e1) {
    	            e1.printStackTrace();
    	            JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	        }
    		}
    	
    	//showCustomers will display companyID and customer/contact name as well as how many customers there are in the display.
    	if (e.getSource() == showCustomers) {
    		  	output.setText("");
	            customerNum.setText("");
	            try (Statement statement = connection.createStatement()) {
	               
	                ResultSet rs = statement.executeQuery(SELECT_CUSTOMERS);
	                while (rs.next()) {
	                    System.out.println(rs.getString("CustomerID") + " " + rs.getString("ContactName"));
	                    String data = "|CompanyID|" + rs.getString("CustomerID") + " |ContactName| " + rs.getString("ContactName") + " |ContactTitle| " + rs.getString("ContactTitle");
	                    output.append(data + "\n");
	                }
	                
	                // Count query statement
	                try (Statement countStatement = connection.createStatement()) {
	                    ResultSet rsCount = countStatement.executeQuery(COUNT_CUSTOMERS);
	                    while (rsCount.next()) {
	                        System.out.println(rsCount.getInt(1));
	                        String dataCount = "There are a total of " + rsCount.getInt(1) + " Customers";
	                        customerNum.append(dataCount);
	                    }
	                }
	                
	            } catch (Exception e1) {
	                e1.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            } 
	          }
    	//showEmployees will display employeeID, last name, first name, etc.
    	if(e.getSource() == showEmployees) {
    		output.setText("");
            customerNum.setText("");
            try (Statement statement = connection.createStatement()) {
               
                ResultSet rs = statement.executeQuery(SELECT_EMPLOYEES);
                while (rs.next()) {
                    String data = "|EmployeeID|" + rs.getString("EmployeeID") + " |LastName| " + rs.getString("LastName") + " |FirstName| " + rs.getString("FirstName")+ " |Title| " + rs.getString("Title");
                    output.append(data + "\n");
                }
                
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } 
            
    	}
    	//showOrders will display OrderID, CustomerID, EmployeeID, OrderDate, etc.
    	if(e.getSource() == showOrders) {
    		output.setText("");
            customerNum.setText("");
            try (Statement statement = connection.createStatement()) {
               
                ResultSet rs = statement.executeQuery(SELECT_ORDERS);
                while (rs.next()) {
                    String data = "|OrderID|" + rs.getString("OrderID") + " |CustomerID| " + rs.getString("CustomerID") + " |EmployeeID| " + rs.getString("EmployeeID") + " |OrderDate| " + rs.getString("OrderDate") + " |RequiredDate| " + rs.getString("RequiredDate") + " |ShippedDate| " + rs.getString("ShippedDate");
                    output.append(data + "\n");
                }
                
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } 
            
    	}
    	
    	//This login button will will take input of the username and password and apply the specific role for each user.
    	if(e.getSource() == loginB) {
    		System.out.println("yes");
    		  String username = usernameField.getText();
    		    String password = new String(passwordField.getPassword());

    		    try {
    		        User user = DatabaseCon.getUser(username, password);

    		        if (user != null) {
    		            String role = user.getRole();
    		            
    		            //once the role is given each button will be enabled/disabled based on given role access
    		            switch (role) {
    		                case "SalesRole":
    		                	output.setText("");
    		    	            customerNum.setText("");
    		                	JOptionPane.showMessageDialog(null, "Succesfully Logged In As Sales User");
    		                	showCustomers.setEnabled(true);
    	    		            showEmployees.setEnabled(false);
    	    		            showOrders.setEnabled(true);
    		                    break;
    		                case "HRRole":
    		                	output.setText("");
    		    	            customerNum.setText("");
    		                	JOptionPane.showMessageDialog(null, "Succesfully Logged In As HR User");
    		                	showCustomers.setEnabled(false);
    	    		            showEmployees.setEnabled(true);
    	    		            showOrders.setEnabled(false);
    		                    break;
    		                case "CEORole":
    		                	output.setText("");
    		    	            customerNum.setText("");
    		                	JOptionPane.showMessageDialog(null, "Succesfully Logged In As CEO User");
    	    		            showCustomers.setEnabled(true);
    	    		            showEmployees.setEnabled(true);
    	    		            showOrders.setEnabled(true);
    		                    break;
    		                default:
    		                    // Handle unknown roles
    		                    break;
    		            }

    		         

    		        } else {
    		            // login failed error
    		            JOptionPane.showMessageDialog(null, "Invalid username or password");
    		        }
    		    } catch (SQLException ex) {
    		        ex.printStackTrace();
    		        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    		    }
    	}
    	}
    }
}
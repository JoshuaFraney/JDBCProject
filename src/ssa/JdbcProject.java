package ssa;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcProject {

	static Connection myConn = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) throws Exception{
		//Run insertData to add George Washington to the student table
		insertData();
		selectData();
			System.out.println("\n");
		//Run updateData to change George's gpa, sat score, and major
		updateData();
		selectData();
			System.out.println("\n");
		//Run deleteData to delete George's information
		deleteData();
		//Run selectData to display George's information at any given time.
		selectData();
		
		//backup();
	}
	
	public static void Myclose(Connection con, Statement stmt, ResultSet rs) throws SQLException {
		
		if(con != null)
			con.close();
		
		if(stmt != null)
			stmt.close();
		
		if(rs != null)
			rs.close();
			
}
public static void selectData() throws Exception{
		
		try{
		// Load the properties file
		Properties props = new Properties();
			
		props.load(new FileInputStream("src/ssa/jdbc.properties"));
			
		// Read the props
		String theUser = props.getProperty("user");
		String thePassword = props.getProperty("password");
		String theDburl = props.getProperty("dburl");
		// Get connection to database
		myConn= (Connection)DriverManager.getConnection(theDburl, theUser, thePassword);
		
		// Create a statement
		stmt= myConn.createStatement();
		
		// Execute SQL query
		rs= stmt.executeQuery("select * from student where first_name='George' and last_name='Washington'");
		
		// Process the result set
			System.out.println("First Name" +"\t" + "Last Name" + "\t" + "SAT Score" + "\t" + "GPA" + "\t" + "Major");
			System.out.println("----------" +"\t" + "---------" + "\t" + "---------" + "\t" + "---" + "\t" + "-----");
			
		while(rs.next())
			System.out.println("  " + rs.getString("first_name") + "\t" + rs.getString("last_name") + "\t  " + rs.getInt("sat") + "\t\t"
			+ rs.getDouble("gpa") + "\t  " + rs.getInt("major_id"));
		
		
		
	}catch(Exception exc){
		
		exc.printStackTrace();
		
	}finally{
		
		Myclose(myConn, stmt, rs);
		
	}
	}
public static void updateData() throws SQLException {
	try {
		// Load the properties file
		Properties props = new Properties();
					
		props.load(new FileInputStream("src/ssa/jdbc.properties"));
					
		// Read the props
		String theUser = props.getProperty("user");
		String thePassword = props.getProperty("password");
		String theDburl = props.getProperty("dburl");
	// Get connection to database
	myConn= DriverManager.getConnection(theDburl, theUser, thePassword);
	
	// Create a statement
	stmt= myConn.createStatement();	
	// Execute SQL query
	String sql= "update student set gpa= 3.5, sat=1450, major_id=1 where id=300";
		int rowAffected= stmt.executeUpdate(sql);
		
		System.out.println("Table updated...");
	
	// Process the result set
	}catch(Exception ex){ ex.printStackTrace();}
	
	finally{
		if(myConn != null)
			myConn.close();
		if(stmt != null)
			stmt.close();
		
	}
}
public static void insertData() throws SQLException {
	try{
		// Load the properties file
		Properties props = new Properties();
					
		props.load(new FileInputStream("src/ssa/jdbc.properties"));
					
		// Read the props
		String theUser = props.getProperty("user");
		String thePassword = props.getProperty("password");
		String theDburl = props.getProperty("dburl");
		// Making connection
		myConn= (Connection)DriverManager.getConnection(theDburl, theUser, thePassword);
		// Create statement
		stmt= myConn.createStatement();
		// Execute sql
		String query= "insert into student values(300,'George','Washington',4.0,1600,null)";
		int rowAffected= stmt.executeUpdate(query);
		
		System.out.println("Row inserted...");
		// Process the result set
	}catch(Exception ex) {
		ex.printStackTrace();
	}finally {
		if(myConn != null)
			myConn.close();
		if(stmt != null)
			stmt.close();
		
	}
}
public static void deleteData() throws SQLException {
	try{
		// Load the properties file
		Properties props = new Properties();
				
		props.load(new FileInputStream("src/ssa/jdbc.properties"));
					
		// Read the props
		String theUser = props.getProperty("user");
		String thePassword = props.getProperty("password");
		String theDburl = props.getProperty("dburl");
		// Making connection
		myConn= (Connection)DriverManager.getConnection(theDburl, theUser, thePassword);
		// Create statement
		stmt= myConn.createStatement();
		// Execute sql
		String query= "delete from student where last_name='Washington' and sat=1450";
		int rowAffected= stmt.executeUpdate(query);
		
		System.out.println("Row deleted...This student no longer exists.");
								
	}catch(Exception ex) {
		ex.printStackTrace();
	}finally {
		if(myConn != null)
			myConn.close();
		if(stmt != null)
			stmt.close();
}
}

/*public static void backup() throws Exception {
	try{
		// Load the properties file
		Properties props = new Properties();
				
		props.load(new FileInputStream("src/ssa/jdbc.properties"));
					
		// Read the props
		String theUser = props.getProperty("user");
		String thePassword = props.getProperty("password");
		String theDburl = props.getProperty("dburl");
		// Making connection
		myConn= (Connection)DriverManager.getConnection(theDburl, theUser, thePassword);
		// Create statement
		stmt= myConn.createStatement();
		// Execute sql
		String query= "select * from student";
		ResultSet rowAffected= stmt.executeQuery(query);
		
		System.out.println("use tiy;");
		System.out.println("create table student ( int id primary key, varchar(30) first_name not null, varchar(30) last_name not null,"
			+	" decimal(5,1) gpa, int sat, int major_id);");
		System.out.println("alter table student add constraint fk_major_id foreign key (major_id) references major(id);");
		System.out.println("insert student (id, first_name, last_name, gpa, sat, major_id) values (100, 'Eric', 'Ephram', 3.3, 1150, 1);");
		System.out.println("insert student (id, first_name, last_name, gpa, sat, major_id) values (110, 'Greg', 'Gould', 2.0, 600, 7);");
		System.out.println("insert student (id, first_name, last_name, gpa, sat, major_id) values (120, 'Adam', 'Ant', 2.6, 1000, 2);");
		System.out.println("insert student (id, first_name, last_name, gpa, sat, major_id) values (130, 'Howard', 'Hess', 3.5, 1300, 3);");
		System.out.println("insert student (id, first_name, last_name, gpa, sat, major_id) values (140, 'Charles', 'Caldwell', 3.1, 1250, 4);");
		System.out.println("insert student (id, first_name, last_name, gpa, sat, major_id) values (150, 'James', 'Joyce', 2.8, 950, 6);");
		System.out.println("insert student (id, first_name, last_name, gpa, sat, major_id) values (160, 'Doug', 'Dumas', 3.8, 1500, 7);");
		System.out.println("insert student (id, first_name, last_name, gpa, sat, major_id) values (170, 'Kevin', 'Kraft', 3.5, 1360, 1);");
		System.out.println("insert student (id, first_name, last_name, gpa, sat, major_id) values (180, 'Frank', 'Fountain', 3.0, 1270, 4);");
		System.out.println("insert student (id, first_name, last_name, gpa, sat, major_id) values (190, 'Brian', 'Biggs', 4.0, 1600, 1);");



	}catch(Exception ex) {
		ex.printStackTrace();
		
	}finally {
		if(myConn != null)
			myConn.close();
		if(stmt != null)
			stmt.close();
}
}
*/
}



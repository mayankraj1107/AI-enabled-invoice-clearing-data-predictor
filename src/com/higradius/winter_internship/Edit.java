package com.higradius.winter_internship;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;


@WebServlet("/edit")
public class Edit extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public static Connection conn = null;
	public static PreparedStatement pst = null;
	static ArrayList<Csv_pojo> data ;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/h2h_internship";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuffer jb = new StringBuffer();
		String line = null;
		Csv_pojo invoice = new Csv_pojo();
		
		try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		try {
			JSONObject jsonObject =  new JSONObject(jb.toString());
	
			invoice.setTotal_open_amount(Double.parseDouble(jsonObject.getString("total_open_amount")) );
			invoice.setNotes(jsonObject.getString("notes"));
			invoice.setDoc_id((long)Double.parseDouble(jsonObject.getString("doc_id")));
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false); 
			
			String sql = "UPDATE invoice_details SET total_open_amount = ?, notes = ? WHERE doc_id = ?";
			pst = conn.prepareStatement(sql);
			
			pst.setString(2, invoice.getNotes());
			pst.setDouble(1, invoice.getTotal_open_amount());
			pst.setLong(3, invoice.getDoc_id());
			
			pst.addBatch();
			pst.executeBatch();
			conn.commit();
			conn.close();
			
			System.out.print("Editing Successfull !!");
				
		} catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(pst!=null)
					pst.close();
			}catch(SQLException se2){
			// nothing we can do
			}
			try{
				if(conn!=null)
				conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
				
	}
	
}



package com.higradius.winter_internship;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject; 


@WebServlet("/add")
public class AddEditServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public static Connection conn = null;
	public static PreparedStatement pst = null;
	static ArrayList<Csv_pojo> data ;
	
	// Database credentials
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/h2h_internship";
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

			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
			invoice.setCust_number(jsonObject.getString("cust_number"));
			invoice.setName_customer(jsonObject.getString("name_customer"));
			invoice.setInvoice_id((long)Double.parseDouble(jsonObject.getString("invoice_id")));
			invoice.setTotal_open_amount(Double.parseDouble(jsonObject.getString("total_open_amount")));
			invoice.setDue_in_date(new java.sql.Date((format1.parse(jsonObject.getString("due_in_date")).getTime())));
			invoice.setDoc_id((long)Double.parseDouble(jsonObject.getString("doc_id")));
			invoice.setNotes(jsonObject.getString("notes"));
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO invoice_details (cust_number,name_customer,invoice_id,total_open_amount,due_in_date,doc_id,notes) VALUES (?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			
			pst.setString(1, invoice.getCust_number());
			pst.setString(2, invoice.getName_customer());
			Long id = invoice.getInvoice_id();
			if(id != null) {
				pst.setLong(3, id);
			}else {
				pst.setNull(3, java.sql.Types.INTEGER);
			}
			pst.setDouble(4, invoice.getTotal_open_amount());
		    pst.setDate(5, invoice.getDue_in_date());
			pst.setLong(6, invoice.getDoc_id());
			pst.setString(7, invoice.getNotes());
			
			pst.addBatch();
			pst.executeBatch();
			conn.commit();
			conn.close();
			
			System.out.print("Insertion Successfull !!");
				
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



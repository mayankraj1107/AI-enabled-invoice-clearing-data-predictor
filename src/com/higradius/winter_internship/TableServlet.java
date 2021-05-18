package com.higradius.winter_internship;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.google.gson.Gson;

@WebServlet("/TABLE")
public class TableServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public static Connection conn;
	public static PreparedStatement pst;
	static ArrayList<Csv_pojo> data ;
	
	// Database credentials
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/h2h_internship";	
	static final String USER = "root";
	static final String PASS = "root";
	

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			PrintWriter out = response.getWriter();
			
			//String selectQuery = "select name_customer,cust_number,invoice_id,total_open_amount,due_in_date,clear_date,doc_id from invoice_details";
			
			data = new ArrayList<Csv_pojo>();
			
			try {
				System.out.println("abd"+request.getQueryString());
				System.out.println(request.getParameter("page"));
				String page = request.getParameter("page");
				Integer pg =Integer.parseInt(page);
				Integer val = 100 * pg;
//				String searchkey = request.getParameter("search1");
//				System.out.println(" serch key = " +searchkey);
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				
			    String selectQuery1 = "select name_customer,cust_number,invoice_id,total_open_amount,due_in_date,clear_date,doc_id,notes from invoice_details limit 100 offset " + val + ";";
//			    String selectQuery2= "SELECT name_customer,cust_number,invoice_id,total_open_amount,due_in_date,clear_date,doc_id,notes FROM invoice_details WHERE invoice_id LIKE '"+searchkey+"%';";
				//String selectQuery = "select name_customer,cust_number,invoice_id,total_open_amount,due_in_date,clear_date,doc_id from invoice_details" + val + ";";
				//creating a pst object
//			    System.out.println(selectQuery1);
//			    if(searchkey == null) {
//			    	pst = conn.prepareStatement(selectQuery1);  	
//			    }else {
//			    	pst = conn.prepareStatement(selectQuery2);  
//			    }
			    pst = conn.prepareStatement(selectQuery1);  
				
				
				ResultSet rs = pst.executeQuery();
				
				int count = 0;
				while(rs.next()) {
					Csv_pojo invoice = new Csv_pojo();
					
					invoice.setName_customer(rs.getString(1));
					invoice.setCust_number(rs.getString(2));
					invoice.setInvoice_id(rs.getLong(3));
					invoice.setTotal_open_amount(rs.getDouble(4));
					invoice.setDue_in_date(rs.getDate(5));
			     	invoice.setClear_date(rs.getTimestamp(6));
					invoice.setDoc_id(rs.getLong(7));
					String note  = rs.getString(8);
					if(note == null) {
						invoice.setNotes("Lorem Ipsum dolor...");
					}else {
						invoice.setNotes(note);
					}
					
					data.add(invoice);
					count++;
					
					if(count==100)
						break;
				}
				
				//Clean up environment
				rs.close();
				pst.close();
				conn.close();
				
				//Creating JSON object
				Gson gson = new Gson();
				String dataj = gson.toJson(data);

				out.print(dataj);
				response.setStatus(200);
				
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




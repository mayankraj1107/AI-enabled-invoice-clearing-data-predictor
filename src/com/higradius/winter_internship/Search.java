package com.higradius.winter_internship;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONObject;
import com.google.gson.Gson;

@WebServlet("/search")
public class Search extends HttpServlet{
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
		String s = null;
		data = new ArrayList<Csv_pojo>();
		
		try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		} catch (Exception e) { 
			e.printStackTrace();
		}

		try {
			JSONObject jsonObject =  new JSONObject(jb.toString());
			s = jsonObject.getString("key");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false); 
			
			String sql = "SELECT name_customer,cust_number,invoice_id,total_open_amount,due_in_date,clear_date,doc_id from invoice_details WHERE invoice_id LIKE ?";
			pst = conn.prepareStatement(sql);
			System.out.println(s);
			pst.setString(1, s + "%");
			
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
				
				
				data.add(invoice);
				
			}

			
			//Clean up environment
			rs.close();
			pst.close();
			
			doGet(request, response);
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		Gson gson = new Gson();
		String dataj = gson.toJson(data);

		out.print(dataj);
		response.setStatus(200);
		System.out.println("search successfull !!");

	}
	
}



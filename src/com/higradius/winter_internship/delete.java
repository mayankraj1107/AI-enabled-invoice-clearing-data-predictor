package com.higradius.winter_internship;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@WebServlet("/delete")
public class delete extends HttpServlet{
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
		ArrayList<Long> doc = new ArrayList<>();
		
		try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		} catch (Exception e) { 
			e.printStackTrace();
		}

		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jb.toString());
			JSONArray jsonArray = (JSONArray) jsonObject.get("doc_id");
			
			@SuppressWarnings("unchecked")
			Iterator<Long> iterator = jsonArray.iterator();
			
			while(iterator.hasNext()) {
				  doc.add(iterator.next());
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
 
			String sql = "DELETE FROM invoice_details WHERE doc_id = ?";
			pst = conn.prepareStatement(sql);
			
			for(int i = 0; i < doc.size(); i++) {
				pst.setLong(1, doc.get(i));
				pst.addBatch();	
			}
			
			pst.executeBatch();
			conn.commit();
			conn.close();
			
			System.out.println("deletion Successfull !!");
				
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

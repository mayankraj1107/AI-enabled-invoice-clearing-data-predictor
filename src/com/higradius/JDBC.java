package com.higradius;

import java.util.*;
import java.sql.*;
import com.higradius.pojo.Avengers;




public class JDBC {

	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	  static final String DB_URL = "jdbc:mysql://localhost:3306/winter_internship";
	  // Database credentials
	  static final String USER = "root";
	  static final String PASS = "root";
	  
	  public static void main(String[] args) {
	    Connection conn = null;
	    Statement stmt = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    
	    Scanner scn = new Scanner(System.in);
	    
	    try {
	      Class.forName("com.mysql.cj.jdbc.Driver");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      int n = -1;
	      while(n != 0) {
	    	  System.out.println("Enter 1 to see database!! \nEnter 2 to see Desired Column!! \nEnter 0 to Exit!!! \n");
	    	  
	    	  n = scn.nextInt();
	    	  
	    	  if (n == 1) {
	    	      stmt = conn.createStatement();
	    	      
	    	      String sql;
	    	      sql = "SELECT * FROM avengers";
	    	      
	    	      rs = stmt.executeQuery(sql);
	    	      
	    	      List<Avengers> data = new ArrayList<>();
	    	      
	    	      while(rs.next()) {
	    	    	  Avengers avg = new Avengers();
	    	    	  
	    	    	  avg.setFirst_Name(rs.getString("First_Name"));
	    	    	  avg.setLast_Name(rs.getString("Last_Name"));
	    	    	  avg.setAlias(rs.getString("Alias"));
	    	    	  avg.setQuote(rs.getString("Quote"));
	    	    	  avg.setSerial(rs.getInt("Serial"));
	    	    	  
	    	    	  data.add(avg);
	    	      }
	    	      
	    	      int count = 1;
	    	      
	    	      for(int i = 0; i < data.size();i++) {
	    	    	  System.out.println("Row " + Integer.toString(count++) + ":" );
	    	    	  System.out.println(); 
	    	    	  System.out.println("Serial : " + data.get(i).Serial);
	    	    	  System.out.println("First Name : " + data.get(i).First_Name + "\t");
	    	    	  System.out.println("Last Name : " + data.get(i).Last_Name + "\t");
	    	    	  System.out.println("Alias : " + data.get(i).Alias + "\t");
	    	    	  System.out.println("Quote : " + data.get(i).Quote + "\t");
	    
	    	    	  System.out.println();  
	    	      } 
	    	      try {
	  				if (rs != null) {
	  					rs.close();
	  				}
	  		      }catch (SQLException e3) {
	  				e3.printStackTrace();
	  			  }
	    	  }
	    	  if (n == 2) {
	    	      System.out.println("Enter Serial No: \n");
	    	      Integer key = scn.nextInt();
	    	      
	    	      String sql1="select Alias, Quote from Avengers where Serial=?";
			      pst=conn.prepareStatement(sql1);
			      pst.setLong(1,key);
			      
			      rs = pst.executeQuery();
			      
			      List<Avengers> data1 = new ArrayList<>();
			      
			      while(rs.next()) {
			    	  Avengers avg = new Avengers();
			    	  
			    	  avg.setAlias(rs.getString("Alias"));
			    	  avg.setQuote(rs.getString("Quote"));
			    	  
			    	  data1.add(avg);  
			      }
			     
			      
			      for(int i = 0; i < data1.size(); i++) {
			    	  System.out.println("For Serial :" + Integer.toString(key));
			    	  System.out.println();
			    	  System.out.println("Alias : " + data1.get(i).Alias + "\t");
	    	    	  System.out.println("Quote : " + data1.get(i).Quote + "\t");
			    	  System.out.println();
			      }
			      try {
						if (rs != null) {
							rs.close();
						}
					} catch (SQLException e3) {
						e3.printStackTrace();
					}
	    	  }  
	      }
		  
	    } catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	    	try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}  // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
			if (scn != null) {
				scn.close();
			}
		}
	    System.out.println("GoodBye!");
	  }
}

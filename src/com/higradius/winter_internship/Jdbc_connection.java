package com.higradius.winter_internship;

import java.util.*;
//import java.io.IOException;
import java.sql.*;

public class Jdbc_connection {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/h2h_internship";
	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	public static void main(String[] args) {
		// Sql Objects
		Connection conn = null;
		PreparedStatement pst = null;

		// Data reading and pojo object list creation
		Data_reader obj = new Data_reader();
		ArrayList<Csv_pojo> arr = obj.data_read();
		int batchSize = 1000;
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO invoice_details (business_code, cust_number, name_customer, clear_date, business_year, "
					+ "doc_id, posting_date, document_create_date, document_create_date_1, due_in_date, invoice_currency, document_type,"
					+ " posting_id, area_business, total_open_amount, baseline_create_date, cust_payment_terms, invoice_id, isOpen)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			
			int count = 1;
			
			for(int i = 0; i < arr.size(); i++) {
				pst.setString(1, arr.get(i).getBusiness_code());
				pst.setString(2, arr.get(i).getCust_number());
				pst.setString(3, arr.get(i).getName_customer());
				pst.setTimestamp(4, arr.get(i).getClear_date());
				pst.setInt(5, arr.get(i).getBuisness_year());
				pst.setLong(6, arr.get(i).getDoc_id());
				pst.setDate(7, arr.get(i).getPosting_date());
				pst.setDate(8, arr.get(i).getDocument_create_date());
				pst.setDate(9, arr.get(i).getDocument_create_date_1());
				pst.setDate(10, arr.get(i).getDue_in_date());
				pst.setString(11, arr.get(i).getInvoice_currency());
				pst.setString(12, arr.get(i).getDocument_type());
				pst.setInt(13, arr.get(i).getPosting_id());
				pst.setString(14, arr.get(i).getArea_business());
				pst.setDouble(15, arr.get(i).getTotal_open_amount());
				pst.setDate(16, arr.get(i).getBaseline_create_date());
				pst.setString(17, arr.get(i).getCust_payment_terms());
				
				Long id = arr.get(i).getInvoice_id();
				if(id != null) {
					pst.setLong(18, id);
				}else {
					pst.setNull(18, java.sql.Types.INTEGER);
				}
				
				pst.setInt(19, arr.get(i).getIsOpen());
				
				pst.addBatch();
				
				if (count % batchSize == 0) {
                    pst.executeBatch();
                }
				
				count++;
			}
			
			pst.executeBatch();
			conn.commit();
			conn.close();
			System.out.print("Insertion Successfull !!");
					

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException es) {
				es.printStackTrace();
			}
		}

	}

}

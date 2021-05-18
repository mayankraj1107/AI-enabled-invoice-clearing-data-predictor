package com.higradius.winter_internship;

import java.io.BufferedReader;
//import java.util.*;
import java.io.FileReader;
import java.sql.Timestamp;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.sql.Date;

public class Data_reader {

	public ArrayList<Csv_pojo> invoice_data = new ArrayList<Csv_pojo>();

	public  ArrayList<Csv_pojo> data_read() {
//		C:\Users\KIIT\Desktop\FLASK\HighRadius
		String path ="C:\\Users\\KIIT\\Desktop\\FLASK\\HighRadius\\1805301.csv";
		String line = "";
		Csv_pojo invoice;
		BufferedReader bfr = null;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
      //SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		//Date date = format.parse(string);
		//System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
		
		//Timestamp.valueOf(text);
		//Date.valueOf(str);
		
//		String startDate="01-02-2013";
//		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
//		java.util.Date date = sdf1.parse(startDate);
//		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime())

		try {

			bfr = new BufferedReader(new FileReader(path));
			bfr.readLine();

			while ((line = bfr.readLine()) != null) {

				String[] values = line.split(",");
				invoice = new Csv_pojo();
				

				invoice.setBusiness_code(values[0]);
				invoice.setCust_number(values[1]);
				invoice.setName_customer(values[2]);
				try {
					invoice.setClear_date(Timestamp.valueOf(values[3]));
				} catch (Exception e) {
					invoice.setClear_date(null);
				}
				invoice.setBuisness_year((int) Double.parseDouble(values[4]));
				invoice.setDoc_id((long)Double.parseDouble(values[5]));
				invoice.setPosting_date(new java.sql.Date((format1.parse(values[6]).getTime())));
				invoice.setDocument_create_date( new java.sql.Date((format.parse(values[7]).getTime())));
				invoice.setDocument_create_date_1(new java.sql.Date((format.parse(values[8]).getTime())));
				invoice.setDue_in_date(new java.sql.Date((format.parse(values[9]).getTime())));
				invoice.setInvoice_currency(values[10]);
				invoice.setDocument_type(values[11]);
				invoice.setPosting_id((int)Double.parseDouble(values[12]));
				invoice.setArea_business(null);
				invoice.setTotal_open_amount(Double.parseDouble(values[14]) );
				invoice.setBaseline_create_date(new java.sql.Date((format.parse(values[15]).getTime())));
				invoice.setCust_payment_terms(values[16]);
				try {
					invoice.setInvoice_id((long)Double.parseDouble(values[17]));
				} catch (Exception e) {
					invoice.setInvoice_id(null);
				}
				
				invoice.setIsOpen(Integer.parseInt(values[18]));

				invoice_data.add(invoice);

			}
			bfr.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return invoice_data;
	}

}

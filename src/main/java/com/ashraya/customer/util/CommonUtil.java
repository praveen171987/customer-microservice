package com.ashraya.customer.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

	public static Timestamp convertStringToTimestamp(String dateTime) {
		  DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	      Date date;
		try {
			date = formatter.parse(dateTime);
			 Timestamp timeStampDate = new Timestamp(date.getTime());
			 return timeStampDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	      return null;
	}
}

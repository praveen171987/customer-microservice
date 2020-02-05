package com.ashraya.customer.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CommonUtil {

    public static Timestamp convertStringToTimestamp(String dateTime) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return new Timestamp((formatter.parse(dateTime)).getTime());
    }
}

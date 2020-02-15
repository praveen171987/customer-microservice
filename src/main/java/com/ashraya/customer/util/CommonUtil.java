package com.ashraya.customer.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

import com.ashraya.customer.constants.Constants;

public class CommonUtil {

    public static Timestamp convertStringToTimestamp(String dateTime) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(Constants.DATEANDTIME_FORMAT);
        return new Timestamp((formatter.parse(dateTime)).getTime());
    }

    public static int generateOTP() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }
}

package com.ashraya.customer.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ashraya.customer.LoggerService;
import com.ashraya.customer.constants.Constants;

public class SMSUtil {

    private static LoggerService log = LoggerService.createLogger(SMSUtil.class.getName());

    public static String sendOtpMessage(int otp, String senderNumber) {
        try {
            log.printStart("sending sms..");
            // Construct data
            String apiKey = "apikey=" + Constants.OTP_API_KEY;
            String message = "&message=" + otp;
            String numbers = "&numbers=" + senderNumber;
            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            return "Error " + e;
        }
    }
    
   /* public static void main(String[] args) {
        System.out.println(sendOtpMessage(1234,"7804814213"));
    }*/
}

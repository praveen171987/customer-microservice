package com.ashraya.customer.util;

import java.io.IOException;
import java.lang.reflect.Type;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import com.ashraya.customer.domain.NotificationData;
import com.ashraya.customer.domain.NotificationRequestModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class PushNotification {

    public final static String AUTH_KEY_FCM = "AIzaSyBELRd6YU6fVc8moz-w01xtRvxTISNXDl4";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    /**
     * Used for Send Notification for mobile
     * 
     * @param deviceId
     * @param body
     * @param title
     * @return
     * @throws IOException
     */
    public static boolean sendPushNotification(String deviceId, String body, String title) throws IOException {
        boolean notificationStatus = false;
        try {
            NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
            NotificationData notificationData = new NotificationData();
            /**
             * Create notification data
             */
            notificationData.setBody(body);
            notificationData.setTitle(title);
            notificationData.setPriority("high");
            notificationRequestModel.setNotification(notificationData);
            notificationRequestModel.setTo(deviceId);

            Gson gson = new Gson();
            Type type = new TypeToken<NotificationRequestModel>() {
            }.getType();

            String json = gson.toJson(notificationRequestModel, type);

            /**
             * Call FCM API
             */
            String result = sendFcmMessage(json);
            JsonObject resultObject = new Gson().fromJson(result, JsonObject.class);

            if (resultObject.get("success").getAsInt() > 0 && resultObject.get("failure").getAsInt() == 0) {
                notificationStatus = true;
            } else {
                notificationStatus = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            notificationStatus = false;
        }
        return notificationStatus;
    }

    /**
     * Call FCM API
     * 
     * @param input
     * @return
     */
    private static String sendFcmMessage(String input) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", "key=" + AUTH_KEY_FCM);

        HttpEntity<String> httpEntity = new HttpEntity<>(input, httpHeaders);
        return restTemplate.postForObject(API_URL_FCM, httpEntity, String.class);

    }

    /*
     * public static void main(String[] args) { String deviceId =
     * "fDqpFgU4MPk:APA91bFw14mwFTT6W4AuzAskZ2PSKPy8PHTqLI7SWe-ut_jl7_LNyvhLdkNnam1jzP0VQW4v6rEXk2roug4npLWVsLCjv0QyfoX65zeSz__aapgW5aOFvguZV-Zi5o38CkRK5ClC4wRc"; String details =
     * "praveen content"; String title = "Praveen title"; try { System.out.println(sendPushNotification(deviceId, details, title)); } catch (IOException e) { e.printStackTrace(); }
     * }
     */
}

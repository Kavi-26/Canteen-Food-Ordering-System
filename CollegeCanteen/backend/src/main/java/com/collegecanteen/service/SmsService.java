package com.collegecanteen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class SmsService {

    @Value("${sms.api.key:}")
    private String apiKey;

    @Value("${sms.enabled:false}")
    private boolean smsEnabled;

    /**
     * Send OTP to mobile number.
     * Uses Fast2SMS API (https://www.fast2sms.com) for Indian numbers.
     * Set sms.api.key and sms.enabled=true in application.properties.
     * Falls back to console log if SMS is not configured.
     */
    public boolean sendOtp(String mobileNumber, String otp) {
        System.out.println("📲 OTP for " + mobileNumber + ": " + otp);

        if (!smsEnabled || apiKey == null || apiKey.isEmpty()) {
            System.out.println("⚠️ SMS not configured. OTP printed to console only.");
            return true; // Still return true so the flow continues
        }

        try {
            String message = URLEncoder.encode("Your College Canteen OTP is: " + otp + ". Valid for 2 minutes.", "UTF-8");
            String urlStr = "https://www.fast2sms.com/dev/bulkV2?" +
                    "authorization=" + apiKey +
                    "&route=otp" +
                    "&variables_values=" + otp +
                    "&flash=0" +
                    "&numbers=" + mobileNumber;

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("cache-control", "no-cache");

            int responseCode = conn.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("SMS Response [" + responseCode + "]: " + response);
            return responseCode == 200;
        } catch (Exception e) {
            System.err.println("❌ SMS sending failed: " + e.getMessage());
            return false;
        }
    }
}

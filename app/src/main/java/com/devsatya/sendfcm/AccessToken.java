package com.devsatya.sendfcm;

import android.util.Log;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AccessToken {

    private static final String firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String TAG = "AccessToken";

    public String getAccessToken() {
        try {
//
//            String jsonString = "";
            String jsonString = "";

            InputStream stream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(stream).createScoped(Lists.newArrayList(firebaseMessagingScope));
            googleCredentials.refreshIfExpired();
            String token = googleCredentials.getAccessToken().getTokenValue();
            Log.d(TAG, "getAccessToken: " + token);
            return token;
        } catch (Exception e) {
            Log.d(TAG, "getAccessTokenError: " + e.getLocalizedMessage());
            return null;
        }
    }
}

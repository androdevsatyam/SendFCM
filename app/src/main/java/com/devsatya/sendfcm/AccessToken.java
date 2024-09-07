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

            String jsonString = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"dummynotfication\",\n" +
                    "  \"private_key_id\": \"ac10cb8c5d9e8824d5b4bbccddaa5ac73a096007\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC0DIt55CfwLF2k\\nVgn3jbon4agr4xPVDY8fZTSvJ1fV4dT6Wo5UsC9XWVUWHbc+y0UZdMZXL/8d8KY5\\nVYvsb1FRG9kwLyqpR0DPaP8TRdKVWbH498Pebt2mL0jInLRJzFNK/Hz9HfvgzO+o\\nwjbpxhmj1EzNMjQdfocKYqzwZ1dfrhoE5LDwgdzJTJLA2nN9ZatZn1UD/3lnDL+t\\nvm4kQAvzEGHiYNeMSO/lAUM9e6ui/2sxDo1Q2br2N+F6/0DLXu/DFYoinT5+tUA8\\nXFn5PrNwhVbD0PjIj3DOBG6jSfxgnMiBXbyASG3G4fTUCx6ot2SH/la0EzFzPsb/\\nSpKSEqcxAgMBAAECggEAI/2n+d8qPp/Xvix2xJX/os5AbDlxsvKJbBiyfdrK+5Wv\\nmbX1YqdvZzITyJ57DWHbt7l4lmAhSq5aw/Y+SK593k1HTpL3H6x/H5bY+lTIQhOS\\neHaqoDGgPAKStYthnKjr837iS0Stn2PH+n+MM5HmeTHMgm4pLcYnn2NXN50owZa5\\ngMh5iNaJe/+46t0DkR8p8h4GdMKuOiYuWxI54S3aO5IwGcbgzWermPG4QkjImD/c\\nEa3gw0gSUb/fUcywUqQvktdiSSWT+g8AWIDG3VMpSH+F+PI5EI0PZb8yVZxxp33x\\naFLp4ec7GhtiUrMifwXDqqT7vdb2J6kP4RyFBjCjOQKBgQDbrboBpKREVvDco3E9\\nUgzw6rcSWoQe/u3vBpEWTtGSsW5X9GJgbr6zC/dwi+J29yOttRLc5Bbc0A5PCEkZ\\nwEpT2CCysrg8Z83iNCGJ5mGrSgBt8N2e2/VaVnlOG5Erunswog0VSyy1oR3JphKX\\nU6S4imyRQUDAwUK96nj5P21wpQKBgQDR0W1LbLuJ4MWgZKPgMervOmkJJYbm+t36\\nJNhg9hmWltqWeI+0OZ8MjyWRTwWX9a+hHpO7HFysxZh6Har7eDpZSBm+IW3r41C5\\nzDdYyD6jYgkqRysVKbv2qFiLAR3PCb2kS5s37vZ5P1iV84YXYKLh6pUJchrUsfXy\\nlgICetuqnQKBgBbvKi4lvCiOUxMV1SqruEImnvpBjO2Xe8uqidckX2jLMVPpZaPf\\nq/URWMHdOMVmw8jM0K1S+rgO2I+Ts94GXzwlojRFyEo2JB5rhtzYunWhgi34KYr+\\nN704re1jn2JLU7UeFfOVJ5PGwE9fES3PQ7EocnEvuMzm4Erngb2zy38pAoGBAKwL\\n6W3+k+0kJYk23PWamQVfRP+eCPdf4CMckV04j5Te0BCwfU2QLi4TNv/m5zRK3p/k\\nPckhIZD+WeBQcYLQfC/3ojw4lzTk6a3gmcWwNUStv8wuHVK2LQ29zxT0bmf1mc6d\\nvIJp7StQ2SfkTyDeOdG8FEdxjDS6VG19udokufk1AoGAHnoSR7m31jGwkxZnoHq5\\nLqx00BX8fFgKMavcV6MxKgQMIGQwjjtP/tPJuITwwI/XDQ7dJCMZniAOAikjczkQ\\n4E2UwINu95rYAkvp2HySp1GEzBejQHDDsk9/IggIkbT4VuOMP2FtIv6OpbipgUwg\\n8LgutoAMZ9/37Y9s47mY4aI=\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-tcoci@dummynotfication.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"114459977370465762533\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-tcoci%40dummynotfication.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}\n";

            InputStream stream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(stream).createScoped(Lists.newArrayList(firebaseMessagingScope));
            googleCredentials.refreshIfExpired();
            String token=googleCredentials.getAccessToken().getTokenValue();
            Log.d(TAG, "getAccessToken: " + token);
            return token;
        } catch (Exception e) {
            Log.d(TAG, "getAccessTokenError: " + e.getLocalizedMessage());
            return  null;
        }
    }
}

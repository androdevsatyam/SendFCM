package com.devsatya.sendfcm;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class SendNotification extends AppCompatActivity {

    private EditText etTitle;
    private EditText etMessage;
    private EditText etImageUrl;
    private EditText etExtraFields;
    private RadioGroup rgNotificationType;
    private EditText etDeviceToken;
    private Button btnSendNotification;
    private final String fcmUrl = "https://fcm.googleapis.com/v1/projects/" + Const.projectId + "/messages:send";
    private JSONObject mainObj = new JSONObject();
    private JSONObject messageObj = new JSONObject();
    private JSONObject notificationObj = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        etTitle = findViewById(R.id.etTitle);
        etMessage = findViewById(R.id.etMessage);
        etImageUrl = findViewById(R.id.etImageUrl);
        etExtraFields = findViewById(R.id.etExtraFields);
        rgNotificationType = findViewById(R.id.rgNotificationType);
        etDeviceToken = findViewById(R.id.etDeviceToken);
        btnSendNotification = findViewById(R.id.btnSendNotification);

        rgNotificationType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbSpecificDevice) {
                etDeviceToken.setVisibility(View.VISIBLE);
            } else {
                etDeviceToken.setVisibility(View.GONE);
            }
        });

        btnSendNotification.setOnClickListener(v -> sendNotification());
    }

    private void sendNotification() {
        String title = etTitle.getText().toString();
        String message = etMessage.getText().toString();
        String imageUrl = etImageUrl.getText().toString();
        String extraFields = etExtraFields.getText().toString();
        int checkedRadioButtonId = rgNotificationType.getCheckedRadioButtonId();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Title and Message cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            notificationObj.put("title", title);
            notificationObj.put("message", message);
            if (!TextUtils.isEmpty(imageUrl)) {
                notificationObj.put("image", imageUrl);
            }
            if (!TextUtils.isEmpty(extraFields)) {
                try {
                    JSONObject extraJson = new JSONObject(extraFields);
                    notificationObj.put("data", extraJson);
                } catch (JSONException e) {
                    Toast.makeText(this, "Invalid JSON in extra fields", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            OkHttpClient client = new OkHttpClient();
            MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");

            RequestBody body;
            switch (checkedRadioButtonId) {
                case R.id.rbTopic:
                    messageObj.put("to", "/topics/your_topic");
                    messageObj.put("notification", notificationObj);
                    mainObj.put("message", messageObj);
                    body = RequestBody.create(mainObj.toString(), jsonMediaType);
                    break;
                case R.id.rbAllUsers:
                    messageObj.put("to", "/topics/all");
                    messageObj.put("notification", notificationObj);
                    mainObj.put("message", messageObj);
                    body = RequestBody.create(mainObj.toString(), jsonMediaType);
                    break;
                case R.id.rbSpecificDevice:
                    String deviceToken = etDeviceToken.getText().toString();
                    if (TextUtils.isEmpty(deviceToken)) {
                        Toast.makeText(this, "Device Token cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    messageObj.put("token", deviceToken);
                    messageObj.put("notification", notificationObj);
                    mainObj.put("message", messageObj);
                    body = RequestBody.create(mainObj.toString(), jsonMediaType);
                    break;
                default:
                    return;
            }

            String token = new AccessToken().getAccessToken();

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, fcmUrl, mainObj, response -> {

            }, volleyError -> {

            }) {
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "Bearer " + token);
                    return header;
                }
            };

            Log.d("TAG", "sendNotification: " + objectRequest.getBody());

            requestQueue.add(objectRequest);
//            Request request = new Request.Builder()
//                    .url(fcmUrl)
//                    .post(body)
//                    .addHeader("Authorization", "Bearer " + token) // Replace with your server key from Firebase
//                    .addHeader("Content-Type", "application/json")
//                    .build();
//
//            Log.d("FCM Request", "Headers: " + request.headers());
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    runOnUiThread(() -> {
//                        Toast.makeText(SendNotification.this, "Failed to send notification: " + e, Toast.LENGTH_SHORT).show();
//                    });
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) {
//                    runOnUiThread(() -> {
//                        Toast.makeText(SendNotification.this, "Notification sent successfully", Toast.LENGTH_SHORT).show();
//                    });
//                }
//            });

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("TAG", "Excep: " + e.getLocalizedMessage());
        }
    }

}
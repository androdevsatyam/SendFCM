package com.devsatya.sendfcm;

import android.content.Context;
import android.widget.Toast;

public class Const {
    static final String projectId = "";
//    static final String projectId = "";


    public static void showToast(Context context, String text, int length){
        Toast.makeText(context,text,length).show();
    }
}

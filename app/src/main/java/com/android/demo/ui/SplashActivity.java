package com.android.demo.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

import com.android.demo.R;
import com.android.demo.utils.Globals;
import com.android.demo.utils.Ipermission.RuntimePermissionsActivity;
import com.android.demo.utils.NetworkUtil;
import com.android.demo.utils.SessionManager;

public class SplashActivity extends RuntimePermissionsActivity {
    private static final int REQUEST_PERMISSIONS = 20;
    SessionManager sessionmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        sessionmanager = new SessionManager(this);
        Globals.UserID = sessionmanager.getToken();
        SplashActivity.super.requestAppPermissions(new
                        String[]{Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
               Manifest.permission.CAMERA},
                R.string.runtime_permissions_txt,
                REQUEST_PERMISSIONS);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (NetworkUtil.isNetworkAvaliable(this)) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(this, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            startActivity(i);
        } else {
            ShowDialog("please check your network", "Attention");
        }
    }
    private void ShowDialog(String message, String Tittle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message)
                .setTitle(Tittle);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

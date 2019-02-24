package com.android.demo.ui;

import android.app.Application;

import com.aimbrain.sdk.AMBNApplication.AMBNApplication;
import com.aimbrain.sdk.Manager;
import com.microblink.MicroblinkSDK;
import com.microblink.intent.IntentDataTransferMode;

public class MyApplication extends AMBNApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MicroblinkSDK.setLicenseKey("sRwAAAAQY29tLmFuZHJvaWQuZGVtb51Efy39ytmncDOSvygGQgvOd37Emg1dsUbAS5Etjxw6FFXAWBuO2Cg3+jwEoEnHsXJldokQ05hIuSVs6LAPN6r5gIeV23KWKnPbtktpM9Jj045OS0fg1YLbNzIXj64HAaOG67eQOUFMN5QH8Uibu8nzamn7CcbT8qsAgNVVpP5aVxaSd8Co16e4E8/CVMrpekfyC6/A7dka5v9XrtKaoCzJuSG+aPsNHKfk5sXZWhVfw0qgimE1kfP28tyIBA==", this);

        MicroblinkSDK.setIntentDataTransferMode(IntentDataTransferMode.PERSISTED_OPTIMISED);
        Manager.getInstance().startCollectingData(null);

        Manager.getInstance().configure("2c6f5846-55b4-43d4-a63d-b722942412ed", "Sxo8QxtNgHvBWc7zgOlN70WaUto+EYmV3fg2UQkO5HRDyERJSd3RyP2K5OyTGKEhRjxz6GGF//OBsf6wAxzltA==");
    }
}
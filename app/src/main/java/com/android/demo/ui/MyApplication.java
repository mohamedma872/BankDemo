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

        Manager.getInstance().configure("b8ab1cae-856a-4b02-983f-b105bcd48bc2", "D9OaQIc1EgVNeeFRAt06HP1nCQ2OFomXMPDRopvsdidDgbj/JWj4vkkOtmJ1dXDGvfqr9CWv0tzPgyku2JNWYA==");
    }
}
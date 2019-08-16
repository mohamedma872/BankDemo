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
        MicroblinkSDK.setLicenseKey("sRwAAAAQY29tLmFuZHJvaWQuZGVtb51Efy39ytmncDOSvym+466WB5SoWLPLGzpr0vsOBF6/hZZNzG1N6lo0lN8Cc0jsZf9x0R2ZhZDMiS+tOuA747ZYHOI4lIJ307ZhrcqnqFabUuI4FJJJzjqCWPBP3wGujVsC3d50rhzCfmxUNeOxA8stQN6GDKFJ3rP1+q91iT8H7fId3ruF9CZpLH17MfgwG7C6y+zWP2/CPRcdyNQhfEEwerpkY+Uk8DhjZjqv7mvC4P59vlketAhRNVgLmteOaODG", this);

        MicroblinkSDK.setIntentDataTransferMode(IntentDataTransferMode.PERSISTED_OPTIMISED);
        Manager.getInstance().startCollectingData(null);

        Manager.getInstance().configure("b8ab1cae-856a-4b02-983f-b105bcd48bc2", "D9OaQIc1EgVNeeFRAt06HP1nCQ2OFomXMPDRopvsdidDgbj/JWj4vkkOtmJ1dXDGvfqr9CWv0tzPgyku2JNWYA==");
    }
}
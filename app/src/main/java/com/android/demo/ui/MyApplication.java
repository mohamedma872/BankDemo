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
        MicroblinkSDK.setLicenseKey("sRwAAAAQY29tLmFuZHJvaWQuZGVtb51Efy39ytmncDOSvyo2QgtmO0VRSbAlHCUu5ckXmPtM2a2VWE80OYDWKQVRowLM9QKvg39u+elkydRLo5q3RWwHWcd5K0HpTGAlmZHOyZTnQJmAkugCMaBgrsF8/axctWSOkeZY+l+RO7Q63VJN3O9tILbZWOCspmNbVQNz0bAltUvkwTDklPkvyKniqLr7VDBOKKnvSmyr1U63DOqS1vMDGyOMRXzNS2ypelvga/B96pYCV6WYsae+DRcYng==", this);

        MicroblinkSDK.setIntentDataTransferMode(IntentDataTransferMode.PERSISTED_OPTIMISED);
        Manager.getInstance().startCollectingData(null);

        Manager.getInstance().configure("b8ab1cae-856a-4b02-983f-b105bcd48bc2", "D9OaQIc1EgVNeeFRAt06HP1nCQ2OFomXMPDRopvsdidDgbj/JWj4vkkOtmJ1dXDGvfqr9CWv0tzPgyku2JNWYA==");
    }
}
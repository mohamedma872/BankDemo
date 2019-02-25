package com.android.demo.result.extract.blinkid.elitepaymentcard;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.elitepaymentcard.ElitePaymentCardFrontRecognizer;
import com.android.demo.R;

public class ElitePaymentCardFrontRecognitionResultExtractor extends BlinkIdExtractor<ElitePaymentCardFrontRecognizer.Result, ElitePaymentCardFrontRecognizer> {
    @Override
    protected void extractData(ElitePaymentCardFrontRecognizer.Result result) {
        add(R.string.PPOwner, result.getOwner());
    }
}

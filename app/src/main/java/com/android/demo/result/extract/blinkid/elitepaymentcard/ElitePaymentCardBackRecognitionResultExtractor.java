package com.android.demo.result.extract.blinkid.elitepaymentcard;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.elitepaymentcard.ElitePaymentCardBackRecognizer;
import com.android.demo.R;

public class ElitePaymentCardBackRecognitionResultExtractor extends BlinkIdExtractor<ElitePaymentCardBackRecognizer.Result, ElitePaymentCardBackRecognizer> {

    @Override
    protected void extractData(ElitePaymentCardBackRecognizer.Result result) {
        add(R.string.PPPaymentCardNumber, result.getCardNumber());
        add(R.string.PPValidThru, result.getValidThru());
        add(R.string.PPCVV, result.getCvv());
        add(R.string.PPInventoryNumber, result.getInventoryNumber());
    }
}

package com.android.demo.utils.result.extract.blinkid.paymentcard;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.paymentcard.PaymentCardBackRecognizer;
import com.android.demo.R;

public class PaymentCardBackRecognitionResultExtractor extends BlinkIdExtractor<PaymentCardBackRecognizer.Result, PaymentCardBackRecognizer> {
    @Override
    protected void extractData(PaymentCardBackRecognizer.Result result) {
        add(R.string.PPCVV, result.getCvv());
        add(R.string.PPInventoryNumber, result.getInventoryNumber());
    }
}
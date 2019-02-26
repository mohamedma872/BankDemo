package com.android.demo.utils.result.extract.blinkid.paymentcard;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.paymentcard.PaymentCardFrontRecognizer;
import com.android.demo.R;

public class PaymentCardFrontRecognitionResultExtractor extends BlinkIdExtractor<PaymentCardFrontRecognizer.Result, PaymentCardFrontRecognizer> {
    @Override
    protected void extractData(PaymentCardFrontRecognizer.Result result) {
        add(R.string.PPPaymentCardNumber, result.getCardNumber());
        add(R.string.PPOwner, result.getOwner());
        add(R.string.PPValidThru, result.getValidThru());
    }
}
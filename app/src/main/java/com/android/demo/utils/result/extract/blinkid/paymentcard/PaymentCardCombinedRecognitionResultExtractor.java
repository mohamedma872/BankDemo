package com.android.demo.utils.result.extract.blinkid.paymentcard;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.paymentcard.PaymentCardCombinedRecognizer;
import com.android.demo.R;

public class PaymentCardCombinedRecognitionResultExtractor extends BlinkIdExtractor<PaymentCardCombinedRecognizer.Result, PaymentCardCombinedRecognizer> {

    @Override
    protected void extractData(PaymentCardCombinedRecognizer.Result result) {
        add(R.string.PPPaymentCardNumber, result.getCardNumber());
        add(R.string.PPOwner, result.getOwner());
        add(R.string.PPValidThru, result.getValidThru());
        add(R.string.PPCVV, result.getCvv());
        add(R.string.PPInventoryNumber, result.getInventoryNumber());
    }


}

package com.android.demo.utils.result.extract.blinkid.elitepaymentcard;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.elitepaymentcard.ElitePaymentCardCombinedRecognizer;
import com.android.demo.R;

public class ElitePaymentCardCombinedRecognitionResultExtractor extends BlinkIdExtractor<ElitePaymentCardCombinedRecognizer.Result, ElitePaymentCardCombinedRecognizer> {
    @Override
    protected void extractData(ElitePaymentCardCombinedRecognizer.Result result) {
        add(R.string.PPPaymentCardNumber, result.getCardNumber());
        add(R.string.PPOwner, result.getOwner());
        add(R.string.PPValidThru, result.getValidThru());
        add(R.string.PPCVV, result.getCvv());
        add(R.string.PPInventoryNumber, result.getInventoryNumber());
    }
}

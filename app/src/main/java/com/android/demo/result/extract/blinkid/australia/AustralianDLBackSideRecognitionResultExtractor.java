package com.android.demo.result.extract.blinkid.australia;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.australia.AustraliaDlBackRecognizer;
import com.android.demo.R;

public class AustralianDLBackSideRecognitionResultExtractor extends BlinkIdExtractor<AustraliaDlBackRecognizer.Result, AustraliaDlBackRecognizer> {

    @Override
    protected void extractData(AustraliaDlBackRecognizer.Result result) {
        add(R.string.PPLastName, result.getLastName());
        add(R.string.PPLicenceNumber, result.getLicenceNumber());
        add(R.string.PPAddress, result.getAddress());
        add(R.string.PPDateOfExpiry, result.getLicenceExpiry());
    }

}

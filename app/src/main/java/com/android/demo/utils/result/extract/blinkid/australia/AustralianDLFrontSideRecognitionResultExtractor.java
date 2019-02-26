package com.android.demo.utils.result.extract.blinkid.australia;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.australia.AustraliaDlFrontRecognizer;
import com.android.demo.R;

public class AustralianDLFrontSideRecognitionResultExtractor extends BlinkIdExtractor<AustraliaDlFrontRecognizer.Result, AustraliaDlFrontRecognizer> {

    @Override
    protected void extractData(AustraliaDlFrontRecognizer.Result result) {
        add(R.string.PPFullName, result.getFullName());
        add(R.string.PPAddress, result.getAddress());
        add(R.string.PPLicenceNumber, result.getLicenceNumber());
        add(R.string.PPLicenceType, result.getLicenceType());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPDateOfExpiry, result.getLicenceExpiry());
    }

}

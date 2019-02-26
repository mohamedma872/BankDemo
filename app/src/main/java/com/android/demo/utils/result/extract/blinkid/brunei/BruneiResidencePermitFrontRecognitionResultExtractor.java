package com.android.demo.utils.result.extract.blinkid.brunei;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.brunei.BruneiResidencePermitFrontRecognizer;
import com.android.demo.R;

public class BruneiResidencePermitFrontRecognitionResultExtractor extends BlinkIdExtractor<BruneiResidencePermitFrontRecognizer.Result, BruneiResidencePermitFrontRecognizer> {
    @Override
    protected void extractData(BruneiResidencePermitFrontRecognizer.Result result) {
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPFullName, result.getFullName());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
    }
}

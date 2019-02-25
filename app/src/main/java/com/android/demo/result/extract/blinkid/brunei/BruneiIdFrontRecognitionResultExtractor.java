package com.android.demo.result.extract.blinkid.brunei;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.brunei.BruneiIdFrontRecognizer;
import com.android.demo.R;

public class BruneiIdFrontRecognitionResultExtractor extends BlinkIdExtractor<BruneiIdFrontRecognizer.Result, BruneiIdFrontRecognizer> {

    @Override
    protected void extractData(BruneiIdFrontRecognizer.Result result) {
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPFullName, result.getFullName());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
    }

}

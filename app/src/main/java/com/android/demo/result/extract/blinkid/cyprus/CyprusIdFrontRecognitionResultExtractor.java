package com.android.demo.result.extract.blinkid.cyprus;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.cyprus.CyprusIdFrontRecognizer;
import com.android.demo.R;

public class CyprusIdFrontRecognitionResultExtractor extends BlinkIdExtractor<CyprusIdFrontRecognizer.Result, CyprusIdFrontRecognizer > {
    @Override
    protected void extractData(CyprusIdFrontRecognizer.Result result) {
        add(R.string.PPIdentityCardNumber, result.getIdNumber());
    }
}

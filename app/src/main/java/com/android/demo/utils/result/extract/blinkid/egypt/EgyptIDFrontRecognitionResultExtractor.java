package com.android.demo.utils.result.extract.blinkid.egypt;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.egypt.EgyptIdFrontRecognizer;
import com.android.demo.R;


public class EgyptIDFrontRecognitionResultExtractor extends BlinkIdExtractor<EgyptIdFrontRecognizer.Result, EgyptIdFrontRecognizer> {

    @Override
    protected void extractData(EgyptIdFrontRecognizer.Result result) {
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPNationalNumber, result.getNationalNumber());
    }

}

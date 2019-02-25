package com.android.demo.result.extract.blinkid.serbia;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.serbia.SerbiaIdFrontRecognizer;
import com.android.demo.R;

public class SerbianIDFrontRecognitionResultExtractor extends BlinkIdExtractor<SerbiaIdFrontRecognizer.Result, SerbiaIdFrontRecognizer> {

    @Override
    protected void extractData(SerbiaIdFrontRecognizer.Result result) {
        add(R.string.PPIssueDate, result.getIssuingDate());
        add(R.string.PPValidUntil, result.getValidUntil());
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
    }

}

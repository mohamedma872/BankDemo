package com.android.demo.result.extract.blinkid.jordan;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.jordan.JordanIdFrontRecognizer;
import com.android.demo.R;

public class JordanIDFrontRecognitionResultExtractor extends BlinkIdExtractor<JordanIdFrontRecognizer.Result, JordanIdFrontRecognizer> {

    @Override
    protected void extractData(JordanIdFrontRecognizer.Result result) {
        add(R.string.PPNationalNumber, result.getNationalNumber());
        add(R.string.PPName, result.getName());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
    }

}

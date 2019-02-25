package com.microblink.result.extract.pdf417mobi;

import com.microblink.entities.recognizers.blinkbarcode.simnumber.SimNumberRecognizer;
import com.android.demo.R;
import com.microblink.result.extract.BaseResultExtractor;

public class SimNumberRecognitionResultExtractor extends BaseResultExtractor<SimNumberRecognizer.Result, SimNumberRecognizer> {

    @Override
    protected void extractData(SimNumberRecognizer.Result result) {
        add(R.string.PPSimNumber, result.getSimNumber());
    }
}
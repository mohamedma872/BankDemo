package com.android.demo.result.extract.pdf417mobi;

import com.android.demo.result.extract.BaseResultExtractor;
import com.microblink.entities.recognizers.blinkbarcode.simnumber.SimNumberRecognizer;
import com.android.demo.R;

public class SimNumberRecognitionResultExtractor extends BaseResultExtractor<SimNumberRecognizer.Result, SimNumberRecognizer> {

    @Override
    protected void extractData(SimNumberRecognizer.Result result) {
        add(R.string.PPSimNumber, result.getSimNumber());
    }
}
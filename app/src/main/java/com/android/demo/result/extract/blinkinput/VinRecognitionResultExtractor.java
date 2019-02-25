package com.android.demo.result.extract.blinkinput;

import com.android.demo.result.extract.BaseResultExtractor;
import com.microblink.entities.recognizers.blinkbarcode.vin.VinRecognizer;
import com.android.demo.R;

public class VinRecognitionResultExtractor extends BaseResultExtractor<VinRecognizer.Result, VinRecognizer> {

    @Override
    protected void extractData(VinRecognizer.Result result) {
        add(R.string.PPVin, result.getVin());
    }
}
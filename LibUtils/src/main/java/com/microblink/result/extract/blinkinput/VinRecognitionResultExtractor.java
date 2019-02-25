package com.microblink.result.extract.blinkinput;

import com.microblink.entities.recognizers.blinkbarcode.vin.VinRecognizer;
import com.android.demo.R;
import com.microblink.result.extract.BaseResultExtractor;

public class VinRecognitionResultExtractor extends BaseResultExtractor<VinRecognizer.Result, VinRecognizer> {

    @Override
    protected void extractData(VinRecognizer.Result result) {
        add(R.string.PPVin, result.getVin());
    }
}
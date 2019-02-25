package com.android.demo.result.extract.blinkid.czechia;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.czechia.CzechiaIdBackRecognizer;
import com.android.demo.R;

public class CzechIDBackSideRecognitionResultExtractor extends BlinkIdExtractor<CzechiaIdBackRecognizer.Result, CzechiaIdBackRecognizer> {

    @Override
    protected void extractData(CzechiaIdBackRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());
        add(R.string.PPAddress, result.getPermanentStay());
        add(R.string.PPPersonalNumber, result.getPersonalNumber());
        add(R.string.PPAuthority, result.getAuthority());
    }

}

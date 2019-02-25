package com.android.demo.result.extract.blinkid.germany;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.germany.GermanyIdOldRecognizer;
import com.android.demo.R;

public class GermanOldIDRecognitionResultExtractor extends BlinkIdExtractor<GermanyIdOldRecognizer.Result, GermanyIdOldRecognizer> {

    @Override
    protected void extractData(GermanyIdOldRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
    }

}

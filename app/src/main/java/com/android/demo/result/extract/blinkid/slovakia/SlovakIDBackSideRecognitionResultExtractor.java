package com.android.demo.result.extract.blinkid.slovakia;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.slovakia.SlovakiaIdBackRecognizer;
import com.android.demo.R;

public class SlovakIDBackSideRecognitionResultExtractor extends BlinkIdExtractor<SlovakiaIdBackRecognizer.Result, SlovakiaIdBackRecognizer> {

    @Override
    protected void extractData(SlovakiaIdBackRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());
        add(R.string.PPAddress, result.getAddress());
        add(R.string.PPSurnameAtBirth, result.getSurnameAtBirth());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
        add(R.string.PPSpecialRemarks, result.getSpecialRemarks());
    }

}

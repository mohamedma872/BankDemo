package com.android.demo.utils.result.extract.blinkid.unitedArabEmirates;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.unitedArabEmirates.UnitedArabEmiratesIdBackRecognizer;

public class UnitedArabEmiratesIDBackRecognitionResultExtractor extends BlinkIdExtractor<UnitedArabEmiratesIdBackRecognizer.Result, UnitedArabEmiratesIdBackRecognizer> {

    @Override
    protected void extractData(UnitedArabEmiratesIdBackRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());
    }

}


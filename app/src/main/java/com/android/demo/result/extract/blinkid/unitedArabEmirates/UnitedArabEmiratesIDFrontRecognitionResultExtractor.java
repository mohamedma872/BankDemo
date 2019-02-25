package com.android.demo.result.extract.blinkid.unitedArabEmirates;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.unitedArabEmirates.UnitedArabEmiratesIdFrontRecognizer;
import com.android.demo.R;

public class UnitedArabEmiratesIDFrontRecognitionResultExtractor extends BlinkIdExtractor<UnitedArabEmiratesIdFrontRecognizer.Result, UnitedArabEmiratesIdFrontRecognizer> {

    @Override
    protected void extractData(UnitedArabEmiratesIdFrontRecognizer.Result result) {
        add(R.string.PPIdentityCardNumber, result.getIdNumber());
        add(R.string.PPName, result.getName());
        add(R.string.PPNationality, result.getNationality());
    }

}
package com.android.demo.utils.result.extract.blinkid.singapore;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.singapore.SingaporeIdFrontRecognizer;
import com.android.demo.R;

public class SingaporeIDFrontRecognitionResultExtractor extends BlinkIdExtractor<SingaporeIdFrontRecognizer.Result, SingaporeIdFrontRecognizer> {

    @Override
    protected void extractData(SingaporeIdFrontRecognizer.Result result) {
        add(R.string.PPIdentityCardNumber, result.getIdentityCardNumber());
        add(R.string.PPFullName, result.getName());
        add(R.string.PPRace, result.getRace());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPCountryOfBirth, result.getCountryOfBirth());
    }

}
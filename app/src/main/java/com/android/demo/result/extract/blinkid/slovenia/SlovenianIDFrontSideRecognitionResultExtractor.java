package com.android.demo.result.extract.blinkid.slovenia;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.slovenia.SloveniaIdFrontRecognizer;
import com.android.demo.R;

public class SlovenianIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<SloveniaIdFrontRecognizer.Result, SloveniaIdFrontRecognizer> {

    @Override
    protected void extractData(SloveniaIdFrontRecognizer.Result sloIdFrontResult) {
        add(R.string.PPLastName, sloIdFrontResult.getLastName());
        add(R.string.PPFirstName, sloIdFrontResult.getFirstName());
        add(R.string.PPSex, sloIdFrontResult.getSex());
        add(R.string.PPNationality, sloIdFrontResult.getNationality());
        add(R.string.PPDateOfBirth, sloIdFrontResult.getDateOfBirth());
        add(R.string.PPDateOfExpiry, sloIdFrontResult.getDateOfExpiry());
    }

}

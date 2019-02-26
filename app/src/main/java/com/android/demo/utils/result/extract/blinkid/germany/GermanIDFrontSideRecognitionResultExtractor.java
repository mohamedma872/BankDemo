package com.android.demo.utils.result.extract.blinkid.germany;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.germany.GermanyIdFrontRecognizer;
import com.android.demo.R;

public class GermanIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<GermanyIdFrontRecognizer.Result, GermanyIdFrontRecognizer> {

    @Override
    protected void extractData(GermanyIdFrontRecognizer.Result result) {
        add(R.string.PPSurname, result.getSurname());
        add(R.string.PPGivenNames, result.getGivenNames());
        add(R.string.PPNationality, result.getNationality());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPDateOfExpiry, result.getDateOfExpiry());
        add(R.string.PPCANNumber, result.getCanNumber());
    }

}

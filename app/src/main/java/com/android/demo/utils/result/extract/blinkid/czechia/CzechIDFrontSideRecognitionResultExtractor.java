package com.android.demo.utils.result.extract.blinkid.czechia;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.czechia.CzechiaIdFrontRecognizer;
import com.android.demo.R;

public class CzechIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<CzechiaIdFrontRecognizer.Result, CzechiaIdFrontRecognizer> {

    @Override
    protected void extractData(CzechiaIdFrontRecognizer.Result result) {
        add(R.string.PPLastName, result.getSurname());
        add(R.string.PPFirstName, result.getGivenNames());
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPDateOfExpiry, result.getDateOfExpiry());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
    }

}

package com.android.demo.utils.result.extract.blinkid.spain;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.spain.SpainDlFrontRecognizer;
import com.android.demo.R;

public class SpainDlFrontRecognitionResultExtractor extends BlinkIdExtractor<SpainDlFrontRecognizer.Result, SpainDlFrontRecognizer> {

    @Override
    protected void extractData(SpainDlFrontRecognizer.Result result) {
        add(R.string.PPLastName, result.getSurname());
        add(R.string.PPFirstName, result.getFirstName());
        add(R.string.PPDateOfBirth, result.getDateOfBirth().getDate());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
        add(R.string.PPValidFrom, result.getValidFrom().getDate());
        add(R.string.PPValidUntil, result.getValidUntil().getDate());
        add(R.string.PPIssuingAuthority, result.getIssuingAuthority());
        add(R.string.PPDocumentNumber, result.getNumber());
        add(R.string.PPLicenceCategories, result.getLicenceCategories());
    }
}

package com.android.demo.utils.result.extract.blinkid.germany;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.germany.GermanyDlFrontRecognizer;
import com.android.demo.R;


public class GermanyDlFrontSideRecognitionResultExtractor extends BlinkIdExtractor<GermanyDlFrontRecognizer.Result, GermanyDlFrontRecognizer> {

    @Override
    protected void extractData(GermanyDlFrontRecognizer.Result result) {
        add(R.string.PPLicenceNumber, result.getLicenceNumber());
        add(R.string.PPLastName, result.getLastName());
        add(R.string.PPFirstName, result.getFirstName());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPDateOfExpiry, result.getDateOfExpiry());
        add(R.string.PPIssuingAuthority, result.getIssuingAuthority());
        add(R.string.PPLicenceCategories, result.getLicenceCategories());
    }

}
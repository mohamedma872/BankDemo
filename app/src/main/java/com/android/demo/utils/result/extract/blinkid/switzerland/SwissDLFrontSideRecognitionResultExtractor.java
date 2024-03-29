package com.android.demo.utils.result.extract.blinkid.switzerland;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.switzerland.SwitzerlandDlFrontRecognizer;
import com.android.demo.R;

public class SwissDLFrontSideRecognitionResultExtractor extends BlinkIdExtractor<SwitzerlandDlFrontRecognizer.Result, SwitzerlandDlFrontRecognizer> {
    @Override
    protected void extractData(SwitzerlandDlFrontRecognizer.Result result) {
        add(R.string.PPFirstName, result.getFirstName());
        add(R.string.PPLastName, result.getLastName());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
        add(R.string.PPDocumentNumber, result.getLicenseNumber());
        add(R.string.PPIssuingAuthority, result.getIssuingAuthority());
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPDateOfExpiry, result.getDateOfExpiry());
        add(R.string.PPDateOfExpiryPermanent, result.isExpiryDatePermanent());
        add(R.string.PPLicenceCategories, result.getVehicleCategories());
    }
}

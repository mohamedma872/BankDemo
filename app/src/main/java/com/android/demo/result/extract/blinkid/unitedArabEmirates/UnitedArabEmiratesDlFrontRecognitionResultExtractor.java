package com.android.demo.result.extract.blinkid.unitedArabEmirates;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.unitedArabEmirates.UnitedArabEmiratesDlFrontRecognizer;
import com.android.demo.R;

public class UnitedArabEmiratesDlFrontRecognitionResultExtractor
        extends BlinkIdExtractor<UnitedArabEmiratesDlFrontRecognizer.Result, UnitedArabEmiratesDlFrontRecognizer> {

    @Override
    protected void extractData(UnitedArabEmiratesDlFrontRecognizer.Result result) {
        add(R.string.PPLicenceNumber, result.getLicenseNumber());
        add(R.string.PPLicencingAuthority, result.getLicensingAuthority());
        add(R.string.PPFullName, result.getName());
        add(R.string.PPNationality, result.getNationality());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPIssueDate, result.getIssueDate());
        add(R.string.PPDateOfExpiry, result.getExpiryDate());
        add(R.string.PPPlaceOfIssue, result.getPlaceOfIssue());
    }
}

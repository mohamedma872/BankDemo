package com.android.demo.utils.result.extract.blinkid.sweden;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.sweden.dl.SwedenDlFrontRecognizer;
import com.android.demo.R;

public class SwedenDlFrontRecognitionResultExtractor extends BlinkIdExtractor<SwedenDlFrontRecognizer.Result, SwedenDlFrontRecognizer> {
    @Override
    protected void extractData(SwedenDlFrontRecognizer.Result result) {
        add(R.string.PPLicenceNumber, result.getLicenceNumber());
        add(R.string.PPSurname, result.getSurname());
        add(R.string.PPName, result.getName());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPDateOfExpiry, result.getDateOfExpiry());
        add(R.string.PPIssuingAgency, result.getIssuingAgency());
        add(R.string.PPReferenceNumber, result.getReferenceNumber());
        add(R.string.PPLicenceCategories, result.getLicenceCategories());
    }
}

package com.android.demo.result.extract.blinkid.colombia;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.colombia.ColombiaDlFrontRecognizer;
import com.android.demo.R;

public class ColombiaDlFrontRecognitionResultExtractor extends BlinkIdExtractor<ColombiaDlFrontRecognizer.Result, ColombiaDlFrontRecognizer> {

    @Override
    protected void extractData(ColombiaDlFrontRecognizer.Result result) {
        add(R.string.PPLicenceNumber, result.getLicenceNumber());
        add(R.string.PPName, result.getName());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPAdditionalInformation, result.getDriverRestrictions());
        add(R.string.PPIssuingAgency, result.getIssuingAgency());
    }

}

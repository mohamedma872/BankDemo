package com.android.demo.utils.result.extract.blinkid.singapore;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.singapore.SingaporeDlFrontRecognizer;
import com.android.demo.R;

public class SingaporeDlFrontRecognitionResultExtractor extends BlinkIdExtractor<SingaporeDlFrontRecognizer.Result, SingaporeDlFrontRecognizer> {

    @Override
    protected void extractData(SingaporeDlFrontRecognizer.Result singaporeDlFrontResult) {
        add(R.string.PPLicenceNumber, singaporeDlFrontResult.getLicenceNumber());
        add(R.string.PPName, singaporeDlFrontResult.getName());
        add(R.string.PPDateOfBirth, singaporeDlFrontResult.getBirthDate());
        add(R.string.PPIssueDate, singaporeDlFrontResult.getIssueDate());
        add(R.string.PPValidUntil, singaporeDlFrontResult.getValidTill());
    }
}

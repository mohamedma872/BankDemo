package com.android.demo.result.extract.blinkid.singapore;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.singapore.SingaporeIdBackRecognizer;
import com.android.demo.R;

public class SingaporeIDBackRecognitionResultExtractor extends BlinkIdExtractor<SingaporeIdBackRecognizer.Result, SingaporeIdBackRecognizer> {

    @Override
    protected void extractData(SingaporeIdBackRecognizer.Result result) {
        add(R.string.PPDocumentNumber, result.getCardNumber());
        add(R.string.PPBloodType, result.getBloodGroup());
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPAddress, result.getAddress());
    }

}
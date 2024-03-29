package com.android.demo.utils.result.extract.blinkid.singapore;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.singapore.SingaporeCombinedRecognizer;
import com.android.demo.R;

public class SingaporeCombinedRecognitionResultExtractor extends BlinkIdExtractor<SingaporeCombinedRecognizer.Result, SingaporeCombinedRecognizer> {

    @Override
    protected void extractData(SingaporeCombinedRecognizer.Result result) {
        add(R.string.PPFullName, result.getName());
        add(R.string.PPBloodType, result.getBloodGroup());
        add(R.string.PPIdentityCardNumber, result.getIdentityCardNumber());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPRace, result.getRace());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPCountryOfBirth, result.getCountryOfBirth());
        add(R.string.PPAddress, result.getAddress());
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPDocumentBothSidesMatch, result.isDocumentDataMatch());
    }
}

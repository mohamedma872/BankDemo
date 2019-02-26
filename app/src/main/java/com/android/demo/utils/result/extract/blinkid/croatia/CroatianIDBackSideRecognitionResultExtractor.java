package com.android.demo.utils.result.extract.blinkid.croatia;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.croatia.CroatiaIdBackRecognizer;
import com.android.demo.R;

public class CroatianIDBackSideRecognitionResultExtractor extends BlinkIdExtractor<CroatiaIdBackRecognizer.Result, CroatiaIdBackRecognizer> {

    @Override
    protected void extractData(CroatiaIdBackRecognizer.Result result) {

        extractMRZResult(result.getMrzResult());

        add(R.string.PPResidence, result.getResidence());
        add(R.string.PPDocumentForNonResidents, result.isDocumentForNonResident());
        add(R.string.PPIssuingAuthority, result.getIssuedBy());
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPDateOfExpiryPermanent, result.isDateOfExpiryPermanent());
    }

}

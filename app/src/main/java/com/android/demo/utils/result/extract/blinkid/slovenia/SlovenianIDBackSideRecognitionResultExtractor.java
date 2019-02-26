package com.android.demo.utils.result.extract.blinkid.slovenia;

import com.android.demo.utils.result.extract.blinkid.mrtd.MrtdResultExtractor;
import com.microblink.entities.recognizers.blinkid.slovenia.SloveniaIdBackRecognizer;
import com.android.demo.R;

public class SlovenianIDBackSideRecognitionResultExtractor extends MrtdResultExtractor<SloveniaIdBackRecognizer.Result, SloveniaIdBackRecognizer> {

    @Override
    protected void extractData(SloveniaIdBackRecognizer.Result result) {
        super.extractData(result);
        add(R.string.PPAddress, result.getAddress());
        add(R.string.PPIssuingAuthority, result.getAuthority());
        add(R.string.PPIssueDate, result.getDateOfIssue());
    }

}

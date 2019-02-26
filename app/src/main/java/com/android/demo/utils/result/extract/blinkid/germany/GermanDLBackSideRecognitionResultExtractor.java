package com.android.demo.utils.result.extract.blinkid.germany;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.germany.GermanyDlBackRecognizer;
import com.android.demo.R;


public class GermanDLBackSideRecognitionResultExtractor extends BlinkIdExtractor<GermanyDlBackRecognizer.Result, GermanyDlBackRecognizer> {

    @Override
    protected void extractData(GermanyDlBackRecognizer.Result result) {
        add(R.string.PPDateOfIssueDlCategoryB, result.getDateOfIssueB10());
        add(R.string.PPDateOfIssueDlCategoryBNotSpecified, result.isDateOfIssueB10NotSpecified());
    }

}

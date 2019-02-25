package com.android.demo.result.extract.blinkid.switzerland;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.switzerland.SwitzerlandIdBackRecognizer;
import com.android.demo.R;

public class SwissIDBackSideRecognitionResultExtractor extends BlinkIdExtractor<SwitzerlandIdBackRecognizer.Result, SwitzerlandIdBackRecognizer> {

    @Override
    protected void extractData(SwitzerlandIdBackRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());

        add(R.string.PPPlaceOfOrigin, result.getPlaceOfOrigin());
        String authority = result.getAuthority();
        if (!authority.isEmpty()) {
            add(R.string.PPAuthority, authority);
        }
        add(R.string.PPIssueDate, result.getDateOfIssue().getDate());
        add(R.string.PPDateOfExpiry, result.getDateOfExpiry().getDate());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPHeight, result.getHeight());
    }
}

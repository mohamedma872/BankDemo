package com.android.demo.result.extract.blinkid.cyprus;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.cyprus.CyprusOldIdBackRecognizer;
import com.android.demo.R;

public class CyprusOldIdBackRecognitionResultExtractor extends BlinkIdExtractor< CyprusOldIdBackRecognizer.Result, CyprusOldIdBackRecognizer> {
    @Override
    protected void extractData(CyprusOldIdBackRecognizer.Result result) {
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPDateOfExpiry, result.getExpiresOn());
    }
}

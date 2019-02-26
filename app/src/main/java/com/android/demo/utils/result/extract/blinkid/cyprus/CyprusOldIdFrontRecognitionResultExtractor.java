package com.android.demo.utils.result.extract.blinkid.cyprus;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.cyprus.CyprusOldIdFrontRecognizer;
import com.android.demo.R;

public class CyprusOldIdFrontRecognitionResultExtractor extends BlinkIdExtractor< CyprusOldIdFrontRecognizer.Result, CyprusOldIdFrontRecognizer > {
    @Override
    protected void extractData(CyprusOldIdFrontRecognizer.Result result) {
        add(R.string.PPIdentityNumber, result.getIdNumber());
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPName, result.getName());
        add(R.string.PPSurname, result.getSurname());
    }
}

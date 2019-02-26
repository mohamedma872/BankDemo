package com.android.demo.utils.result.extract.blinkid.cyprus;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.cyprus.CyprusIdBackRecognizer;

public class CyprusIdBackRecognitionResultExtractor extends BlinkIdExtractor<CyprusIdBackRecognizer.Result, CyprusIdBackRecognizer> {
    @Override
    protected void extractData(CyprusIdBackRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());
    }
}
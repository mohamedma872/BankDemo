package com.android.demo.utils.result.extract.blinkid.mrtd;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdCombinedRecognizer;
import com.android.demo.R;

public class MRTDCombinedRecognitionResultExtractor extends BlinkIdExtractor<MrtdCombinedRecognizer.Result, MrtdCombinedRecognizer> {

    @Override
    protected void extractData(MrtdCombinedRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());
        add(R.string.PPDocumentBothSidesMatch, result.isDocumentDataMatch());
    }

}

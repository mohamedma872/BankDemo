package com.android.demo.utils.result.extract.blinkid.kuwait;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.kuwait.KuwaitIdBackRecognizer;
import com.android.demo.R;

public class KuwaitIdBackRecognitionResultExtractor  extends BlinkIdExtractor<KuwaitIdBackRecognizer.Result, KuwaitIdBackRecognizer> {

    @Override
    protected void extractData(KuwaitIdBackRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());

        add(R.string.PPSerialNo, result.getSerialNo());
    }
}
package com.android.demo.utils.result.extract.blinkid.mrtd;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.android.demo.utils.result.extract.blinkinput.TemplateDataExtractor;
import com.microblink.entities.recognizers.blinkid.mrtd.MrtdRecognizer;

public class MrtdRecognitionResultExtractor extends BlinkIdExtractor<MrtdRecognizer.Result, MrtdRecognizer> {

    @Override
    protected void extractData(MrtdRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());

        TemplateDataExtractor templateDataExtractor = new TemplateDataExtractor();
        mExtractedData.addAll(templateDataExtractor.extract(mContext, mRecognizer));
    }

}

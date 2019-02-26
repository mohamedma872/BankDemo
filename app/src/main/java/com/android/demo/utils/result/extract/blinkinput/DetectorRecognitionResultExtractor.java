package com.android.demo.utils.result.extract.blinkinput;

import com.android.demo.utils.result.extract.BaseResultExtractor;
import com.microblink.entities.recognizers.detector.DetectorRecognizer;
import com.android.demo.R;

public class DetectorRecognitionResultExtractor extends BaseResultExtractor<DetectorRecognizer.Result, DetectorRecognizer> {

    @Override
    protected void extractData(DetectorRecognizer.Result result) {
        mExtractedData.add(mBuilder.build(R.string.MBRecognitionStatus, result.getResultState().name()));
        // add detection location
        mExtractedData.add(mBuilder.build(R.string.PPDetectorResult, mRecognizer.getDetector().getResult().toString()));

        TemplateDataExtractor templateDataExtractor = new TemplateDataExtractor();
        mExtractedData.addAll(templateDataExtractor.extract(mContext, mRecognizer));
    }

}

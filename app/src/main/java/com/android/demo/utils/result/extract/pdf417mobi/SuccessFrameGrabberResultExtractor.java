package com.android.demo.utils.result.extract.pdf417mobi;

import com.android.demo.utils.result.extract.BaseResultExtractor;
import com.android.demo.utils.result.extract.ResultExtractorFactoryProvider;
import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.successframe.SuccessFrameGrabberRecognizer;
import com.android.demo.R;

public class SuccessFrameGrabberResultExtractor extends BaseResultExtractor<SuccessFrameGrabberRecognizer.Result, SuccessFrameGrabberRecognizer> {

    @Override
    protected void extractData(SuccessFrameGrabberRecognizer.Result result) {
        Recognizer slaveRecognizer = mRecognizer.getSlaveRecognizer();
        BaseResultExtractor slaveExtractor = ResultExtractorFactoryProvider.get().createExtractor(slaveRecognizer);
        mExtractedData.addAll(slaveExtractor.extractData(mContext, slaveRecognizer));
        mExtractedData.add(mBuilder.build(
                R.string.PPSuccessFrame,
                mRecognizer.getResult().getSuccessFrame()
        ));
    }
}

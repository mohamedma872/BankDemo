package com.android.demo.utils.result.extract;

import com.android.demo.utils.result.extract.blinkid.BlinkIdResultExtractorFactory;

public class ResultExtractorFactoryProvider {

    private static final BlinkIdResultExtractorFactory extractorFactory = new BlinkIdResultExtractorFactory();

    public static BaseResultExtractorFactory get() {
        return extractorFactory;
    }

}

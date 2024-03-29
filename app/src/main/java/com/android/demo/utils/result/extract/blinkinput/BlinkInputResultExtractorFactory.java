package com.android.demo.utils.result.extract.blinkinput;

import com.android.demo.utils.result.extract.BaseResultExtractorFactory;
import com.android.demo.utils.result.extract.pdf417mobi.BarcodeRecognitionResultExtractor;
import com.android.demo.utils.result.extract.pdf417mobi.Pdf417RecognitionResultExtractor;
import com.android.demo.utils.result.extract.pdf417mobi.SimNumberRecognitionResultExtractor;
import com.android.demo.utils.result.extract.pdf417mobi.SuccessFrameGrabberResultExtractor;
import com.microblink.entities.recognizers.blinkbarcode.barcode.BarcodeRecognizer;
import com.microblink.entities.recognizers.blinkbarcode.pdf417.Pdf417Recognizer;
import com.microblink.entities.recognizers.blinkbarcode.simnumber.SimNumberRecognizer;
import com.microblink.entities.recognizers.blinkbarcode.vin.VinRecognizer;
import com.microblink.entities.recognizers.detector.DetectorRecognizer;
import com.microblink.entities.recognizers.successframe.SuccessFrameGrabberRecognizer;

public class BlinkInputResultExtractorFactory extends BaseResultExtractorFactory {

    @Override
    protected void addExtractors() {
        add(DetectorRecognizer.class,
                new DetectorRecognitionResultExtractor());
        add(VinRecognizer.class,
                new VinRecognitionResultExtractor());

        // from pdf 417, excluding USDL
        add(SuccessFrameGrabberRecognizer.class,
                new SuccessFrameGrabberResultExtractor());
        add(BarcodeRecognizer.class,
                new BarcodeRecognitionResultExtractor());
        add(Pdf417Recognizer.class,
                new Pdf417RecognitionResultExtractor());
        add(SimNumberRecognizer.class,
                new SimNumberRecognitionResultExtractor());
    }

}

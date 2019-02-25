package com.android.demo.result.extract.blinkid.documentface;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.documentface.DocumentFaceRecognizer;
import com.android.demo.R;

public class DocumentFaceRecognitionResultExtractor extends BlinkIdExtractor<DocumentFaceRecognizer.Result, DocumentFaceRecognizer> {

    @Override
    protected void extractData(DocumentFaceRecognizer.Result result) {
        add(R.string.MBDocumentLocation, result.getDocumentLocation().toString());
        add(R.string.MBFaceLocation, result.getFaceLocation().toString());
    }
}
package com.android.demo.utils.result.extract.blinkid.colombia;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.colombia.ColombiaIdFrontRecognizer;
import com.android.demo.R;

public class ColombiaIDFrontRecognitionResultExtractor extends BlinkIdExtractor<ColombiaIdFrontRecognizer.Result, ColombiaIdFrontRecognizer> {

    @Override
    protected void extractData(ColombiaIdFrontRecognizer.Result result) {
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPFirstName, result.getLastName());
        add(R.string.PPLastName, result.getFirstName());
    }

}

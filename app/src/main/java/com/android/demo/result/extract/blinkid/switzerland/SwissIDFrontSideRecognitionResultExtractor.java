package com.android.demo.result.extract.blinkid.switzerland;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.switzerland.SwitzerlandIdFrontRecognizer;
import com.android.demo.R;

public class SwissIDFrontSideRecognitionResultExtractor extends BlinkIdExtractor<SwitzerlandIdFrontRecognizer.Result, SwitzerlandIdFrontRecognizer> {

    @Override
    protected void extractData(SwitzerlandIdFrontRecognizer.Result result) {
        add(R.string.PPLastName, result.getSurname());
        add(R.string.PPFirstName, result.getGivenName());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
    }

}


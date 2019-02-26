package com.android.demo.utils.result.extract.blinkid.malaysia;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.malaysia.MalaysiaMyKadBackRecognizer;
import com.android.demo.R;

public class MyKadBackRecognitionResultExtractor extends BlinkIdExtractor<MalaysiaMyKadBackRecognizer.Result, MalaysiaMyKadBackRecognizer> {

    @Override
    protected void extractData(MalaysiaMyKadBackRecognizer.Result result) {
        add(R.string.PPNRICNumber, result.getNric());
        add(R.string.PPExtendedNRIC, result.getExtendedNric());
        add(R.string.PPOldNRIC, result.getOldNric());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
    }

}
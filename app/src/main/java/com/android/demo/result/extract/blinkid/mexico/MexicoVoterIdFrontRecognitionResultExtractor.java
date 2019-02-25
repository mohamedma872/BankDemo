package com.android.demo.result.extract.blinkid.mexico;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.mexico.MexicoVoterIdFrontRecognizer;
import com.android.demo.R;

public class MexicoVoterIdFrontRecognitionResultExtractor extends BlinkIdExtractor<MexicoVoterIdFrontRecognizer.Result, MexicoVoterIdFrontRecognizer> {

    @Override
    protected void extractData(MexicoVoterIdFrontRecognizer.Result result) {
        add(R.string.PPFullName, result.getFullName());
        add(R.string.PPAddress, result.getAddress());
        add(R.string.PPElectorKey, result.getElectorKey());
        add(R.string.PPCURP, result.getCurp());
        add(R.string.PPDateOfBirth, result.getDateOfBirth().getDate());
        add(R.string.PPSex, result.getSex());
    }
}

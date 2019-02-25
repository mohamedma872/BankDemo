package com.android.demo.result.extract.blinkid.morocco;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.morocco.MoroccoIdFrontRecognizer;
import com.android.demo.R;

public class MoroccoIdFrontRecognitionResultExtractor extends BlinkIdExtractor<MoroccoIdFrontRecognizer.Result, MoroccoIdFrontRecognizer> {
    @Override
    protected void extractData(MoroccoIdFrontRecognizer.Result result) {
        add(R.string.PPName, result.getName());
        add(R.string.PPLastName, result.getSurname());
        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPDateOfExpiry, result.getDateOfExpiry());
    }
}

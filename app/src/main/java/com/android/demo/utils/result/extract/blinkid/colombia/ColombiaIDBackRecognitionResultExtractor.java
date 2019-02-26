package com.android.demo.utils.result.extract.blinkid.colombia;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.colombia.ColombiaIdBackRecognizer;
import com.android.demo.R;

import java.util.Arrays;


public class ColombiaIDBackRecognitionResultExtractor extends BlinkIdExtractor<ColombiaIdBackRecognizer.Result, ColombiaIdBackRecognizer> {

    @Override
    protected void extractData(ColombiaIdBackRecognizer.Result result) {
        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPFirstName, result.getFirstName());
        add(R.string.PPLastName, result.getLastName());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPDateOfBirth, result.getBirthDate());
        add(R.string.PPBloodType, result.getBloodGroup());
        add(R.string.PPFingerprint, Arrays.toString(result.getFingerprint()));
    }

}

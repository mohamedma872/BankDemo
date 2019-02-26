package com.android.demo.utils.result.extract.blinkid.kuwait;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.kuwait.KuwaitIdFrontRecognizer;
import com.android.demo.R;

public class KuwaitIdFrontRecognitionResultExtractor  extends BlinkIdExtractor<KuwaitIdFrontRecognizer.Result, KuwaitIdFrontRecognizer> {

    @Override
    protected void extractData(KuwaitIdFrontRecognizer.Result result) {
        add(R.string.PPCivilIdNumber, result.getCivilIdNumber());
        add(R.string.PPName, result.getName());
        add(R.string.PPNationality, result.getNationality());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPDateOfBirth, result.getBirthDate());
        add(R.string.PPDateOfExpiry, result.getExpiryDate());
    }

}
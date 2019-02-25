package com.android.demo.result.extract.blinkid.malaysia;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.malaysia.MalaysiaMyKadFrontRecognizer;
import com.android.demo.R;

public class MalaysiaMyKadFrontRecognitionResultExtractor extends BlinkIdExtractor<MalaysiaMyKadFrontRecognizer.Result, MalaysiaMyKadFrontRecognizer> {

    @Override
    protected void extractData(MalaysiaMyKadFrontRecognizer.Result result) {
        add(R.string.PPFullName, result.getFullName());
        add(R.string.PPAddress, result.getFullAddress());
        add(R.string.PPAddressStreet, result.getStreet());
        add(R.string.PPAddressZipCode, result.getZipcode());
        add(R.string.PPAddressCity, result.getCity());
        add(R.string.PPAddressState, result.getOwnerState());
        add(R.string.PPDateOfBirth, result.getBirthDate());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPReligion, result.getReligion());
        add(R.string.PPNRICNumber, result.getNric());
    }
}

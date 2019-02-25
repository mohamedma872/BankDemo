package com.android.demo.result.extract.blinkid.malaysia;

import com.android.demo.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.malaysia.MalaysiaMyPrFrontRecognizer;
import com.android.demo.R;

public class MalaysiaMyPrFrontRecognitionResultExtractor extends BlinkIdExtractor<MalaysiaMyPrFrontRecognizer.Result, MalaysiaMyPrFrontRecognizer> {

    @Override
    protected void extractData(MalaysiaMyPrFrontRecognizer.Result result) {
        add(R.string.PPFullName, result.getFullName());
        add(R.string.PPDateOfBirth, result.getBirthDate());
        add(R.string.PPSex, result.getSex());
        add(R.string.PPReligion, result.getReligion());
        add(R.string.PPNRICNumber, result.getNric());
        add(R.string.PPAddress, result.getFullAddress());
        add(R.string.PPAddressStreet, result.getStreet());
        add(R.string.PPAddressZipCode, result.getZipcode());
        add(R.string.PPAddressCity, result.getCity());
        add(R.string.PPAddressState, result.getOwnerState());
        add(R.string.PPCountryCode, result.getCountryCode());
    }

}
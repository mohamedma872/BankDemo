package com.android.demo.utils.result.extract.blinkid.malaysia;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.malaysia.MalaysiaDlFrontRecognizer;
import com.android.demo.R;

public class MalaysiaDLFrontRecognitionResultExtractor extends BlinkIdExtractor<MalaysiaDlFrontRecognizer.Result, MalaysiaDlFrontRecognizer> {

    @Override
    protected void extractData(MalaysiaDlFrontRecognizer.Result result) {
        add(R.string.PPIdentityNumber, result.getIdentityNumber());
        add(R.string.PPFullName, result.getName());
        add(R.string.PPNationality, result.getNationality());
        add(R.string.PPClass, result.getDlClass());
        add(R.string.PPValidFrom, result.getValidFrom());
        add(R.string.PPValidUntil, result.getValidUntil());
        add(R.string.PPAddressStreet, result.getStreet());
        add(R.string.PPAddressZipCode, result.getZipcode());
        add(R.string.PPAddressCity, result.getCity());
        add(R.string.PPAddressState, result.getOwnerState());
        add(R.string.PPAddress, result.getFullAddress());
    }

}

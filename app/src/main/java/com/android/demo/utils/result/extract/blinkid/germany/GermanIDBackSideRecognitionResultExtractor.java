package com.android.demo.utils.result.extract.blinkid.germany;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.germany.GermanyIdBackRecognizer;
import com.android.demo.R;

public class GermanIDBackSideRecognitionResultExtractor extends BlinkIdExtractor<GermanyIdBackRecognizer.Result, GermanyIdBackRecognizer> {

    @Override
    protected void extractData(GermanyIdBackRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());

        String address = result.getFullAddress();
        if (!address.isEmpty()) {
            add(R.string.PPAddress, address);
        }

        String zipCode = result.getAddressZipCode();
        if (!zipCode.isEmpty()) {
            add(R.string.PPAddressZipCode, zipCode);
        }

        String city = result.getAddressCity();
        if (!city.isEmpty()) {
            add(R.string.PPAddressCity, city);
        }

        String street = result.getAddressStreet();
        if (!street.isEmpty()) {
            add(R.string.PPAddressStreet, street);
        }

        String houseNumber = result.getAddressHouseNumber();
        if (!houseNumber.isEmpty()) {
            add(R.string.PPAddressHouseNumber, houseNumber);
        }

        String authority = result.getAuthority();
        if (!authority.isEmpty()) {
            add(R.string.PPAuthority, authority);
        }

        add(R.string.PPIssueDate, result.getDateOfIssue());

        String eyeColor = result.getColourOfEyes();
        if (!eyeColor.isEmpty()) {
            add(R.string.PPEyeColour, eyeColor);
        }

        String height = result.getHeight();
        if (!height.isEmpty()) {
            add(R.string.PPHeight, height);
        }
    }

}

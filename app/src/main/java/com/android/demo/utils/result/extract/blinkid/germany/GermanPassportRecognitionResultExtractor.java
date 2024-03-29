package com.android.demo.utils.result.extract.blinkid.germany;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.germany.GermanyPassportRecognizer;
import com.android.demo.R;

public class GermanPassportRecognitionResultExtractor extends BlinkIdExtractor<GermanyPassportRecognizer.Result, GermanyPassportRecognizer> {

    @Override
    protected void extractData(GermanyPassportRecognizer.Result result) {
        extractMRZResult(result.getMrzResult());

        String name = result.getGivenName();
        if (!name.isEmpty()) {
            add(R.string.PPFirstName, result.getGivenName());
        }

        String surname = result.getSurname();
        if (!surname.isEmpty()) {
            add(R.string.PPLastName, result.getSurname());
        }

        String authority = result.getAuthority();
        if (!authority.isEmpty()) {
            add(R.string.PPAuthority, result.getAuthority());
        }

        add(R.string.PPIssueDate, result.getDateOfIssue());

        String nat = result.getNationality();
        if (!nat.isEmpty()) {
            add(R.string.PPNationality, nat);
        }

        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
    }

}

package com.android.demo.utils.result.extract.blinkid.brunei;
import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.brunei.BruneiResidencePermitBackRecognizer;
import com.android.demo.R;

public class BruneiResidencePermitBackRecognitionResultExtractor extends BlinkIdExtractor<BruneiResidencePermitBackRecognizer.Result, BruneiResidencePermitBackRecognizer> {

        @Override
        protected void extractData(BruneiResidencePermitBackRecognizer.Result result) {
            extractMRZResult(result.getMrzResult());

            add(R.string.PPRace, result.getRace());
            add(R.string.PPAddress, result.getAddress());
            add(R.string.PPIssueDate, result.getDateOfIssue());
        }

}

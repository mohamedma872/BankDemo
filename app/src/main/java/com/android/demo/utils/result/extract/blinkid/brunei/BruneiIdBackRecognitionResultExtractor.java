package com.android.demo.utils.result.extract.blinkid.brunei;
import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.blinkid.brunei.BruneiIdBackRecognizer;
import com.android.demo.R;

public class BruneiIdBackRecognitionResultExtractor extends BlinkIdExtractor<BruneiIdBackRecognizer.Result, BruneiIdBackRecognizer> {

        @Override
        protected void extractData(BruneiIdBackRecognizer.Result result) {
            extractMRZResult(result.getMrzResult());

            add(R.string.PPRace, result.getRace());
            add(R.string.PPAddress, result.getAddress());
            add(R.string.PPIssueDate, result.getDateOfIssue());
        }

}

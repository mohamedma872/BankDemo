package com.android.demo.utils.result.extract.blinkid.mrtd;

import android.support.annotation.CallSuper;

import com.android.demo.utils.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.entities.recognizers.Recognizer;
import com.android.demo.R;
import com.microblink.recognizers.blinkid.mrtd.MRTDResult;

public abstract class MrtdResultExtractor<ResultType extends Recognizer.Result & MRTDResult, RecognizerType extends Recognizer<?, ResultType>>
        extends BlinkIdExtractor<ResultType, RecognizerType> {

    @Override
    @CallSuper
    protected void extractData(ResultType mrtdResult) {
        if (mrtdResult == null) {
            return;
        }

        add(R.string.PPMRZParsed, mrtdResult.isMrzParsed());
        add(R.string.PPMRZVerified, mrtdResult.isMrzVerified());
        add(R.string.PPPrimaryId, mrtdResult.getPrimaryId());
        add(R.string.PPSecondaryId, mrtdResult.getSecondaryId());
        add(R.string.PPDateOfBirth, mrtdResult.getDateOfBirth());
        add(R.string.PPSex, mrtdResult.getSex());
        add(R.string.PPNationality, mrtdResult.getNationality());
        add(R.string.PPDocumentCode, mrtdResult.getDocumentCode());
        add(R.string.PPIssuer, mrtdResult.getIssuer());
        add(R.string.PPDateOfExpiry, mrtdResult.getDateOfExpiry());
        add(R.string.PPOpt2, mrtdResult.getOpt2());
        add(R.string.PPDocumentNumber, mrtdResult.getDocumentNumber());
        add(R.string.PPOpt1, mrtdResult.getOpt1());
        add(R.string.PPMRZText, mrtdResult.getMrzText());
    }

}

package com.android.demo.score;

import com.aimbrain.sdk.models.ScoreModel;


public interface ScoreListener {

    public void updateScore(ScoreModel scoreModel, long timestamp);

}

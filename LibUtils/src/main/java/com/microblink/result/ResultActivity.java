package com.microblink.result;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.LruCache;
import android.view.View;

import com.google.gson.Gson;
import com.microblink.entities.parsers.config.fieldbyfield.FieldByFieldBundle;
import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.help.pageindicator.TabPageIndicator;
import com.android.demo.R;
import com.microblink.result.extract.RecognitionResultEntry;
import com.microblink.util.Globals;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultActivity extends FragmentActivity implements
        ResultFragment.IResultFragmentActivity,
        FieldByFieldResultFragment.IFieldByFieldResultFragmentActivity {

    public static final String EXTRAS_RESULT_TYPE = "EXTRAS_RESULT_TYPE";

    public enum ResultType {
        RECOGNIZER_BUNDLE,
        FIELD_BY_FIELD_BUNDLE
    }

    protected ViewPager mPager;

    protected RecognizerBundle mRecognizerBundle;
    protected FieldByFieldBundle mFieldByFieldBundle;

    protected ResultType mResultType;

    private ArrayList<Recognizer> mRecognizersWithResult;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView();
        CalculteBitmap();
        Intent intent = getIntent();
        mResultType = (ResultType) intent.getSerializableExtra(EXTRAS_RESULT_TYPE);
        mRecognizerBundle = new RecognizerBundle();
        mFieldByFieldBundle = new FieldByFieldBundle();
        if (mResultType == null) {
            if (mRecognizerBundle.existsInIntent(intent)) {
                mResultType = ResultType.RECOGNIZER_BUNDLE;
            } else if (mFieldByFieldBundle.existsInIntent(intent)) {
                mResultType = ResultType.FIELD_BY_FIELD_BUNDLE;
            }
        }
        if (mResultType == null) {
            throw new IllegalStateException("Results must be passed to ResultActivity!");
        }
        mPager = findViewById(R.id.resultPager);
        switch (mResultType) {
            case RECOGNIZER_BUNDLE:
                mRecognizersWithResult = new ArrayList<>();
                mRecognizerBundle.loadFromIntent(intent);
                for (Recognizer<Recognizer, Recognizer.Result> r : mRecognizerBundle.getRecognizers()) {
                    if (r.getResult().getResultState() != Recognizer.Result.State.Empty) {
                        mRecognizersWithResult.add(r);
                    }
                }
                mPager.setAdapter(new RecognizerListFragmentAdapter(getSupportFragmentManager()));
                break;
            case FIELD_BY_FIELD_BUNDLE:
                mFieldByFieldBundle.loadFromIntent(intent);
                mPager.setAdapter(new FieldByFieldBundleFragmentAdapter(getSupportFragmentManager()));
                break;
        }

        TabPageIndicator indicator = findViewById(R.id.resultIndicator);
        indicator.setViewPager(mPager);
        indicator.setClipChildren(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // clear saved state to be sure that data is cleared from cache and from file when
        // intent optimisation is used
        mRecognizerBundle.clearSavedState();
        mFieldByFieldBundle.clearSavedState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mResultType == ResultType.RECOGNIZER_BUNDLE) {
            mRecognizerBundle.saveState();
        } else if (mResultType == ResultType.FIELD_BY_FIELD_BUNDLE) {
            mFieldByFieldBundle.saveState();
        }
    }

    public void setActivityContentView() {
        setContentView(R.layout.result_menu);
    }

    public void footerButtonCancelClickHandler(View view) {
        finish();

        Globals.extractedData = null;
    }

    private LruCache<String, Bitmap> mMemoryCache;

    private void CalculteBitmap() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

// Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {

            mMemoryCache.put(key, bitmap);

    }
    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    public void footerButtonConfirmClickHandler(View view) {
        if (Globals.extractedData != null) {


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                Bitmap bmp =  scaleDownBitmap(Objects.requireNonNull(Globals.extractedData.get(3).getImageValue()),200,this);
                Globals.extractedData.remove(3);
                Globals.extractedData.remove(3);
                Intent myIntent = new Intent("FBR-IMAGE");
                myIntent.putExtra("data", serializeToJson(Globals.extractedData));
                myIntent.putExtra("img", bmp);
                this.sendBroadcast(myIntent);




            }


        }

        finish();
    }
   // SharedData date;
    public String serializeToJson(List<RecognitionResultEntry> myClass) {
        Gson gson = new Gson();
        String j = gson.toJson(myClass);
        return j;
    }

    @Override
    public Recognizer<Recognizer, Recognizer.Result> getRecognizerAtPosition(int resultPosition) {
        if (resultPosition < 0 || resultPosition >= mRecognizersWithResult.size()) {
            throw new IllegalStateException("Recognizer with non empty result on requested position"
                    + " does not exist. Possible cause is that recognizer bundle state has been lost"
                    + " in intent transactions.");
        }
        //noinspection unchecked
        return mRecognizersWithResult.get(resultPosition);
    }

    @Override
    public FieldByFieldBundle getFieldByFieldBundle() {
        return mFieldByFieldBundle;
    }

    private class RecognizerListFragmentAdapter extends FragmentPagerAdapter {

        RecognizerListFragmentAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return ResultFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mRecognizersWithResult.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ResultUtils.getRecognizerSimpleName(mRecognizersWithResult.get(position));
        }
    }

    private class FieldByFieldBundleFragmentAdapter extends FragmentPagerAdapter {

        FieldByFieldBundleFragmentAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return FieldByFieldResultFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ResultActivity.this.getString(R.string.title_field_by_field_results);
        }

    }
}

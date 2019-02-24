package com.android.demo.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aimbrain.sdk.Manager;
import com.aimbrain.sdk.exceptions.InternalException;
import com.aimbrain.sdk.exceptions.SessionException;
import com.aimbrain.sdk.faceCapture.VideoFaceCaptureActivity;
import com.aimbrain.sdk.models.FaceAuthenticateModel;
import com.aimbrain.sdk.models.FaceCompareModel;
import com.aimbrain.sdk.models.FaceEnrollModel;
import com.aimbrain.sdk.models.FaceTokenType;
import com.aimbrain.sdk.models.ScoreModel;
import com.aimbrain.sdk.models.SessionModel;
import com.aimbrain.sdk.models.VoiceAuthenticateModel;
import com.aimbrain.sdk.models.VoiceEnrollModel;
import com.aimbrain.sdk.models.VoiceTokenModel;
import com.aimbrain.sdk.models.VoiceTokenType;
import com.aimbrain.sdk.server.AMBNResponseErrorListener;
import com.aimbrain.sdk.server.FaceCapturesAuthenticateCallback;
import com.aimbrain.sdk.server.FaceCapturesEnrollCallback;
import com.aimbrain.sdk.server.FaceCompareCallback;
import com.aimbrain.sdk.server.ScoreCallback;
import com.aimbrain.sdk.server.VoiceCaptureEnrollCallback;
import com.aimbrain.sdk.server.VoiceCapturesAuthenticateCallback;
import com.aimbrain.sdk.server.VoiceTokenCallback;
import com.aimbrain.sdk.voiceCapture.VoiceCaptureActivity;
import com.android.demo.R;
import com.android.demo.score.ScoreManager;
import com.android.demo.utils.Globals;
import com.android.demo.utils.Spinner;
import com.android.volley.NetworkResponse;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.result.ResultActivity;
import com.microblink.result.extract.RecognitionResultEntry;
import com.microblink.uisettings.ActivityRunner;
import com.microblink.uisettings.BaseScanUISettings;
import com.microblink.uisettings.UISettings;
import com.microblink.uisettings.options.BeepSoundUIOptions;
import com.microblink.uisettings.options.HelpIntentUIOptions;
import com.microblink.uisettings.options.OcrResultDisplayMode;
import com.microblink.uisettings.options.OcrResultDisplayUIOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    private static final int authenticationRequestcode = 1543;
    private static final int voiceEnrollmentRequestcode = 1544;
    private static final int voiceAuthenticationRequestcode = 1545;
    private static final String photoAuthUpperText = "To authenticate please face the camera directly, press 'camera' button and blink";
    private static final String photoLowerText = "Position your face fully within the outline.";
    private static final String faceAuthenticationHint = "Please BLINK now...";
    private static final String[] faceEnrollStepsTexts = {
            "To enroll please face the camera directly and press 'camera' button",
            "Face the camera slightly from the top and press 'camera' button",
            "Face the camera slightly from the bottom and press 'camera' button",
            "Face the camera slightly from the left and press 'camera' button",
            "Face the camera slightly from the right and press 'camera' button"
    };
    private static final String[] voiceEnrollStepsTexts = {
            "To enroll please press 'microphone' button read text below",
            "Please press 'microphone' button read the text below 2nd time",
            "Please press 'microphone' button read the text below 3rd time",
            "Please press 'microphone' button read the text below 4th time",
            "Please press 'microphone' button read the text below the last time"
    };
    private static final String voiceAuthenticationHint = "To authenticate please press 'microphone' button and read text below";
    private int triesAmount;
    private int voiceTriesAmount;
    private boolean isActive;
    public static byte[] video;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.sigin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sigin:

                if(Globals.UserID==0)
                {
                    new AlertDialog.Builder(HomeActivity.this)
                            .setMessage("Please Register First")
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                            }).show();
                }else
                {
                    voiceAuthType();
                }

                break;
            case R.id.back:
                // user is not logged in redirect him to Login Activity
                Intent i = new Intent(this, LoginActivity.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Staring Register Activity
                startActivity(i);
                break;
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregisterReceiver(myReceiver);
        isActive = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void voiceAuthType() {
        try {
            createSessionUsingVoice();
        } catch (ConnectException | InternalException e) {
            hideSpinner();
            //errorTextView.setText(e.getMessage());
            new AlertDialog.Builder(HomeActivity.this)
                    .setTitle("voice Auth unavailable")
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    })
                    .show();
        }
    }

    private void showEnrollmentDialog() {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage("Please enroll before using Facial authentication")
                .setPositiveButton("Continue", (dialog, which) -> {
                    triesAmount = 0;
                }).setNegativeButton("Cancel", (dialog, which) -> {
            hideSpinner();
        }).setCancelable(false).show();
    }



    private void createSessionUsingVoice() throws ConnectException, InternalException {
        progressDialog = Spinner.showSpinner(this);
        Manager.getInstance().createSession(getDeviceIMEI(this) + String.valueOf(Globals.UserID), getApplicationContext(), session -> {
                    hideSpinner();
                    if (!isActive) {
                        return;
                    }
                    Log.i("SESSION", "session id: " + session.getSessionId());
                    Log.i("SESSION", "session face status: " + session.getFaceStatus());
                    Log.i("SESSION", "session behaviour status: " + session.getBehaviourStatus());
                    Log.i("SESSION", "session voice status: " + session.getVoiceStatus());
                    if (session.getVoiceStatus() == SessionModel.BUILDING) {
                        new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("Voice detection unavailable")
                                .setMessage("Generating template. Please try again in a few seconds")
                                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                })
                                .show();
                    } else if (session.getVoiceStatus() == SessionModel.ENROLLED) {
                        authenticateWithVoice();
                    } else if (session.getVoiceStatus() == SessionModel.NOT_ENROLLED) {
                        showVoiceEnrollmentDialog();
                    }
                }, new AMBNResponseErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideSpinner();
                        // errorTextView.setText(getErrorMessage(error));
                        new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("Voice detection unavailable")
                                .setMessage(error.getMessage())
                                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                })
                                .show();
                    }
                }
        );
    }

    private void showVoiceEnrollmentDialog() {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage("Please enroll  before using Voice authentication")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        voiceTriesAmount = 0;
                        enrollWithVoice();
                    }
                }).setNegativeButton("Cancel", (dialog, which) -> hideSpinner()).setCancelable(false).show();
    }

    private void enrollWithVoice() {
        if (voiceTriesAmount < 1) {
            VoiceTokenType tokenType = VoiceTokenType.ENROLL1;
            switch (voiceTriesAmount) {
                case 0:
                    tokenType = VoiceTokenType.ENROLL1;
                    break;
                case 1:
                    tokenType = VoiceTokenType.ENROLL2;
                    break;
                case 2:
                    tokenType = VoiceTokenType.ENROLL3;
                    break;
                case 3:
                    tokenType = VoiceTokenType.ENROLL4;
                    break;
                case 4:
                    tokenType = VoiceTokenType.ENROLL5;
                    break;
            }

            Log.d("SignInActivity", "Voice token type " + tokenType.toString());
            try {
                //progressDialog = Spinner.showSpinner(this);
                Manager.getInstance().getVoiceToken(tokenType, tokenModel -> {
                    hideSpinner();
                    if (!isActive) {
                        return;
                    }
                    openVoiceCaptureActivity(voiceEnrollmentRequestcode, voiceEnrollStepsTexts[voiceTriesAmount],
                            tokenModel.getToken());
                });
            } catch (InternalException | SessionException | ConnectException e) {
                Log.e("signingActivity", "voiceEnroll", e);
            }
        } else {
            hideSpinner();
            new AlertDialog.Builder(HomeActivity.this)
                    .setMessage("Voice Enrollment finished successfully we will transfer you to login screen")
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        // user is not logged in redirect him to Login Activity
                        Intent i = new Intent(this, HomeActivity.class);
                        // Closing all the Activities
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // Staring Register Activity
                        startActivity(i);

                    }).show();
        }
    }

    private void authenticateWithVoice() {
        try {
            progressDialog = Spinner.showSpinner(this);
            Manager.getInstance().getVoiceToken(VoiceTokenType.AUTH, new VoiceTokenCallback() {
                @Override
                public void success(VoiceTokenModel tokenModel) {
                    hideSpinner();
                    if (!isActive) {
                        return;
                    }
                    openVoiceCaptureActivity(voiceAuthenticationRequestcode, voiceAuthenticationHint, tokenModel.getToken());
                }
            });
        } catch (InternalException | SessionException | ConnectException e) {
            Log.e("signingActivity", "voiceEnroll", e);
        }
    }

    private void openVoiceCaptureActivity(int requestCode, String upperText, String recordingToken) {
        Intent intent = new Intent(this, VoiceCaptureActivity.class);
        intent.putExtra(VoiceCaptureActivity.EXTRA_UPPER_TEXT, upperText);
        intent.putExtra(VoiceCaptureActivity.EXTRA_RECORDING_HINT, recordingToken);
        startActivityForResult(intent, requestCode);
    }

    private void showRetryVoiceAuthDialog(String message) {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage(message)
                .setPositiveButton("Retry", (dialog, which) -> {
                    try {
                        createSessionUsingVoice();
                    } catch (ConnectException | InternalException e) {
                        hideSpinner();
                        // errorTextView.setText(e.getMessage());
                    }
                }).setNegativeButton("Cancel", (dialog, which) -> hideSpinner()).setCancelable(false).show();
    }



    private void showRetryVoiceEnrollmentDialog(String message) {
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("Error")
                .setMessage(message + (message.endsWith(".") ? " " : ". ") + " Do you want to try again?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        enrollWithVoice();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false).show();
    }

    private String getErrorMessage(VolleyError error) {
        String json = null;
        String errorMessage = null;
        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            json = new String(response.data);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
                errorMessage = jsonObject.getString("error");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            if (errorMessage != null)
                return errorMessage;
            else
                return "Server problem. Unprocessable response";
        } else if (error instanceof TimeoutError)
            return "Unable to connect to server. Request timed out.";
        else
            return "Unable to connect to server. Please check network settings.";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {


            case voiceEnrollmentRequestcode:
                if (resultCode != RESULT_OK) {
                    return;
                }
                try {
                    progressDialog = Spinner.showSpinner(this);
                    Manager.getInstance().sendProvidedVoiceCapturesToEnroll(VoiceCaptureActivity.audio, new VoiceCaptureEnrollCallback() {
                        @Override
                        public void success(VoiceEnrollModel faceEnrollModel) {
                            hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            Log.d("signingActivity", "success " + faceEnrollModel.getVoiceSamples());
                            voiceTriesAmount++;
                            enrollWithVoice();
                        }

                        @Override
                        public void failure(VolleyError volleyError) {
                            hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            showRetryVoiceEnrollmentDialog("Please retry, reason: " + getErrorMessage(volleyError));
                        }
                    });
                } catch (ConnectException | InternalException e) {
                    hideSpinner();
                    showRetryVoiceEnrollmentDialog("Please retry, reason: " + e.getMessage());
                } catch (SessionException e) {
                    hideSpinner();
                    Log.e("signingActivity", "sendProvidedVoiceCapturesToEnroll", e);
                }
                break;
            case voiceAuthenticationRequestcode:
                if (resultCode != RESULT_OK) {
                    return;
                }
                try {
                    progressDialog = Spinner.showSpinner(this);
                    Manager.getInstance().sendProvidedVoiceCapturesToAuthenticate(VoiceCaptureActivity.audio, new VoiceCapturesAuthenticateCallback() {
                        @Override
                        public void success(VoiceAuthenticateModel voiceAuthenticateModel) {
                            hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            if (voiceAuthenticateModel.getScore() >= 0.5) {
                                if (voiceAuthenticateModel.getLiveliness() >= 0.5) {
                                    // startDemoBankActivity();
                                    new AlertDialog.Builder(HomeActivity.this)
                                            .setTitle("Voice detection succeeded")
                                            .setMessage("Welcome To the app Mr/Mss : " + Globals.extractedData.get(1).getValue())
                                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                                            })
                                            .show();
                                } else {
                                    showRetryVoiceAuthDialog(String.format("Your voice matched to %.0f%%, but it failed the liveliness test", voiceAuthenticateModel.getScore() * 100));
                                }
                            } else {
                                showRetryVoiceAuthDialog("Access denied");
                            }
                        }

                        @Override
                        public void failure(VolleyError volleyError) {
                            hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            // showRetryVoiceAuthDialog("Please retry again, reason: " + getErrorMessage(volleyError));
                        }
                    });
                } catch (ConnectException | InternalException e) {
                    hideSpinner();
                    showRetryVoiceAuthDialog(e.getMessage());
                } catch (SessionException e) {
                    hideSpinner();
                    Log.e("signingActivity", "sendProvidedVoiceCapturesToAuthenticate", e);
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sendCollectedData();
    }

    private void sendCollectedData() {
        try {
            Manager.getInstance().submitCollectedData(new ScoreCallback() {
                @Override
                public void success(ScoreModel scoreModel) {
                    Log.i("DATA SENT", "and collected");
                    ScoreManager.getInstance().scoreChanged(scoreModel, System.currentTimeMillis());
                }
            });
        } catch (InternalException | ConnectException | SessionException e) {
            Log.e("signingActivity", "submitCollectedData", e);
        }
    }



    public static String getDeviceIMEI(Context ctx) {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                return "";
            }
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }

    public void hideSpinner() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}

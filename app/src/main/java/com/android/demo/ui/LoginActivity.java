package com.android.demo.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.aimbrain.sdk.Manager;
import com.aimbrain.sdk.exceptions.InternalException;
import com.aimbrain.sdk.exceptions.SessionException;
import com.aimbrain.sdk.faceCapture.VideoFaceCaptureActivity;
import com.aimbrain.sdk.models.FaceAuthenticateModel;
import com.aimbrain.sdk.models.FaceEnrollModel;
import com.aimbrain.sdk.models.ScoreModel;
import com.aimbrain.sdk.models.SessionModel;
import com.aimbrain.sdk.models.VoiceAuthenticateModel;
import com.aimbrain.sdk.models.VoiceEnrollModel;
import com.aimbrain.sdk.models.VoiceTokenModel;
import com.aimbrain.sdk.models.VoiceTokenType;
import com.aimbrain.sdk.server.AMBNResponseErrorListener;
import com.aimbrain.sdk.server.FaceCapturesAuthenticateCallback;
import com.aimbrain.sdk.server.FaceCapturesEnrollCallback;
import com.aimbrain.sdk.server.ScoreCallback;
import com.aimbrain.sdk.server.SessionCallback;
import com.aimbrain.sdk.server.VoiceCaptureEnrollCallback;
import com.aimbrain.sdk.server.VoiceCapturesAuthenticateCallback;
import com.aimbrain.sdk.server.VoiceTokenCallback;
import com.aimbrain.sdk.voiceCapture.VoiceCaptureActivity;
import com.android.demo.R;
import com.android.demo.score.ScoreManager;
import com.android.demo.utils.Globals;
import com.android.volley.NetworkResponse;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.microblink.result.extract.RecognitionResultEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.sigin)
    Button sigin;
    @BindView(R.id.Register)
    Button Register;
    public static final int MY_BLINKID_REQUEST_CODE = 123;
    private static final int enrollmentRequestcode = 1542;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.sigin, R.id.Register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sigin:

                if(Globals.UserID==0)
                {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("Please Register First")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }else
                {
                    faceAuthType();
                }

                break;
            case R.id.Register:
                // user is not logged in redirect him to Login Activity
                Intent i = new Intent(this, RegisterActivity.class);
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


    // Deserialize to single object.
    public List<RecognitionResultEntry> deserializeFromJson(String jsonString) {
        Gson gson = new Gson();
        RecognitionResultEntry[] myClass = gson.fromJson(jsonString, RecognitionResultEntry[].class);
        List<RecognitionResultEntry> lst = Arrays.asList(myClass);
        lst = new ArrayList<>(lst);
        return lst;
    }
    public void faceEnrollButtonClick() {

        faceAuthType();
       /* final CharSequence[] items = {getString(R.string.auth_type_face), getString(R.string.auth_type_voice) };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_select_auth_type));
        builder.setPositiveButton("Cancel", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    //face selected
                    faceAuthType();
                } else {
                    //voice selected
                    voiceAuthType();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();*/
    }
    private void faceAuthType() {
        try {
            createSessionUsingPhoto();
        } catch (ConnectException | InternalException e) {
            // hideSpinner();
            // errorTextView.setText(e.getMessage());
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Face Auth unavailable")
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    })
                    .show();
        }
    }
    private void voiceAuthType() {
        try {
            createSessionUsingVoice();
        } catch (ConnectException | InternalException e) {
            // hideSpinner();
            //errorTextView.setText(e.getMessage());
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("voice Auth unavailable")
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    })
                    .show();
        }
    }
    private void createSessionUsingPhoto() throws ConnectException, InternalException {
        // progressDialog = Spinner.showSpinner(this);
        ScoreManager.getInstance().clearCache();
        Manager.getInstance().createSession(String.valueOf(getDeviceIMEI(this)+Globals.UserID), getApplicationContext(), session -> {
                    //  hideSpinner();
                    if (!isActive) {
                        return;
                    }
                    Log.i("SESSION", "session id: " + session.getSessionId());
                    Log.i("SESSION", "session face status: " + session.getFaceStatus());
                    Log.i("SESSION", "session behaviour status: " + session.getBehaviourStatus());
                    Log.i("SESSION", "session voice status: " + session.getVoiceStatus());
                    if (session.getFaceStatus() == SessionModel.BUILDING) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Face detection unavailable")
                                .setMessage("Generating template. Please try again in a few seconds")
                                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                })
                                .show();
                    } else if (session.getFaceStatus() == SessionModel.ENROLLED) {
                        authenticateWithPhotos();
                    } else if (session.getFaceStatus() == SessionModel.NOT_ENROLLED) {
                        showEnrollmentDialog();
                    }
                }, new AMBNResponseErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hideSpinner();
                        //errorTextView.setText(getErrorMessage(error));
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Face detection unavailable")
                                .setMessage(error.getMessage())
                                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                })
                                .show();
                    }
                }
        );

    }
    private void authenticateWithPhotos() {
        openFaceImageCaptureActivity(authenticationRequestcode, photoAuthUpperText, photoLowerText, faceAuthenticationHint);
    }
    private void openFaceImageCaptureActivity(int requestCode, String upperText, String lowerText) {
        openFaceImageCaptureActivity(requestCode, upperText, lowerText, null);
    }
    private void openFaceImageCaptureActivity(int requestCode, String upperText, String lowerText, String recordingHint) {
        Intent intent = new Intent(this, VideoFaceCaptureActivity.class);
        intent.putExtra(VideoFaceCaptureActivity.EXTRA_UPPER_TEXT, upperText);
        intent.putExtra(VideoFaceCaptureActivity.EXTRA_LOWER_TEXT, lowerText);
        if (recordingHint != null) {
            //  intent.putExtra(FaceCaptureActivity.RECORDING_HINT, recordingHint);

        }
        intent.putExtra(VideoFaceCaptureActivity.EXTRA_DURATION_MILLIS, 2000);
        intent.putExtra(VideoFaceCaptureActivity.EXTRA_RECORDING_TOKEN_HINT, "");
        startActivityForResult(intent, requestCode);
    }
    private void showEnrollmentDialog() {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage("Please enroll before using Facial authentication")
                .setPositiveButton("Continue", (dialog, which) -> {
                    triesAmount = 0;
                    enrollWithPhotos();
                }).setNegativeButton("Cancel", (dialog, which) -> {
        }).setCancelable(false).show();
    }
    private void enrollWithPhotos() {
        if (triesAmount < 5) {
            openFaceImageCaptureActivity(enrollmentRequestcode, faceEnrollStepsTexts[triesAmount], photoLowerText);
        } else {
            //hideSpinner();
            new AlertDialog.Builder(LoginActivity.this)
                    .setMessage("Video Enrollment finished successfully when you press ok we will open voice Enrollment")
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        voiceAuthType();
                    }).show();
        }
    }
    private void createSessionUsingVoice() throws ConnectException, InternalException {
        // progressDialog = Spinner.showSpinner(this);
        Manager.getInstance().createSession(getDeviceIMEI(this)+String.valueOf(Globals.UserID), getApplicationContext(), session -> {
                    // hideSpinner();
                    if (!isActive) {
                        return;
                    }
                    Log.i("SESSION", "session id: " + session.getSessionId());
                    Log.i("SESSION", "session face status: " + session.getFaceStatus());
                    Log.i("SESSION", "session behaviour status: " + session.getBehaviourStatus());
                    Log.i("SESSION", "session voice status: " + session.getVoiceStatus());
                    if (session.getVoiceStatus() == SessionModel.BUILDING) {
                        new AlertDialog.Builder(LoginActivity.this)
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
                        //hideSpinner();
                        // errorTextView.setText(getErrorMessage(error));
                        new AlertDialog.Builder(LoginActivity.this)
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
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage("Please enroll  before using Voice authentication")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        voiceTriesAmount = 0;
                        enrollWithVoice();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //hideSpinner();
            }
        }).setCancelable(false).show();
    }
    private void enrollWithVoice() {
        if (voiceTriesAmount < 5) {
            VoiceTokenType tokenType = VoiceTokenType.ENROLL1;
            switch (voiceTriesAmount) {
                case 0: tokenType = VoiceTokenType.ENROLL1;
                    break;
                case 1: tokenType = VoiceTokenType.ENROLL2;
                    break;
                case 2: tokenType = VoiceTokenType.ENROLL3;
                    break;
                case 3: tokenType = VoiceTokenType.ENROLL4;
                    break;
                case 4: tokenType = VoiceTokenType.ENROLL5;
                    break;
            }

            Log.d("SignInActivity", "Voice token type " + tokenType.toString());
            try {
                //progressDialog = Spinner.showSpinner(this);
                Manager.getInstance().getVoiceToken(tokenType, new VoiceTokenCallback() {
                    @Override
                    public void success(VoiceTokenModel tokenModel) {
                        // hideSpinner();
                        if (!isActive) {
                            return;
                        }
                        openVoiceCaptureActivity(voiceEnrollmentRequestcode, voiceEnrollStepsTexts[voiceTriesAmount],
                                tokenModel.getToken());
                    }
                });
            } catch (InternalException | SessionException | ConnectException e) {
                Log.e("signingActivity", "voiceEnroll", e);
            }
        }
        else {
            // hideSpinner();
            new AlertDialog.Builder(LoginActivity.this)
                    .setMessage("Voice Enrollment finished successfully we will transfer you to login screen")
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        // user is not logged in redirect him to Login Activity
                        Intent i = new Intent(this, LoginActivity.class);
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
            // progressDialog = Spinner.showSpinner(this);
            Manager.getInstance().getVoiceToken(VoiceTokenType.AUTH, new VoiceTokenCallback() {
                @Override
                public void success(VoiceTokenModel tokenModel) {
                    //   hideSpinner();
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
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            createSessionUsingVoice();
                        } catch (ConnectException | InternalException e) {
                            // hideSpinner();
                            // errorTextView.setText(e.getMessage());
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setCancelable(false).show();
    }
    private void showRetryPhotosEnrollmentDialog(String message) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Error")
                .setMessage(message + (message.endsWith(".") ? " " : ". ") + " Do you want to try again?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        enrollWithPhotos();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false).show();
    }
    private void showRetryVoiceEnrollmentDialog(String message) {
        new AlertDialog.Builder(LoginActivity.this)
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
    private void showRetryPhotosAuthDialog(String message) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            createSessionUsingPhoto();
                        } catch (ConnectException | InternalException e) {
                            // hideSpinner();
                            //  errorTextView.setText(e.getMessage());
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setCancelable(false).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case enrollmentRequestcode:
                if (resultCode != RESULT_OK) {
                    return;
                }

                try {
                    //  progressDialog = Spinner.showSpinner(this);
                    Manager.getInstance().sendProvidedFaceCapturesToEnroll(VideoFaceCaptureActivity.video, new FaceCapturesEnrollCallback() {
                        @Override
                        public void success(FaceEnrollModel faceEnrollModel) {
                            //  hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            triesAmount++;
                            enrollWithPhotos();
                        }

                        @Override
                        public void failure(VolleyError volleyError) {
                            //  hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            showRetryPhotosEnrollmentDialog("Please re-take the picture, reason: " + getErrorMessage(volleyError));
                        }
                    });
                } catch (ConnectException | InternalException e) {
                    //hideSpinner();
                    showRetryPhotosEnrollmentDialog("Please re-take the picture, reason: " + e.getMessage());
                } catch (SessionException e) {
                    // hideSpinner();
                    Log.e("signingActivity", "sendProvidedFaceCapturesToEnroll", e);
                }
                break;
            case authenticationRequestcode:
                if (resultCode != RESULT_OK) {
                    return;
                }
                try {
                    // progressDialog = Spinner.showSpinner(this);
                    Manager.getInstance().sendProvidedFaceCapturesToAuthenticate(VideoFaceCaptureActivity.video, new FaceCapturesAuthenticateCallback() {
                        @Override
                        public void success(FaceAuthenticateModel faceAuthenticateModel) {
                            // hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            if (faceAuthenticateModel.getScore() >= 0.5) {
                                if (faceAuthenticateModel.getLiveliness() >= 0.5) {
                                    // startDemoBankActivity();
                                    new AlertDialog.Builder(LoginActivity.this)
                                            .setTitle("Face detection succeeded")
                                            .setMessage("now we will authenticate voice")
                                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                                voiceAuthType();
                                            })
                                            .show();

                                } else {
                                    showRetryPhotosAuthDialog(String.format("Your face matched to %.0f%%, but it failed the liveliness test", faceAuthenticateModel.getScore() * 100));
                                }
                            } else {
                                showRetryPhotosAuthDialog("Access denied");
                            }
                        }

                        @Override
                        public void failure(VolleyError volleyError) {
                            // hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            showRetryPhotosAuthDialog("Please re-take the picutre, reason: " + getErrorMessage(volleyError));
                        }
                    });
                } catch (ConnectException | InternalException e) {
                    // hideSpinner();
                    showRetryPhotosAuthDialog(e.getMessage());
                } catch (SessionException e) {
                    // hideSpinner();
                    Log.e("signingActivity", "sendProvidedFaceCapturesToAuthenticate", e);
                }
                break;
            case voiceEnrollmentRequestcode:
                if (resultCode != RESULT_OK) {
                    return;
                }
                try {
                    // progressDialog = Spinner.showSpinner(this);
                    Manager.getInstance().sendProvidedVoiceCapturesToEnroll(VoiceCaptureActivity.audio, new VoiceCaptureEnrollCallback() {
                        @Override
                        public void success(VoiceEnrollModel faceEnrollModel) {
                            // hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            Log.d("signingActivity", "success " + faceEnrollModel.getVoiceSamples());
                            voiceTriesAmount++;
                            enrollWithVoice();
                        }

                        @Override
                        public void failure(VolleyError volleyError) {
                            //  hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            showRetryVoiceEnrollmentDialog("Please retry, reason: " + getErrorMessage(volleyError));
                        }
                    });
                } catch (ConnectException | InternalException e) {
                    //hideSpinner();
                    showRetryVoiceEnrollmentDialog("Please retry, reason: " + e.getMessage());
                } catch (SessionException e) {
                    //hideSpinner();
                    Log.e("signingActivity", "sendProvidedVoiceCapturesToEnroll", e);
                }
                break;
            case voiceAuthenticationRequestcode:
                if (resultCode != RESULT_OK) {
                    return;
                }
                try {
                    // progressDialog = Spinner.showSpinner(this);
                    Manager.getInstance().sendProvidedVoiceCapturesToAuthenticate(VoiceCaptureActivity.audio, new VoiceCapturesAuthenticateCallback() {
                        @Override
                        public void success(VoiceAuthenticateModel voiceAuthenticateModel) {
                            //   hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            if (voiceAuthenticateModel.getScore() >= 0.5) {
                                if (voiceAuthenticateModel.getLiveliness() >= 0.5) {
                                    // startDemoBankActivity();
                                    new AlertDialog.Builder(LoginActivity.this)
                                            .setTitle("Voice detection succeeded")
                                            .setMessage("Welcome To the app Mr/Mss : "+ Globals.extractedData.get(1).getValue())
                                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                                voiceAuthType();
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
                            // hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            // showRetryVoiceAuthDialog("Please retry again, reason: " + getErrorMessage(volleyError));
                        }
                    });
                } catch (ConnectException | InternalException e) {
                    // hideSpinner();
                    showRetryVoiceAuthDialog(e.getMessage());
                } catch (SessionException e) {
                    // hideSpinner();
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
}

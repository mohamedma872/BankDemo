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
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
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
import com.aimbrain.sdk.faceCapture.FaceCaptureActivity;
import com.aimbrain.sdk.faceCapture.VideoFaceCaptureActivity;
import com.aimbrain.sdk.models.FaceAuthenticateModel;
import com.aimbrain.sdk.models.FaceCompareModel;
import com.aimbrain.sdk.models.FaceEnrollModel;
import com.aimbrain.sdk.models.FaceTokenModel;
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
import com.aimbrain.sdk.server.FaceTokenCallback;
import com.aimbrain.sdk.server.ScoreCallback;
import com.aimbrain.sdk.server.SessionCallback;
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
import com.microblink.entities.recognizers.blinkid.egypt.EgyptIdFrontRecognizer;
import com.microblink.entities.recognizers.blinkid.unitedArabEmirates.UnitedArabEmiratesIdBackRecognizer;
import com.microblink.entities.recognizers.blinkid.unitedArabEmirates.UnitedArabEmiratesIdFrontRecognizer;
import com.microblink.result.ResultActivity;
import com.microblink.result.extract.RecognitionResultEntry;
import com.microblink.uisettings.ActivityRunner;
import com.microblink.uisettings.BaseScanUISettings;
import com.microblink.uisettings.DocumentUISettings;
import com.microblink.uisettings.UISettings;
import com.microblink.uisettings.options.BeepSoundUIOptions;
import com.microblink.uisettings.options.HelpIntentUIOptions;
import com.microblink.uisettings.options.OcrResultDisplayMode;
import com.microblink.uisettings.options.OcrResultDisplayUIOptions;
import com.microblink.util.ImageSettings;
import com.microblink.util.RecognizerCompatibility;
import com.microblink.util.RecognizerCompatibilityStatus;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.aimbrain.sdk.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.cancel)
    Button cancel;
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

    /**
     * Reference to bundle is kept, it is used later for loading results
     * from intent
     */
    private int triesAmount;
    private int voiceTriesAmount;
    private boolean isActive;
    public static byte[] video;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        registerReceiver(myReceiver, new IntentFilter("FBR-IMAGE"));
        //aimbrain
        Logger.LEVEL = Logger.VERBOSE;
        // check if BlinkID is supported on the device
        RecognizerCompatibilityStatus supportStatus = RecognizerCompatibility.getRecognizerCompatibilityStatus(this);
        if (supportStatus != RecognizerCompatibilityStatus.RECOGNIZER_SUPPORTED) {
            Toast.makeText(this, "BlinkID is not supported! Reason: " + supportStatus.name(), Toast.LENGTH_LONG).show();
        } else {

        }


    }

    private void scanAction(@NonNull UISettings activitySettings, @Nullable Intent helpIntent) {
        setupActivitySettings(activitySettings, helpIntent);
        ActivityRunner.startActivityForResult(this, MY_BLINKID_REQUEST_CODE, activitySettings);
    }

    /**
     * Starts scan activity. Activity that will be used is determined by the passed activity settings.
     * UI options are configured inside this method.
     *
     * @param activitySettings activity settings that will be used for scanning, only recognizers are
     *                         important, UI options will be configured inside this method.
     */
    private void scanAction(@NonNull UISettings activitySettings) {
        scanAction(activitySettings, null);
    }

    private void setupActivitySettings(@NonNull UISettings settings, @Nullable Intent helpIntent) {
        if (settings instanceof BeepSoundUIOptions) {
            // optionally, if you want the beep sound to be played after a scan
            // add a sound resource id
            ((BeepSoundUIOptions) settings).setBeepSoundResourceID(R.raw.beep);
        }
        if (helpIntent != null && settings instanceof HelpIntentUIOptions) {
            // if we have help intent, we can pass it to scan activity so it can invoke
            // it if user taps the help button. If we do not set the help intent,
            // scan activity will hide the help button.
            ((HelpIntentUIOptions) settings).setHelpIntent(helpIntent);
        }
        if (settings instanceof OcrResultDisplayUIOptions) {
            // If you want, you can disable drawing of OCR results on scan activity. Drawing OCR results can be visually
            // appealing and might entertain the user while waiting for scan to complete, but might introduce a small
            // performance penalty.
            // ((ShowOcrResultUIOptions) settings).setShowOcrResult(false);

            // Enable showing of OCR results as animated dots. This does not have effect if non-OCR recognizer like
            // barcode recognizer is active.
            ((OcrResultDisplayUIOptions) settings).setOcrResultDisplayMode(OcrResultDisplayMode.ANIMATED_DOTS);
        }
        if (settings instanceof BaseScanUISettings) {
            // If you want you can have scan activity display the focus rectangle whenever camera
            // attempts to focus, similarly to various camera app's touch to focus effect.
            // By default this is off, and you can turn this on by setting EXTRAS_SHOW_FOCUS_RECTANGLE
            // extra to true.
            // ((BaseScanUISettings) settings).setShowingFocusRectangle(true);

            // If you want, you can enable the pinch to zoom feature of scan activity.
            // By enabling this you allow the user to use the pinch gesture to zoom the camera.
            // By default this is off
            ((BaseScanUISettings) settings).setPinchToZoomAllowed(true);
        }
    }

    private void startResultActivity(Intent data) {
        // set intent's component to ResultActivity and pass its contents
        // to ResultActivity. ResultActivity will show how to extract
        // data from result.
        data.setComponent(new ComponentName(getApplicationContext(), ResultActivity.class));
        startActivity(data);
    }

    @OnClick({R.id.scan, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scan:
                UnitedArabEmiratesIdFrontRecognizer uaeFront = new UnitedArabEmiratesIdFrontRecognizer();
                ImageSettings.enableAllImages(uaeFront);
                UnitedArabEmiratesIdBackRecognizer uaeBack = new UnitedArabEmiratesIdBackRecognizer();
                ImageSettings.enableAllImages(uaeBack);
                scanAction(new DocumentUISettings(prepareRecognizerBundle(uaeFront, uaeBack)));
/*
                EgyptIdFrontRecognizer egyptFront = new EgyptIdFrontRecognizer();
                ImageSettings.enableAllImages(egyptFront);
                scanAction(new DocumentUISettings(prepareRecognizerBundle(egyptFront)));*/
                break;
            case R.id.cancel:
                Intent i = new Intent(this, LoginActivity.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
                startActivity(i);
                break;
        }
    }

    private RecognizerBundle prepareRecognizerBundle(@NonNull Recognizer<?, ?>... recognizers) {
        return new RecognizerBundle(recognizers);
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
        unregisterReceiver(myReceiver);
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
        progressDialog = Spinner.showSpinner(this);
        ScoreManager.getInstance().clearCache();
        try {
            Manager.getInstance().createSession(getDeviceIMEI(this) + String.valueOf(Globals.UserID), getApplicationContext(), session -> {
                        hideSpinner();
                        if (!isActive) {
                            return;
                        }
                        Log.i("SESSION", "session id: " + session.getSessionId());
                        Log.i("SESSION", "session face status: " + session.getFaceStatus());
                        Log.i("SESSION", "session behaviour status: " + session.getBehaviourStatus());
                        Log.i("SESSION", "session voice status: " + session.getVoiceStatus());
                        if (session.getFaceStatus() == SessionModel.BUILDING) {
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Face detection unavailable")
                                    .setMessage("Generating template. Please try again in a few seconds")
                                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                    })
                                    .show();
                        } else if (session.getFaceStatus() == SessionModel.ENROLLED) {

                        } else if (session.getFaceStatus() == SessionModel.NOT_ENROLLED) {
                            SendCaptureImage();
                        }
                    }, new AMBNResponseErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideSpinner();
                            //errorTextView.setText(getErrorMessage(error));
                            new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("Face detection unavailable")
                                    .setMessage(error.getMessage())
                                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                    })
                                    .show();
                        }
                    }
            );
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (ConnectException e) {
            e.printStackTrace();
        }

    }

    private void SendCaptureImage() {
        progressDialog = Spinner.showSpinner(this);
        List<Bitmap> lst = new ArrayList<>();
        lst.add(DataList.get(3).getImageValue());
        try {
            Manager.getInstance().sendProvidedFaceCapturesToEnroll(lst, new FaceCapturesEnrollCallback() {
                @Override
                public void success(FaceEnrollModel faceEnrollModel) {
                    hideSpinner();
                    faceAuthType();
                }

                @Override
                public void failure(VolleyError volleyError) {
                    hideSpinner();
                    showRetryPhotosEnrollmentDialog("Please re-take the picture, reason: " + getErrorMessage(volleyError));
                }
            });
        } catch (InternalException e) {
            hideSpinner();
            e.printStackTrace();
        } catch (ConnectException e) {
            hideSpinner();
            e.printStackTrace();
        } catch (SessionException e) {
            hideSpinner();
            e.printStackTrace();
        }
    }

    private void faceAuthType() {
        try {
            createSessionUsingPhoto();
        } catch (ConnectException | InternalException e) {
            hideSpinner();
            // errorTextView.setText(e.getMessage());
            new AlertDialog.Builder(RegisterActivity.this)
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
            hideSpinner();
            //errorTextView.setText(e.getMessage());
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("voice Auth unavailable")
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    })
                    .show();
        }
    }

    private void createSessionUsingPhoto() throws ConnectException, InternalException {
        progressDialog = Spinner.showSpinner(this);
        ScoreManager.getInstance().clearCache();
        Manager.getInstance().createSession(getDeviceIMEI(this) + String.valueOf(Globals.UserID), getApplicationContext(), session -> {
                    hideSpinner();
                    if (!isActive) {
                        return;
                    }
                    Log.i("SESSION", "session id: " + session.getSessionId());
                    Log.i("SESSION", "session face status: " + session.getFaceStatus());
                    Log.i("SESSION", "session behaviour status: " + session.getBehaviourStatus());
                    Log.i("SESSION", "session voice status: " + session.getVoiceStatus());
                    if (session.getFaceStatus() == SessionModel.BUILDING) {
                        new AlertDialog.Builder(RegisterActivity.this)
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
                        hideSpinner();
                        //errorTextView.setText(getErrorMessage(error));
                        new AlertDialog.Builder(RegisterActivity.this)
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

        try {
            progressDialog = Spinner.showSpinner(this);
            Manager.getInstance().getFaceToken(FaceTokenType.AUTH, tokenModel -> {
                hideSpinner();
                String token = tokenModel.getToken();
                // use token, e.g. present video recording activity with token
                Intent intent = new Intent(RegisterActivity.this, VideoFaceCaptureActivity.class);
                intent.putExtra(VideoFaceCaptureActivity.EXTRA_UPPER_TEXT, upperText);
                intent.putExtra(VideoFaceCaptureActivity.EXTRA_LOWER_TEXT, lowerText);
                intent.putExtra(VideoFaceCaptureActivity.EXTRA_DURATION_MILLIS, 2000);
                intent.putExtra(VideoFaceCaptureActivity.EXTRA_RECORDING_TOKEN_HINT, token);
                startActivityForResult(intent, requestCode);

            });
        } catch (InternalException e) {
            e.printStackTrace();
            hideSpinner();
        } catch (SessionException e) {
            e.printStackTrace();
            hideSpinner();
        } catch (ConnectException e) {
            e.printStackTrace();
            hideSpinner();
        }


    }

    private void showEnrollmentDialog() {
        new AlertDialog.Builder(RegisterActivity.this)
                .setMessage("Please enroll before using Facial authentication")
                .setPositiveButton("Continue", (dialog, which) -> {
                    triesAmount = 0;
                    enrollWithPhotos();
                }).setNegativeButton("Cancel", (dialog, which) -> {
            hideSpinner();
        }).setCancelable(false).show();
    }

    private void enrollWithPhotos() {
        if (triesAmount < 1) {
            openFaceImageCaptureActivity(enrollmentRequestcode, faceEnrollStepsTexts[triesAmount], photoLowerText);
        } else {
            hideSpinner();
            this.video = VideoFaceCaptureActivity.video;


            new AlertDialog.Builder(RegisterActivity.this)
                    .setMessage("Video Enrollment finished successfully when you press ok we will open voice Enrollment")
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                      /*  try {
                            //should compare cropped image first with vedio
                            List<Bitmap> facialImages1 = new ArrayList<>();
                            facialImages1.add(DataList.get(3).getImageValue());
                            byte[] decodeResponse = Base64.decode(this.video, Base64.DEFAULT | Base64.NO_WRAP);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeResponse, 0, decodeResponse.length);
                            List<Bitmap> facialImages2 = new ArrayList<>();
                            facialImages2.add(bitmap);
                            Manager.getInstance().compareFacesPhotos(facialImages1, facialImages2, new FaceCompareCallback() {
                                @Override
                                public void success(FaceCompareModel faceCompareModel) {
                                    double score = faceCompareModel.getFirstLiveliness();

                                }

                                @Override
                                public void failure(VolleyError volleyError) {

                                }
                            });
                        } catch (InternalException e) {
                            e.printStackTrace();
                        } catch (ConnectException e) {
                            e.printStackTrace();
                        } catch (SessionException e) {
                            e.printStackTrace();
                        }*/

                        voiceAuthType();
                    }).show();
        }
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
                        new AlertDialog.Builder(RegisterActivity.this)
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
                        new AlertDialog.Builder(RegisterActivity.this)
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
        new AlertDialog.Builder(RegisterActivity.this)
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
            new AlertDialog.Builder(RegisterActivity.this)
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
        new AlertDialog.Builder(RegisterActivity.this)
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

    private void showRetryPhotosEnrollmentDialog(String message) {
        new AlertDialog.Builder(RegisterActivity.this)
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
        new AlertDialog.Builder(RegisterActivity.this)
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
        new AlertDialog.Builder(RegisterActivity.this)
                .setMessage(message)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            createSessionUsingPhoto();
                        } catch (ConnectException | InternalException e) {
                            hideSpinner();
                            //  errorTextView.setText(e.getMessage());
                        }
                    }
                }).setNegativeButton("Cancel", (dialog, which) -> hideSpinner()).setCancelable(false).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MY_BLINKID_REQUEST_CODE:
                if (requestCode == MY_BLINKID_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
                    startResultActivity(data);
                } else {
                    // if BlinkID activity did not return result, user has probably
                    // pressed Back button and cancelled scanning
                    Toast.makeText(this, "Scan cancelled!", Toast.LENGTH_SHORT).show();
                }
                break;
            case enrollmentRequestcode:
                if (resultCode != RESULT_OK) {
                    return;
                }

                try {
                    //  progressDialog = Spinner.showSpinner(this);
                    Manager.getInstance().sendProvidedFaceCapturesToEnroll(VideoFaceCaptureActivity.video, new FaceCapturesEnrollCallback() {
                        @Override
                        public void success(FaceEnrollModel faceEnrollModel) {
                            hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            triesAmount++;
                            enrollWithPhotos();
                        }

                        @Override
                        public void failure(VolleyError volleyError) {
                            hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            showRetryPhotosEnrollmentDialog("Please re-take the picture, reason: " + getErrorMessage(volleyError));
                        }
                    });
                } catch (ConnectException | InternalException e) {
                    hideSpinner();
                    showRetryPhotosEnrollmentDialog("Please re-take the picture, reason: " + e.getMessage());
                } catch (SessionException e) {
                    hideSpinner();
                    Log.e("signingActivity", "sendProvidedFaceCapturesToEnroll", e);
                }
                break;
            case authenticationRequestcode:
                if (resultCode != RESULT_OK) {
                    return;
                }
                try {
                    progressDialog = Spinner.showSpinner(this);
                    Manager.getInstance().sendProvidedFaceCapturesToAuthenticate(VideoFaceCaptureActivity.video, new FaceCapturesAuthenticateCallback() {
                        @Override
                        public void success(FaceAuthenticateModel faceAuthenticateModel) {
                            hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            if (faceAuthenticateModel.getScore() >= 0.3) {
                                if (faceAuthenticateModel.getLiveliness() >= 0.3) {
                                    // startDemoBankActivity();
                                    new AlertDialog.Builder(RegisterActivity.this)
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
                            hideSpinner();
                            if (!isActive) {
                                return;
                            }
                            showRetryPhotosAuthDialog("Please re-take the picutre, reason: " + getErrorMessage(volleyError));
                        }
                    });
                } catch (ConnectException | InternalException e) {
                    hideSpinner();
                    showRetryPhotosAuthDialog(e.getMessage());
                } catch (SessionException e) {
                    hideSpinner();
                    Log.e("signingActivity", "sendProvidedFaceCapturesToAuthenticate", e);
                }
                break;
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
                                    new AlertDialog.Builder(RegisterActivity.this)
                                            .setTitle("Voice detection succeeded")
                                            .setMessage("Welcome To the app Mr/Mss : " + Globals.extractedData.get(1).getValue())
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

    List<RecognitionResultEntry> DataList;
    public BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getStringExtra("action");
            DataList = deserializeFromJson(action);
            Globals.extractedData = DataList;
            Globals.UserID = Globals.UserID + 1;
            faceEnrollButtonClick();
        }
    };

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

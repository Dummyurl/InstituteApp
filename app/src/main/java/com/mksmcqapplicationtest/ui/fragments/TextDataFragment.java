package com.mksmcqapplicationtest.ui.fragments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.util.AppUtility;

import java.io.File;
import java.io.IOException;


public class TextDataFragment extends Fragment implements View.OnTouchListener, TextToSpeech.OnInitListener, MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl {
    private static final String ARG_SECTION_NUMBER = "section_number";
    View view;
    TextView txtTextData;
    Button btnSpeechOut;
    final static float STEP = 200;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    TextToSpeech textToSpeech;
    int mStatus = 0;
    MediaPlayer mediaPlayer;
    boolean mProceed = false;
    String FILENAME = "/mks_tts.wav";
    MediaController medioController;
    SeekBar seekSpeed;
    private double speed = 1.0;

    public TextDataFragment() {
    }

    public static TextDataFragment newInstance(int sectionNumber) {
        TextDataFragment fragment = new TextDataFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_text_data, container, false);
        btnSpeechOut = (Button) view.findViewById(R.id.btnSpeechOut);
        txtTextData = (TextView) view.findViewById(R.id.txtTextData);
        txtTextData.setText(AppUtility.DataText);
//        mediaPlayer = new MediaPlayer();
//        medioController = new MediaController(getContext());
//        mediaPlayer.setOnPreparedListener(this);
//
//
//        txtTextData.setOnTouchListener(this);
//        textToSpeech = new TextToSpeech(getContext(), this);
//        btnSpeechOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                    playMediaPlayer(1);
//                    btnSpeechOut.setText("Speek");
//
//                }
//
//                HashMap<String, String> myHashRender = new HashMap();
//                String id = "mks";
//                myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, id);
//                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + FILENAME;
//
//                if (!mProceed) {
//                    int status = textToSpeech.synthesizeToFile(txtTextData.getText().toString(), myHashRender, fileName);
//                } else {
//                    playMediaPlayer(0);
//                }
//            }
//        });
//
//
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                btnSpeechOut.setText("Speek");
//            }
//        });

        return view;
    }


    int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) { //TODO Tejal Zoom In on Text view
        if (event.getPointerCount() == 2) {
            int action = event.getAction();
            int pureaction = action & MotionEvent.ACTION_MASK;
            if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                mBaseDist = getDistance(event);
                mBaseRatio = mRatio;
            } else {
                float delta = (getDistance(event) - mBaseDist) / STEP;
                float multi = (float) Math.pow(2, delta);
                mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                txtTextData.setTextSize(mRatio + 13);
            }
        }
        medioController.show();
//        return false;
        return true;
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        medioController.show();
//        return false;
//    }

    private void playMediaPlayer(int i) {
        if (i == 0) {
            mediaPlayer.start();
        }
        if (i == 1) {
            mediaPlayer.pause();

        }
    }


    @Override
    public void onDestroy() {

        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            mediaPlayer.stop();
            mediaPlayer.release();
            String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + FILENAME;
            File file = new File(filename);
            boolean deleted = file.delete();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        mStatus = status;
        setTts(textToSpeech);

    }

    private void setTts(TextToSpeech tts) {
        this.textToSpeech = tts;
        if (Build.VERSION.SDK_INT >= 15) {
            this.textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }

                @Override
                public void onDone(String utteranceId) {
                    mProceed = true;
                    initializeMediaPlayer();
                    playMediaPlayer(0);
                }

                @Override
                public void onError(String utteranceId) {

                }
            });
        } else {
            this.textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }

                @Override
                public void onDone(String utteranceId) {
                    mProceed = true;
                    initializeMediaPlayer();
                    playMediaPlayer(0);
                }

                @Override
                public void onError(String utteranceId) {

                }
            });
        }
    }

    private void initializeMediaPlayer() {
        String filename = Environment.getExternalStorageDirectory().getAbsolutePath() + FILENAME;
        Uri uri = Uri.parse("file://" + filename);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getContext(), uri);
            mediaPlayer.prepare();
            medioController.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        medioController.setMediaPlayer(this);
        medioController.setAnchorView(view.findViewById(R.id.medialController));
        medioController.setEnabled(true);
        medioController.show();
    }


}


package com.mksmcqapplicationtest.MediaPlayer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ListActivity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mksmcqapplicationtest.ProgressDialog.ProgressDialogShow;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.Utilities;
import com.mksmcqapplicationtest.beans.AdvertiesClass;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.util.AppUtility;

import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity {
    private static final int UPDATE_FREQUENCY = 500;
    private static final int STEP_VALUE = 5000;
    Context context;
    Boolean active = false;
    private SeekBar seekBar = null;
    private MediaPlayer player = null;
    private ImageButton prev = null;
    private ImageButton play = null;
    private ImageButton next = null;
    TextView  SongDescription, SongFormula;
    private Utilities utils;
    private boolean isStarted = true;
    private String currentFile = "";
    private boolean isMovingSeekBar = false;
    Bundle bundle;
    String AudioPath, AudioName, AudioFormula, AudioDescription;
    TextView songCurrentDurationLabel;
    TextView songTotalDurationLabel;
    private final Handler handler = new Handler();
    Runnable runnable;
    ProgressDialogShow progressDialogClickClass;
    //    ProgressDialog progressDialog;
    private final Runnable updatePositinRunnable = new Runnable() {
        @Override
        public void run() {
            updatePosition();
        }
    };

//    public void showNotification() {
//        new MyNotification(this);
////        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_music_player);
        active = true;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        prev = (ImageButton) findViewById(R.id.previous);
        play = (ImageButton) findViewById(R.id.play);
        next = (ImageButton) findViewById(R.id.next);
        SongDescription = (TextView) findViewById(R.id.txtExample);
        SongFormula = (TextView) findViewById(R.id.txtFormula);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);


        this.context = getApplicationContext();
        utils = new Utilities();

        player = new MediaPlayer();
        player.setOnCompletionListener(onCompletion);
        player.setOnErrorListener(onError);

        seekBar.setOnSeekBarChangeListener(seekBarChanged);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Buffering...");
//        progressDialog.show();
//        progressDialog.setCancelable(false);
        progressDialogClickClass = new ProgressDialogShow
                (this, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Buffering...");
        progressDialogClickClass.show();

        bundle = getIntent().getExtras();
        if (bundle != null) {
            AudioPath = bundle.getString("AudioPath");
            AudioName = bundle.getString("AudioName");
            AudioFormula = bundle.getString("AudioFormula");
            AudioDescription = bundle.getString("AudioDescription");
        }
        currentFile = AppUtility.AudioFilesURL + AudioPath;
//        startPlay(currentFile);
        prev.setOnClickListener(OnButtonClick);
        play.setOnClickListener(OnButtonClick);
        next.setOnClickListener(OnButtonClick);

        if (AppUtility.IsTeacher.equals("G")) {
            if (AppUtility.IsAdvertiesVisibleForGuest) {
                AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                advertiesClass.ShowAdver();
                AdvertiesClass advertiesClass1 = new AdvertiesClass(this, this, R.id.adView1);
                advertiesClass1.ShowAdver();
            } else if (AppUtility.IsAdvertiesVisible) {
                AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                advertiesClass.ShowAdver();
                AdvertiesClass advertiesClass1 = new AdvertiesClass(this, this, R.id.adView1);
                advertiesClass1.ShowAdver();
            }
        } else {
            if (AppUtility.IsAdvertiesVisible) {
                AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                advertiesClass.ShowAdver();
                AdvertiesClass advertiesClass1 = new AdvertiesClass(this, this, R.id.adView1);
                advertiesClass1.ShowAdver();
            }
        }

        if (AudioFormula.equals("")) {
            SongFormula.setVisibility(View.GONE);
        } else {
//            String CurrentString = AudioFormula;
//            String[] separated = CurrentString.split(";");
//            StringBuilder stringBuilder = new StringBuilder(100);
//            for (int i = 0; i < separated.length; i++) {
//                if (i == separated.length - 1) {
//                    String sepratedFormula = separated[i];
//                    stringBuilder.append(sepratedFormula);
//                } else {
//                    String sepratedFormula = separated[i] + "\n";
//                    stringBuilder.append(sepratedFormula);
//                }
//
//            }
//            String finalFormula = stringBuilder.toString();
            SongFormula.setText(Html.fromHtml(AudioFormula));
        }

        if (AudioDescription.equals("")) {
            SongDescription.setVisibility(View.GONE);
        } else {
            SongDescription.setText(Html.fromHtml(AudioDescription));
        }
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" " + AudioName);
        toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startPlay(String file) {
        //This is used ti intent with media player
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.parse(file), "audio/mp3");
//        startActivity(intent);
        Log.i("Selected: ", file);
        seekBar.setProgress(0);
        player.setScreenOnWhilePlaying(true);
        player.stop();
        player.reset();
        try {
            player.setLooping(true);
            player.setDataSource(file);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    playCycle();
                    if (mediaPlayer.isPlaying()) {
//                        if (progressDialog.isShowing()) {
//                            progressDialog.cancel();
                        progressDialogClickClass.dismiss();
                        seekBar.setMax(mediaPlayer.getDuration());
                        play.setImageResource(R.drawable.play);
                        updatePosition();
                        isStarted = true;
//                        }
                    }
                }
            });
            player.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlay() {
        player.stop();
        player.reset();
        play.setImageResource(R.drawable.pause);
        handler.removeCallbacks(updatePositinRunnable);
        seekBar.setProgress(0);
        isStarted = false;
//        if (progressDialog.isShowing()) {
//            progressDialog.cancel();
//        }
        progressDialogClickClass.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.pause();
        active = false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (active) {
            startPlay(currentFile);
        } else {
            play.setImageResource(R.drawable.play);
            player.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = true;
        handler.removeCallbacks(updatePositinRunnable);
        player.stop();
        player.reset();
        player.release();
        player = null;
//        if (progressDialog.isShowing()) {
//            progressDialog.cancel();
//        }
        progressDialogClickClass.dismiss();
    }


    private void updatePosition() {

        handler.removeCallbacks(updatePositinRunnable);
        seekBar.setProgress(player.getCurrentPosition());
        handler.postDelayed(updatePositinRunnable, UPDATE_FREQUENCY);
        try {
            long totalDuration = player.getDuration();
            long currentDuration = player.getCurrentPosition();
            songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));
//            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
//            seekBar.setProgress(progress);
//            isMovingSeekBar=true;
        } catch (Exception ex) {
            Log.d("Error in time", "" + ex);
        }
    }

    private View.OnClickListener OnButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.play:
                    if (player.isPlaying()) {
                        handler.removeCallbacks(updatePositinRunnable);
                        player.pause();
                        play.setImageResource(R.drawable.pause);
                    } else {
                        if (isStarted) {
                            player.start();
                            play.setImageResource(R.drawable.play);
                            updatePosition();
                        } else {
                            startPlay(currentFile);
                        }
                    }
                    break;


                case R.id.next:

                    int seektonext = player.getCurrentPosition() + STEP_VALUE;
                    if (seektonext > player.getDuration())
                        seektonext = player.getDuration();
                    player.pause();
                    player.seekTo(seektonext);
                    player.start();
                    if (player.isPlaying()) {
                        play.setImageResource(R.drawable.play);
                    }
                    updatePosition();
                    break;


                case R.id.previous:
                    int seekto = player.getCurrentPosition() - STEP_VALUE;
                    if (seekto < 0)
                        seekto = 0;
                    player.pause();
                    player.seekTo(seekto);
                    player.start();
                    if (player.isPlaying()) {
                        play.setImageResource(R.drawable.play);
                    }
                    updatePosition();
                    break;

            }
        }
    };
    private MediaPlayer.OnCompletionListener onCompletion = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            stopPlay();
        }
    };
    private MediaPlayer.OnErrorListener onError = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            openErrorDialog();
            return false;
        }
    };

    private void openErrorDialog() {
        final Dialog dialog = new Dialog(MusicPlayerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
        txtTitle.setText("Message!!");
        TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
        txtMessage.setText("Something went wrong please try again later.");
        final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
        dialogButton.setText("Ok");
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(MusicPlayerActivity.this, dialogButton);
                bounce_button_class1.BounceMethod();
                dialog.dismiss();
                MusicPlayerActivity.this.finish();

            }
        });
        Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
        CancledialogButton.setText("No");
        CancledialogButton.setVisibility(View.GONE);
        dialog.show();
    }

    private SeekBar.OnSeekBarChangeListener seekBarChanged =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        seekBar.setProgress(player.getCurrentPosition());
                    if (player != null && fromUser) {
                        player.seekTo(progress);
                    }
                    Log.i("OnSeekBarChangeListener", "OnProgressChanged");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
//                    isMovingSeekBar = true;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
//                    player.seekTo(seekBar.getProgress());
                }
            };

    public void playCycle() {

        seekBar.setProgress(player.getCurrentPosition());
        if (player.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {

                    playCycle();
                }
            };
            handler.postDelayed(updatePositinRunnable, 1000);
        }
    }
}

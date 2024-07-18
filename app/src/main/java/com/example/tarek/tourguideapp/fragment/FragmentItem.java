package com.example.tarek.tourguideapp.fragment;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tarek.tourguideapp.R;
import com.example.tarek.tourguideapp.locations.Location;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


// TODO: create a separated class for MediaPlayer

public class FragmentItem extends Fragment {

    private final String LOCATION = "LOCATION";
    private final String NUMBER_OF_PAGE = "NUMBER_OF_PAGE";
    private final String PROGRESS = "PROGRESS";
    private final int ZERO = 0;
    private final int HUNDRED = 100;
    private final Handler handler = new Handler();
    @BindView(R.id.category_item_image)
    ImageView locationImage;
    @BindView(R.id.location_name)
    TextView locationName;
    @BindView(R.id.location_address)
    TextView locationAddress;
    @BindView(R.id.category_item_text_description)
    TextView locationDescription;
    @BindView(R.id.id_seekbar)
    SeekBar seekBar;
    @BindView(R.id.elapsed_time)
    TextView elapsedTimeTV;
    @BindView(R.id.full_time)
    TextView fullTimeTV;
    @BindView(R.id.play_sound_icon)
    ImageView playSoundIcon;
    private final Runnable updateSongTime = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                setSeekBarPosition(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, HUNDRED);
            }
        }
    };

    private String numberOfPage;
    private Location currentLocation;
    private MediaPlayer mediaPlayer;
    private double currentPosition;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;


    public FragmentItem() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.category_item, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        //MediaPlayer-JNI: release
        releaseMediaPlayer(); // and it will be set again if play icon was clicked (if screen rotated)
    }

    @Override
    public void onStop() {
        super.onStop();
        // even if the sound was playing it must stop and release media player if the user moved to next page !
        releaseMediaPlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putParcelable(LOCATION, currentLocation);
        savedInstanceState.putString(NUMBER_OF_PAGE, numberOfPage);
        savedInstanceState.putDouble(PROGRESS, currentPosition); // to save current position of seekbar
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            currentLocation = savedInstanceState.getParcelable(LOCATION);
            numberOfPage = savedInstanceState.getString(NUMBER_OF_PAGE);
            currentPosition = savedInstanceState.getDouble(PROGRESS); // it must use this value to set progress on setSeekBar();
        }

        setAudioManager();
        setMediaPlayer();
        setSeekBar();
        locationImage.setImageResource(currentLocation.getImageResourceId());
        String title = numberOfPage + currentLocation.getName();
        locationName.setText(title);
        locationAddress.setText(currentLocation.getAddress());
        locationDescription.setText(currentLocation.getDescription());

    }

    public void setLocation(Location location, String numberOfPage) {
        this.currentLocation = location;
        this.numberOfPage = numberOfPage;
    }

    @OnClick(R.id.play_sound_icon)
    public void OnClickPlayIcon() {
        String tag = playSoundIcon.getTag().toString();
        if (tag.equals(getString(R.string.tag1_play_sound_icon))) {
            changePlayIconToPause();
            if (mediaPlayer == null) {
                // in case sound  finished then MediaPlayer released and = null : set it again
                setMediaPlayer();
            }
            if (currentPosition > ZERO) { // if the screen rotated while playing sound resume from stopped point
                setSeekBarPosition((int) currentPosition);
                seekMediaPlayerTOPosition((int) currentPosition);
            }
            playSoundIfAudioFocusRequestGranted();
        } else {
            changePlayIconToPlay();
            if (mediaPlayer != null)
                pauseSound(); // in case sound finished then MediaPlayer released and = null
        }
    }

    /**
     * to change play Icon tag to "click to pause" and image to pause
     */
    private void changePlayIconToPause() {
        playSoundIcon.setTag(getString(R.string.tag2_play_sound_icon));
        playSoundIcon.setImageResource(R.drawable.ic_pause_circle_outline_black_48dp);
    }

    /**
     * to change play Icon tag to "click to play" and image to play
     */
    private void changePlayIconToPlay() {
        playSoundIcon.setTag(getString(R.string.tag1_play_sound_icon));
        playSoundIcon.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
    }

    public void setMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getContext(), currentLocation.getSoundResourceId());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    changePlayIconToPlay();
                    setSeekBarPosition(ZERO);
                    releaseMediaPlayer();
                }
            });
        }
    }

    public void setSeekBar() {
        double finalTime = mediaPlayer.getDuration();
        elapsedTimeTV.setText(getTextTime(currentPosition));
        fullTimeTV.setText(getTextTime(finalTime));
        seekBar.setMax((int) finalTime);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    setSeekBarPosition(progress);
                    seekMediaPlayerTOPosition(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    /**
     * to handle current position of seekbar immediately using Runnable updateSongTime
     */
    public void updateSongTimeImmediately() {
        handler.postDelayed(updateSongTime, HUNDRED);
    }

    public void setSeekBarPosition(int position) {
        currentPosition = position;
        seekBar.setProgress((int) currentPosition);
        elapsedTimeTV.setText(getTextTime(currentPosition));
    }

    /**
     * to seek media player to a certain position
     * used if screen rotated while playing sound to resume from stopped point
     * or if the user clicked on seek bar and select position to start
     *
     * @param position
     */
    public void seekMediaPlayerTOPosition(int position) {
        // if the user change the position of the seekbar before clicking on play button which sets mediaplayer
        if (mediaPlayer == null) setMediaPlayer();
        mediaPlayer.seekTo(position);
    }

    public void playSound() {
        mediaPlayer.start();
        updateSongTimeImmediately();
    }

    public void pauseSound() {
        mediaPlayer.pause();
    }

    public void stopSound() {
        mediaPlayer.stop();
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) stopSound();
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    /**
     * to return text shown in elapsed / remain time
     *
     * @param time
     * @return
     */
    public String getTextTime(double time) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes((long) time),
                TimeUnit.MILLISECONDS.toSeconds((long) time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) time)));
    }

    public void setAudioManager() {
        audioManager = (AudioManager) getContext().getSystemService(getContext().AUDIO_SERVICE);

        onAudioFocusChangeListener =
                new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int focusChange) {
                        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                                focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                            pauseSound();// we loss the audio focus temporarily so pause mediaplayer
                        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                            releaseMediaPlayer(); // we loss the audio focus permanently so stop and release mediaplayer
                        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                            playSound(); // we have the audio focus so resume playing
                        }
                    }
                };
    }

    /**
     * to play sound if AUDIOFOCUS_REQUEST_GRANTED
     */
    public void playSoundIfAudioFocusRequestGranted() {
        int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT); //AUDIOFOCUS_GAIN  for unknown duration
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            playSound();
        }
    }

    /**
     * to release media player if the fragment isn't visible to the user (he moved to next page)
     *
     * @param isVisibleToUser boolean to get true or false
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            // If we are becoming invisible, then...
            if (!isVisibleToUser) {
                releaseMediaPlayer();
                changePlayIconToPlay();
            }
        }
    }
}

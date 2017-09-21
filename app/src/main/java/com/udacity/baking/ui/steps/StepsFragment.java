package com.udacity.baking.ui.steps;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.baking.R;
import com.udacity.baking.data.entities.Step;

import org.parceler.Parcels;

import static com.udacity.baking.utils.ViewUtils.hide;
import static com.udacity.baking.utils.ViewUtils.show;

public class StepsFragment extends Fragment implements ExoPlayer.EventListener {

    private final static String StepKey = "step";
    private final static String PositionKey = "position";
    private final static String TAG = StepsFragment.class.getSimpleName();

    private SimpleExoPlayerView mPlayerView;
    private SimpleDraweeView mImageView;
    private TextView mTvDescription;

    private boolean mLandscape;
    private boolean mTablet;
    private Step mStep;
    private long mPosition;

    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    public StepsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(StepKey)) {
                Parcelable parcel = savedInstanceState.getParcelable(StepKey);
                setStep((Step) Parcels.unwrap(parcel));
            }

            if (savedInstanceState.containsKey(PositionKey)) {
                mPosition = savedInstanceState.getLong(PositionKey, C.TIME_UNSET);
            }
        }

        final View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        mLandscape = rootView.findViewById(R.id.landscape) != null;
        mTablet = rootView.findViewById(R.id.tablet) != null;
        mTvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        mImageView = (SimpleDraweeView) rootView.findViewById(R.id.imageView);

        if (mStep != null) {
            mTvDescription.setText(mStep.getDescription());

            String videoSuffix = "mp4";

            if (mStep.getVideoURL().endsWith(videoSuffix)) {
                showPlayer(mStep.getVideoURL());
            } else if (mStep.getThumbnailURL().endsWith(videoSuffix)) {
                showPlayer(mStep.getThumbnailURL());
            } else {
                showImage();
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        if (mStep != null) {
            currentState.putParcelable(StepKey, Parcels.wrap(mStep));
        }

        if (mExoPlayer != null) {
            long position = mExoPlayer.getCurrentPosition();
            currentState.putLong(PositionKey, position);
        }

        releasePlayer();
    }

    public void setStep(Step step) {
        mStep = step;
    }

    private void showPlayer(String url) {
        show(mPlayerView);
        hide(mImageView);

        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.baking));

        initializeMediaSession();
        initializePlayer(Uri.parse(url));

        if (mLandscape && !mTablet) {
            hide(mTvDescription);
        }
    }

    private void showImage() {
        show(mImageView);
        hide(mPlayerView);
        show(mTvDescription);
    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder()
            .setActions(
                PlaybackStateCompat.ACTION_PLAY |
                PlaybackStateCompat.ACTION_PAUSE |
                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");

            MediaSource mediaSource = new ExtractorMediaSource(
                    mediaUri,
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);

            if (mPosition != C.TIME_UNSET) mExoPlayer.seekTo(mPosition);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) { }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) { }

    @Override
    public void onLoadingChanged(boolean isLoading) { }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(
                PlaybackStateCompat.STATE_PLAYING,
                mExoPlayer.getCurrentPosition(),
                1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(
                PlaybackStateCompat.STATE_PAUSED,
                mExoPlayer.getCurrentPosition(),
                1f);
        }

        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) { }

    @Override
    public void onPositionDiscontinuity() { }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
package com.audioplayer.fragments;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.NoSuchPaddingException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;

import com.audioplayer.app.R;


public class AudioFragment extends Fragment implements
		OnPreparedListener, MediaController.MediaPlayerControl{

	LinearLayout audioVideoView;

	private MediaPlayer mediaPlayer;
	private MediaController mediaController;

	private Handler handler = new Handler();

	private boolean isPaused = false;
	

	private static final String RESOLUTION_SIGNATURE_DIALOG_FRAGMENT = "resolutionSignatureDialogFragment";
	private static final String SIGNER_DIALOG_FRAGMENT = "signer_details_dialog_fragment";

	private int currentPosition;
	private File file;
	MenuItem sign;
	MenuItem action;
	MenuItem sync;
	View signatureBtn;
	private SearchView searchView;
	MenuItem selectedAnnotType;


	String audioLink = "";


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View view = getView();

		audioVideoView = (LinearLayout) view
				.findViewById(R.id.play_audio_layout);

		mediaController.setMediaPlayer(this);
		mediaController.setAnchorView(audioVideoView);
		mediaController.setEnabled(true);

		if(getArguments().getString("audio_link") != null){
			audioLink = getArguments().getString("audio_link");
			loadMaterial(audioLink);
		}


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_play_audio, container,
				false);

		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mediaController.show();
				return true;
			}
		});

		mediaController = new MediaController(getActivity());

		return view;

	}




	@Override
	public void onStart() {
		super.onStart();

		// isPaused = false;
	}


	public void loadMaterial(String  audioLink) {

		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnPreparedListener(this);

		try {
			mediaPlayer.setDataSource(audioLink);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// mediaPlayer.start();

	}

	@Override
	public void onPause() {
		super.onPause();

		if (mediaPlayer != null) {
			mediaPlayer.pause();
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		if (!isPaused) {
			if (mediaPlayer != null)
				mediaPlayer.start();
		}
	}

	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return false;
	}

	@Override
	public boolean canSeekForward() {
		return false;
	}

	@Override
	public int getAudioSessionId() {
		return 0;
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public int getCurrentPosition() {

		try {
			currentPosition = mediaPlayer.getCurrentPosition();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return currentPosition;
	}

	@Override
	public int getDuration() {
		return mediaPlayer.getDuration();

	}

	@Override
	public boolean isPlaying() {

		return mediaPlayer.isPlaying();

	}

	@Override
	public void pause() {
		isPaused = true;
		mediaPlayer.pause();
	}

	@Override
	public void seekTo(int i) {
		mediaPlayer.seekTo(i);

	}

	@Override
	public void start() {
		isPaused = false;
		mediaPlayer.start();
	}

	@Override
	public void onPrepared(final MediaPlayer mediaPlayer) {
		Log.d("TAG", "onPrepared");

		handler.post(new Runnable() {
			public void run() {

				mediaPlayer.start();

			}
		});
	}

	// public boolean onTouchEvent(MotionEvent event) {
	// // the MediaController will hide after 3 seconds - tap the screen to
	// // make it appear again
	// mediaController.show();
	// return false;
	// }



	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	}



	
}

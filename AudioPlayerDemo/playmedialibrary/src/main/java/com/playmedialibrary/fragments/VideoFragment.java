package com.playmedialibrary.fragments;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.playmedialibrary.app.R;


public class VideoFragment extends Fragment
		 {

	VideoView playVideoView;
	MediaController mc;

	int currentPosition;
	String videoLink = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aprio.fragments.DocumentViewFragment#onActivityCreated(android.os
	 * .Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View view = getView();
		playVideoView = (VideoView) view.findViewById(R.id.play_videoview);
		videoLink = getArguments().getString("video_link");
		loadMaterial(videoLink);


	}




	@Override
	public void onResume() {
		super.onResume();

		playVideoView.seekTo(currentPosition);
		playVideoView.resume();

	}

	@Override
	public void onPause() {
		super.onPause();
		currentPosition = playVideoView.getCurrentPosition();
		playVideoView.stopPlayback();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_play_video, container,
				false);

		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});

		return view;

	}



	public void loadMaterial(String videoLink) {

		try {

			Log.i("mov videoLink", videoLink);
			Log.i("video link extension", getExtension(videoLink));
			if (getExtension(videoLink).equals("mov")) {
				Toast.makeText(getActivity(),"Can't Play this video", Toast.LENGTH_LONG).show();
			} else {
				Uri uri = Uri.parse(videoLink);

				mc = new MediaController(getActivity());

				mc.setAnchorView(playVideoView);
				mc.setMediaPlayer(playVideoView);
				playVideoView.setMediaController(mc);

				playVideoView.setVideoURI(uri);

				playVideoView.setOnPreparedListener(new OnPreparedListener() {

					@Override
					public void onPrepared(MediaPlayer mp) {
						Log.d("App", "Prepared Video");
						// playVideoView.seekTo(currentPosition);
						playVideoView.start();
					}
				});

				playVideoView.setOnErrorListener(new OnErrorListener() {

					@Override
					public boolean onError(MediaPlayer mp, int what, int extra) {
						boolean showError = false;

						return !showError;
					}
				});

			}

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), "Unsupported file format",
					Toast.LENGTH_LONG).show();

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aprio.fragments.DocumentViewFragment#backClicked()
	 */


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		currentPosition = playVideoView.getCurrentPosition();
//		Toast.makeText(getActivity(), currentPosition + "", Toast.LENGTH_LONG)
//				.show();
		Log.v("Current position", currentPosition + "");
		playVideoView = null;
	}

			 private  String getExtension(String fileName) {
				 if (fileName != null && fileName.length() > 0) {
					 int i = fileName.lastIndexOf('.');
					 String fileExtension = fileName.substring(i + 1);
					 return fileExtension;

				 }
				 return "";

			 }



}

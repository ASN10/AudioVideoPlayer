package com.audioplayer.app;

import android.app.FragmentManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.audioplayer.fragments.AudioFragment;
import com.audioplayer.fragments.VideoFragment;

public class MainActivity extends AppCompatActivity {


    private FrameLayout layout;
    AudioFragment fragment;
    VideoFragment videoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (FrameLayout) findViewById(R.id.fragment_layout);
      //  String filePath = Environment.getExternalStorageDirectory()+"/Ringtones/hangouts_video_call.ogg";
      //  String filePath = Environment.getExternalStorageDirectory()+"/DCIM/Camera/VID_20150925_125724.mp4";
      //  String filePath = Environment.getExternalStorageDirectory()+"/DCIM/Camera/test.mp4";

        String filePath = getPathOfTheVideo();
        Log.i("filepath", filePath);


        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        AudioFragment fragment = new AudioFragment();
        Bundle args = new Bundle();
        args.putString("audio_link", filePath);
        fragment.setArguments(args);
        fm.beginTransaction().add(R.id.fragment_layout,fragment, "audio").commit();


//        videoFragment = new VideoFragment();
//        Bundle args = new Bundle();
//        args.putString("video_link", filePath);
//        videoFragment.setArguments(args);
//        android.support.v4.app.FragmentManager fmanager = getSupportFragmentManager();
//        fmanager.beginTransaction().add(R.id.fragment_layout, videoFragment, "video").commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private String getPathOfTheVideo(){
        String url = "android.resource://" + getPackageName() + "/" + R.raw.demo_video;
        Log.i("url",url);
        return url;
    }
}



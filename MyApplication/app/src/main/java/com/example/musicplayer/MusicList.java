package com.example.musicplayer;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MusicList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView notFound_Text;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);


        ArrayList<Music> musicList = new ArrayList<Music>();
        String[] projection = new String[] {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST
        };
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";


        String sortOrder = MediaStore.Audio.Media.DISPLAY_NAME + " ASC";


        try (Cursor cursor = getApplicationContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                sortOrder
        );) {
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                long duration = cursor.getInt(durationColumn);
                long minutes = (duration / 1000) / 60;
                long seconds = (duration / 1000) % 60;
                String secondsStr = Long.toString(seconds);
                String secs;
                if (secondsStr.length() >= 2) {
                    secs = secondsStr.substring(0, 2);
                } else {
                    secs = "0" + secondsStr;
                }
                String duration_str = String.valueOf(minutes) +":" + String.valueOf(secs);

                String artistName = cursor.getString(artistColumn);

                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                musicList.add(new Music(contentUri, name, artistName, duration_str));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("YANLIŞ OLDU BİR ŞEYLER: " + e.getMessage());
        }
        if(musicList.size() != 0){
            notFound_Text = (TextView) findViewById(R.id.notFound_Text);
            recyclerView = (RecyclerView) findViewById(R.id.music_recyclerview);

            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            mAdapter = new MusicAdapter(getApplicationContext(), musicList);
            recyclerView.setAdapter(mAdapter);
        }else{
            notFound_Text.setVisibility(View.VISIBLE);
        }
    }
}
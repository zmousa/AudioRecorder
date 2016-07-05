package com.zenus.audioRecorder.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.zenus.audioRecorder.R;
import com.zenus.audioRecorder.util.Constants;
import com.zenus.audioRecorder.util.Utilities;

import java.io.File;
import java.util.ArrayList;

public class RecordsListActivity extends Activity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_list);
        lv = (ListView) findViewById(R.id.list_file);
        initComponents();
    }

    private void initComponents(){
        final ArrayList<String> FilesInFolder = GetFiles(Utilities.getDataFolder(Constants.APP_FOLDER_NAME) + "/");

        if (FilesInFolder != null) {
            lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, FilesInFolder));

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    MediaPlayer m = new MediaPlayer();
                    try {
                        m.setDataSource(Utilities.getDataFolder(Constants.APP_FOLDER_NAME) + "/" + FilesInFolder.get(position));
                        m.prepare();
                        m.start();
                        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer m) {
                            m.release();
                        }
                    });
                }
            });
        }
    }

    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<>();

        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();

        if (files.length == 0)
            return null;
        else {
            for (int i=0; i<files.length; i++)
                MyFiles.add(files[i].getName());
        }
        return MyFiles;

    }

    public void backMain(View v){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

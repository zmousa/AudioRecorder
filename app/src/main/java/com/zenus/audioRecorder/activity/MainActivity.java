package com.zenus.audioRecorder.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.zenus.audioRecorder.R;
import com.zenus.audioRecorder.business.RecorderController;
import com.zenus.audioRecorder.util.Constants;
import com.zenus.audioRecorder.util.SharedPreferencesHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private Button start, stop,show;
    private RecorderController recorderController;
    private Spinner qualitySpinner, extSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start=(Button)findViewById(R.id.startButton);
        stop=(Button)findViewById(R.id.stopButton);
        show=(Button)findViewById(R.id.showFiles);
        qualitySpinner = (Spinner) findViewById(R.id.qualitySpinner);
        extSpinner = (Spinner) findViewById(R.id.extSpinner);

        initComponents();
        recorderController = new RecorderController();
    }

    private void initComponents(){
        start=(Button)findViewById(R.id.startButton);
        start.setOnClickListener(startListener);
        stop=(Button)findViewById(R.id.stopButton);
        stop.setOnClickListener(stopListener);
        show=(Button)findViewById(R.id.showFiles);
        show.setOnClickListener(showListener);

        List<String> qualityList = new ArrayList<>();
        qualityList.add(Constants.QUALITY_8);
        qualityList.add(Constants.QUALITY_16);
        ArrayAdapter<String> qualityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, qualityList);
        qualityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qualitySpinner.setAdapter(qualityAdapter);
        qualitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                final String quality = String.valueOf(qualitySpinner.getSelectedItem());
                if (quality.equals(Constants.QUALITY_8)) {
                    SharedPreferencesHandler.saveStringValue(Constants.Preferences.RECORDER_AUDIO_QUALITY, "8");
                } else if (quality.equals(Constants.QUALITY_16)) {
                    SharedPreferencesHandler.saveStringValue(Constants.Preferences.RECORDER_AUDIO_QUALITY, "16");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        List<String> extList = new ArrayList<>();
        extList.add(Constants.EXT_MP3);
        extList.add(Constants.EXT_WAV);
        ArrayAdapter<String> extAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, extList);
        extAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        extSpinner.setAdapter(extAdapter);
        extSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String ext = String.valueOf(extSpinner.getSelectedItem());
                SharedPreferencesHandler.saveStringValue(Constants.Preferences.RECORDER_AUDIO_EXTENSION, ext);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode, data);
    }

    private View.OnClickListener startListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            recorderController.startRecording();
            start.setEnabled(false);
            stop.setEnabled(true);
        }
    };

    private View.OnClickListener stopListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            recorderController.stopRecording();
            start.setEnabled(true);
            stop.setEnabled(false);
        }
    };

    private View.OnClickListener showListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                Intent intent=new Intent(MainActivity.this,RecordsListActivity.class);
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}

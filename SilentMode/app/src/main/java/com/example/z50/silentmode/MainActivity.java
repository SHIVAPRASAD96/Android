package com.example.z50.silentmode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Set;

public class MainActivity extends Activity {
        LocationTracker loc =null;
        public static String latitude;
        String longitude="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            loc= new LocationTracker(this);
       Button btnFind= (Button) findViewById(R.id.btn_find_location);

            btnFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText txtLocationName = (EditText) findViewById(R.id.txt_name);
                    String name = txtLocationName.getText().toString();
                    if(name.equals("")){
                        Toast.makeText(MainActivity.this,
                                "Please Enter group name",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Location   l = loc.getLocation();
                    if(l !=null) {
                        ((TextView) findViewById(R.id.lbl_latitude)).setText(l.getLatitude() + "");
                        ((TextView) findViewById(R.id.lbl_longitude)).setText(l.getLongitude() + "");
                        Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Unable to get the location", Toast.LENGTH_SHORT).show();
                    }
                    //latitude= Double.toString( l.getLatitude());
                    //longitude=Double.toString( l.getLongitude());

           // Location l= loc.getLocation();


        //Set<String> existingName = StorageHelper.getAppData(MainActivity.this,"groupName");

       // if( !existingName.equals("")){
         //   existingName = existingName ;}




       //String finalStr = existingGroup + "#" + gname;

        StorageHelper.saveAppData(MainActivity.this, "groupName",l);

        Toast.makeText(MainActivity.this, "Group Created!",
                Toast.LENGTH_SHORT).show();
        txtLocationName.setText("");

                }
            });

       /* Location g = loc.getLocation();
        Double distance= LocationUtil.getDistance(g.getLatitude(),g.getLongitude(),(g.getLatitude()),(g.getLongitude()),LocationUtil.UNIT_KM);
            if(Math.abs(distance) >= 0.03){
                AudioManager audioManage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManage.setRingerMode(AudioManager.RINGER_MODE_NORMAL );
            }
            else{
                AudioManager audioManage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE );
            }*/


        AlarmReceiver alarm = new AlarmReceiver();
        alarm.setAlarm(this);

       Button btnLocations = (Button) findViewById(R.id.btn_locations);
        btnLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(MainActivity.this,StoredLocations.class);
                startActivityForResult(in,0);

            }
        });

    }
}

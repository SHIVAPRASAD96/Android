package com.example.z50.silentmode;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.lang.*;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SchedulingService extends IntentService {
	public SchedulingService() {
		super("SchedulingService");
	}

	public static final String TAG = "SchedulingService";
	// An ID used to post the notification.
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
    LocationTracker loc =null;



	@Override
	protected void onHandleIntent(Intent intent) {
		try {   loc= new LocationTracker(getApplicationContext());
            Location L = loc.getLocation();
            Set<String> s1 = StorageHelper.getAppData(getApplicationContext(),"groupName");
            s1.toString();
            String o1,o2;
            //T [] array= s1.toArray(new T[s1.size()]);
            List<String> list = new ArrayList<String>(s1);
            o1=list.get(0);
            o2=list.get(1);


            Double d1 = Double.parseDouble(o1);
            Double d2 = Double.parseDouble(o2);
            Double Dist = LocationUtil.getDistance(d2,d1,L.getLatitude(),L.getLongitude(),LocationUtil.UNIT_KM);
            if(Math.abs(Dist) >= 0.05){
                AudioManager audioManage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManage.setRingerMode(AudioManager.RINGER_MODE_NORMAL );
            }
            else{
                AudioManager audioManage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManage.setRingerMode(AudioManager.RINGER_MODE_VIBRATE );
            }





        }catch (Exception ex){
				Log.e("Location", ex.getMessage());
			}


		 finally {
			// Release the wake lock provided by the BroadcastReceiver.
			AlarmReceiver.completeWakefulIntent(intent);
			// END_INCLUDE(service_onhandle)
		}

	}


}

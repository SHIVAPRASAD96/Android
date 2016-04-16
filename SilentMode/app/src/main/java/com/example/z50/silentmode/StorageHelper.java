package com.example.z50.silentmode;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Z50 on 29-07-2015.
 */
public class StorageHelper {
   public static Set<String> l = new HashSet();
    //public static Set location = new HashSet();
    public static final String PREFS_NAME = "SMS_APP_DATA";

    public static void saveAppData(Context ctx, String key, Location value) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        String lat = Double.toString(value.getLatitude());
        String lon = Double.toString(value.getLongitude());
        Set location = new HashSet();
        location.add(lat);
        location.add(lon);
        editor.putStringSet(key, location);
        editor.apply();
    }

    public static Set<String> getAppData(Context ctx, String key) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME, 0);
        //Set<String> l = new HashSet();

        return settings.getStringSet(key, l );
    }

  public static List<String> getAllKeys(Context ctx){
      List<String> keyList = new ArrayList<String>();
      SharedPreferences settings = ctx.getSharedPreferences(PREFS_NAME,0);

      Map<String,?> allEntries = settings.getAll();

      for(Map.Entry<String,?> entry :
              allEntries.entrySet()){

          keyList.add(entry.getKey());


      }
      return  keyList;
  }

}

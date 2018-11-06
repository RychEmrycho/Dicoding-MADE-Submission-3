package com.developnerz.indie_indonesianenglishdictionary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.developnerz.indie_indonesianenglishdictionary.R;

/**
 * Created by Rych Emrycho on 8/31/2018 at 1:43 AM.
 * Updated by Rych Emrycho on 8/31/2018 at 1:43 AM.
 */
public class SharedPreference {
    SharedPreferences preference;
    Context context;

    public SharedPreference(Context context) {
        preference = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input){
        SharedPreferences.Editor editor = preference.edit();
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key, input);
        editor.commit();
    }

    public boolean getFirstRun(){
        String key = context.getResources().getString(R.string.app_first_run);
        return preference.getBoolean(key, true);
    }
}

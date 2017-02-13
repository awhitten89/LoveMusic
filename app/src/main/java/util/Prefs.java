package util;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by alanwhitten on 21/10/2016.
 */

public class Prefs {

    SharedPreferences preferences;

    public Prefs(Activity activity){
        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    /**
     * Save city to shared preferences
     * @param country
     */
    public void setCountry(String country){
        preferences.edit().putString("country", country).commit();
    }

    /**
     * If user does not pick a city then return the default.
     * @return
     */
    public String getCountry(){
        return preferences.getString("country", "United Kingdom");
    }
}

package css.com.applab.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



public class SharedPreferencesUtils {

    private static final String PREFERENCE_NAME = "MDemo";
    private static final String PREFERENCE_KEY_MAC = "mac";

    public void setShakeMac(Context context,String deivecMac){
        SharedPreferences share = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = share.edit();
        editor.putString(PREFERENCE_KEY_MAC, deivecMac);
        editor.apply();
    }

    public String getShakeMac(Context context){
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getString(PREFERENCE_KEY_MAC,"");
    }


}

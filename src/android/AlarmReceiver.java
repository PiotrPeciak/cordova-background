package pl.dwape.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Piotrek on 28.12.2016.
 */
public class AlarmReceiver extends BroadcastReceiver
{
    protected String TAG = "PPE";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.w(TAG, "   ->   - AlarmReceiver : execute");
    }
}

package pl.dwape.service;

import org.apache.cordova.CordovaPlugin;


import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.app.AlarmManager;
import android.os.SystemClock;
public class TestPlugin extends CordovaPlugin {
    private static final String LOG_TAG = "PPE";
    private Context context;
    int counter = 0;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		this.context = cordova.getActivity().getApplicationContext();
		Log.i(LOG_TAG, "PPE: my test plugin has been initialized!");
		// your init code here
		//this.webView = webView;
	}
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("test")) {
			this.test(args);
			callbackContext.success();
			return true;

		} else if (action.equals("alarm")) {
			this.setAlarm(callbackContext, this.context, args);
			return true;
		} else if (action.equals("job")) {
			this.setJob(callbackContext, this.context, args);
			return true;
		}
		return false;
	}

	private boolean setAlarm(final CallbackContext cbc, Context ctx, JSONArray data) {
        Log.w(LOG_TAG, "Set alarm with params: "+data.toString());
		try {
			Intent intent = new Intent(ctx, AlarmReceiver.class);
			//intent.setAction("pl.dwape.service.AlarmReceiver");

			PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
			//alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10000, pendingIntent);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60*1000, pendingIntent);
			makeToast("Alarm was set...");
			Log.w(LOG_TAG, "Alarm was set");
			cbc.success("Repeated alarm has been setted!");
			return true;
		} catch(Exception e) {
			cbc.error("Failed!");
			return false;
		}
	}

	private boolean setJob(final CallbackContext cbc, Context ctx, JSONArray data) {
		Log.i(LOG_TAG, "exec setJob " + counter++);
		ComponentName mServiceComponent = new ComponentName(ctx, TestService.class);
        JobInfo.Builder builder = new JobInfo.Builder(counter, mServiceComponent);
        //latency and deadline OR periodic
        //builder.setMinimumLatency(5 * 1000); // wait at least
        //builder.setOverrideDeadline(50 * 1000); // maximum delay
        builder.setPeriodic(60 * 1000); //petla?

        //builder.setPersisted(true) //wymaga RECEIVE_BOOT_COMPLETED
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = (JobScheduler) ctx.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        try {
	        int scheduleResultCode = jobScheduler.schedule(builder.build());
	        boolean scheduleResult = (scheduleResultCode == JobScheduler.RESULT_SUCCESS);
	        makeToast("Job scheduled...");
	        Log.i(LOG_TAG, "Job has been scheduled (code: " + scheduleResultCode + ", result: " + scheduleResult + ")");
			cbc.success("Job has been scheduled!");
			return true;
		} catch(Exception e) {
			cbc.error("Error: " + e.getMessage());
			return false;
		}
	}
	
	public void makeToast(String msg) {
		Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show();      
	}
	
	public void test(JSONArray args) {
		Log.i(LOG_TAG, "Execute test function with arguments: " + args.toString());
        //Toast.makeText(this.context, "Alarm Scheduled for Tommrrow", Toast.LENGTH_LONG).show();
        makeToast("execute test function");
    }
}

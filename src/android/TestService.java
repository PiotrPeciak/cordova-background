package pl.dwape.service;

import java.util.LinkedList;
import java.lang.Void;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class TestService extends JobService {
	private static final String TAG = "PPE";
    private MyAsyncTask myTask = new MyAsyncTask();

	@Override
    public void onCreate() {
        super.onCreate();
        //Log.i(TAG, "   -> Service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.i(TAG, "   -> Service destroyed");
    }
 
    @Override
    public boolean onStartJob(JobParameters params) {
        //Log.i(TAG, "   -> on start job: " + params.getJobId() + " - execute task...");

        myTask.execute(params);
        //Log.i(TAG, "   -> task has been submited");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "stop job: " + params.getJobId());
        return true;
    }

    private class MyAsyncTask extends AsyncTask<JobParameters, Void, JobParameters[]> {
        @Override
        protected JobParameters[] doInBackground(JobParameters... params) {
            // Do updating and stopping logical here.
            Log.i(TAG, "   ->   - ScheduledTask : do in background");

            return params;
        }

        @Override
        protected void onPostExecute(JobParameters[] result) {
            for (JobParameters params : result) {
                if (!hasJobBeenStopped(params)) {
                    //Log.i(TAG, "   ->   - MyTask: call jobFinished and try reschedule...");
                    jobFinished(params, true); //second parameter is need to reschedule?
                }
            }
        }

        private boolean hasJobBeenStopped(JobParameters params) {
            //Log.i(TAG, "   ->   - MyTask: ask if job schould been stopped? - returned false");
            return false;
        }

        public boolean stopJob(JobParameters params) {
            //Logic for stopping a job. return true if job should be rescheduled.
            //Log.i(TAG, "   ->   - MyTask: Task has been finished, rechedule...");
            return true;
        }

    }
 
}
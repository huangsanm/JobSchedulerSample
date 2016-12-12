package com.huashengmi.jobs.jobs;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by huangsanm@gmail.com on 2016/12/8.
 */

public class TaskJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        mHandler.sendMessage(Message.obtain(mHandler, 1, jobParameters) );
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        mHandler.removeMessages(1);
        return false;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText( getApplicationContext(),
                    "JobService task running", Toast.LENGTH_SHORT )
                    .show();
            jobFinished( (JobParameters) msg.obj, false );
        }
    };


}

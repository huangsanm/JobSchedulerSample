package com.huashengmi.jobs;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.huashengmi.jobs.jobs.TaskJobService;

public class MainActivity extends AppCompatActivity {

    public static final int TASK_NUM = 1;

    private JobScheduler mJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        findViewById(R.id.btn_start_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTask();
            }
        });
    }

    /**
     * 创建任务
     */
    private void startTask() {
        ComponentName cn = new ComponentName(this, TaskJobService.class);
        JobInfo.Builder jobBuilder = new JobInfo.Builder(TASK_NUM, cn);
        jobBuilder.setPeriodic(3000);//任务每三秒定期运行一次
        //setMinimumLatency(long minLatencyMillis)：在规定的时间内到了不执行任务,和setPeriodic不兼容
        //setOverrideDeadline(long maxExecutionDelayMillis) 还没有到规定时间内任务已经开始执行,和setPeriodic不兼容
        //setPersisted(boolean isPersisted)：设置重启之后任务是否继续执行。
        //setRequiredNetworkType(int networkType)：设备处于一种特定的网络中时，它才启动。
        // 它的默认值是JobInfo.NETWORK_TYPE_NONE，这就意味着，无论是否有网络连接，该任务均可以运
        //行。另外两个可用的类型是JobInfo.NETWORK_TYPE_ANY，这需要某种类型的网络连接可用，工作才可以运行；
        // 以及JobInfo.NETWORK_TYPE_UNMETERED，这就要求设备在非蜂窝网络中
        //setRequiresCharging(boolean requiresCharging)：设备开始充电时任务开始执行。
        //setRequiresDeviceIdle(boolean requiresDeviceIdle)：如果用户没有使用设置，该任务不执行。
        final int result = mJobScheduler.schedule(jobBuilder.build());
        if (result <= 0) {
            Toast.makeText(this, "任务创建失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消单个
        //mJobScheduler.cancel(TASK_NUM);
        //取消全部
        mJobScheduler.cancelAll();
    }

}

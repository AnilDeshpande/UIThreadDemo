package uithreadsdemo.youtube.com.uithreaddemo;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import java.util.Random;

public class MyIntentService extends JobService {

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    JobParameters jobParameters;

    /**
     * Return FALSE when this jpb is of short duration
     * and needs to be executed for very small time.
     * By default everything here runs on UI thread. If you don't
     * want to block the UI thread with long running work, then use thread.
     * Return TRUE whenever you are running long running tasks. So when you are
     * using a thread to do long running task, return true.
     * @param jobParameters
     * @return
     */
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(getString(R.string.service_demo_tag),"onStartJob");
        this.jobParameters = jobParameters;
        doBackgroundWork();
        return true;
    }

    private void doBackgroundWork(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mIsRandomGeneratorOn =true;
                startRandomNumberGenerator();
            }
        }).start();
    }

    /**
     * This method gets called when job gets cancelled.
     * Return True if you want to restart the job automatically when a condition is met (WIFI - ON)
     * Return false if you want don't want to restart the job automatically even when the condition is met (WIFI - OFF)
     * @param jobParameters
     * @return
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(getString(R.string.service_demo_tag),"onStopJob, JobId: "+jobParameters.getJobId());
        return true;
    }

    private void startRandomNumberGenerator(){
        int counter=0;
        while (counter<5){
            try{
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber =new Random().nextInt(MAX)+MIN;
                    Log.i(getString(R.string.service_demo_tag),"Thread id: "+Thread.currentThread().getId()+
                            ", Random Number: "+ mRandomNumber+"" +
                            "jobId: "+jobParameters.getJobId());
                }
            }catch (InterruptedException e){
                Log.i(getString(R.string.service_demo_tag),"Thread Interrupted");
            }
            counter++;
        }
        this.jobFinished(jobParameters,true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsRandomGeneratorOn=false;
        Log.i(getString(R.string.service_demo_tag),getString(R.string.string_stopservice)+
                ", thread Id: "+Thread.currentThread().getId()+
                ", jobId: "+jobParameters.getJobId());
    }
}

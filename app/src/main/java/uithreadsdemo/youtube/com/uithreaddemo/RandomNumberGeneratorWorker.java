package uithreadsdemo.youtube.com.uithreaddemo;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ForegroundInfo;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;

public class RandomNumberGeneratorWorker extends Worker {

    Context context;
    WorkerParameters workerParameters;

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    MyAppsNotificationManager myAppsNotificationManager;
    private final int NOTIFICATION_ID = 10;

    public RandomNumberGeneratorWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.workerParameters = workerParams;
        mIsRandomGeneratorOn = true;
        myAppsNotificationManager = MyAppsNotificationManager.getInstance(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        setForegroundAsync(createForeGroundInfo());
        startRandomNumberGenerator();
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(context.getString(R.string.service_demo_tag),"Worker has been cancelled");
    }

    private void startRandomNumberGenerator(){
        while (!isStopped()){
            try{
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber =new Random().nextInt(MAX)+MIN;
                    Log.i(context.getString(R.string.service_demo_tag),"Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
                }
            }catch (InterruptedException e){
                Log.i(context.getString(R.string.service_demo_tag),"Thread Interrupted");
            }
        }
    }

    private ForegroundInfo createForeGroundInfo(){
        Notification notification = myAppsNotificationManager.getNotification(MainActivity.class,
                "Randon Number",
                1,
                false,
                NOTIFICATION_ID);
        return new ForegroundInfo(NOTIFICATION_ID, notification);
    }
}

package uithreadsdemo.youtube.com.uithreaddemo;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyIntentService extends IntentService{

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    public MyIntentService(){
        super(MyIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        startForeground(1, getNotification());
        mIsRandomGeneratorOn =true;
        startRandomNumberGenerator();
    }

    private void startRandomNumberGenerator(){
        while (mIsRandomGeneratorOn){
            try{
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber =new Random().nextInt(MAX)+MIN;
                    Log.i(getString(R.string.service_demo_tag),"Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
                }
            }catch (InterruptedException e){
                Log.i(getString(R.string.service_demo_tag),"Thread Interrupted");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsRandomGeneratorOn=false;
        Log.i(getString(R.string.service_demo_tag),getString(R.string.string_stopservice)+ ", thread Id: "+Thread.currentThread().getId());
    }

    private Notification getNotification(){
        return MyApplication.getMyAppsNotificationManager().getNotification(MainActivity.class,
                "BackgroundService running",
                1,
                false,
                1);
    }
}

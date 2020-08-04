package uithreadsdemo.youtube.com.uithreaddemo;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import java.util.Random;

public class MyIntentService extends JobIntentService {

    private int mRandomNumber;

    boolean isThreadOn = false;
    private final int MIN=0;
    private final int MAX=100;

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    static void enqueueWork(Context context, Intent intent){
        enqueueWork(context,MyIntentService.class, 101, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.i(getString(R.string.service_demo_tag), "onHandleWork, Thread Id: "+Thread.currentThread().getId());
        isThreadOn = true;
        startRandomNumberGenerator(intent.getStringExtra("starter"));
    }

    private void startRandomNumberGenerator(String starterIdentifier){
        for (int i=0;i<5;i++){
            try{
                if(isStopped()){
                    Log.i(getString(R.string.service_demo_tag), "Sorry, JobScheduler is force stopping the thread: "+ isStopped());
                    return;
                }
                Thread.sleep(1000);
                mRandomNumber =new Random().nextInt(MAX)+MIN;
                Log.i(getString(R.string.service_demo_tag),"Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber+", "+starterIdentifier);
            }catch (InterruptedException e){
                Log.i(getString(R.string.service_demo_tag),"Thread Interrupted");
            }
        }
        Log.i(getString(R.string.service_demo_tag),"Service stopped");
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(getString(R.string.service_demo_tag),getString(R.string.string_stopservice)+ ", thread Id: "+Thread.currentThread().getId());
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.i(getString(R.string.service_demo_tag), "onStopCurrentWork, Thread Id: "+Thread.currentThread().getId());
        return super.onStopCurrentWork();
    }

    @Override
    public IBinder onBind(@NonNull Intent intent) {
        return super.onBind(intent);
    }
}

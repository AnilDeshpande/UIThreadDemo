package uithreadsdemo.youtube.com.uithreaddemo;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import java.util.Random;

public class MyIntentService extends JobIntentService {

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    static void enqueueWork(Context context, Intent intent){
        enqueueWork(context,MyIntentService.class, 101, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.i(getString(R.string.service_demo_tag), "onHandleWork, Thread Id: "+Thread.currentThread().getId());
        mIsRandomGeneratorOn = true;
        startRandomNumberGenerator();
    }

    private void startRandomNumberGenerator(){
        while (mIsRandomGeneratorOn){
            try{
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber =new Random().nextInt(MAX)+MIN;
                    Log.i(getString(R.string.service_demo_tag),"Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
                }else {
                    Log.i(getString(R.string.service_demo_tag),"Service stopped");
                    return;
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

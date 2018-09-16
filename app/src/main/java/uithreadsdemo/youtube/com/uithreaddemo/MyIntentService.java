package uithreadsdemo.youtube.com.uithreaddemo;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

public class MyIntentService extends IntentService{

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    public MyIntentService(String serviceName){
        super(serviceName);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
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
    }
}

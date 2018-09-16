package uithreadsdemo.youtube.com.uithreaddemo;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Random;

public class MyIntentService extends IntentService{

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    public MyIntentService(){
        super(MyIntentService.class.getSimpleName());
    }

    class MyIntentServiceBinder extends Binder{
        public MyIntentService getService(){
            return MyIntentService.this;
        }
    }

    private IBinder mBinder = new MyIntentServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(getString(R.string.service_demo_tag),"In OnBind");
        return mBinder;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(getString(R.string.service_demo_tag),"Service Started");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(getString(R.string.service_demo_tag),"In OnReBind");
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
                    if(!Util.isNumberPrime(mRandomNumber)){
                        triggerNotification(mRandomNumber);
                    }
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
        Log.i(getString(R.string.service_demo_tag),"Service Destroyed");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(getString(R.string.service_demo_tag),"In onUnbind");
        return super.onUnbind(intent);
    }

    public int getRandomNumber(){
        return mRandomNumber;
    }

    private void triggerNotification(int number){

        Log.i(getString(R.string.service_demo_tag),"Triggering Notification");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"prime_number_channel")
                .setSmallIcon(R.drawable.prime_icon)
                .setContentTitle("Prime number generated")
                .setContentText("Prime number is : "+number)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("A service generated prime number"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());


    }
}

package uithreadsdemo.youtube.com.uithreaddemo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Random;

public class RandomNumberGeneratorWorker3 extends Worker {
    Context context;
    WorkerParameters workerParameters;

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=301;
    private final int MAX=400;

    public RandomNumberGeneratorWorker3(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.workerParameters = workerParams;
        mIsRandomGeneratorOn = true;
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        startRandomNumberGenerator();
        return ListenableWorker.Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(context.getString(R.string.worker_demo),"Worker3 has been cancelled");
    }

    private void startRandomNumberGenerator(){
        int i=0;
        while (i<5 && !isStopped()){
            try{
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber =new Random().nextInt(MAX)+MIN;
                    Log.i(context.getString(R.string.worker_demo),"Worker3: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
                    i++;
                }
            }catch (InterruptedException e){
                Log.i(context.getString(R.string.worker_demo),"Thread Interrupted");
            }
        }
    }
}

package uithreadsdemo.youtube.com.uithreaddemo;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button buttonStart, buttonStop;
    private TextView textViewthreadCount;
    int count = 0;

    private MyIntentService myService;
    private boolean isServiceBound;
    private ServiceConnection  serviceConnection;

    WorkManager workManager;

    /*Handler handler;*/


    private  Intent serviceIntent;

    private boolean mStopLoop;

    private OneTimeWorkRequest workRequest1, workRequest2, workRequest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(getString(R.string.worker_demo), "MainActivity thread id: " + Thread.currentThread().getId());

        buttonStart = (Button) findViewById(R.id.buttonThreadStarter);
        buttonStop = (Button) findViewById(R.id.buttonStopthread);

        textViewthreadCount = (TextView) findViewById(R.id.textViewthreadCount);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        //workManager = WorkManager.getInstance(getApplicationContext());

        //workRequest = OneTimeWorkRequest.from(RandomNumberGeneratorWorker.class);
        workRequest1 = new OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker1.class).addTag("worker1").build();
        workRequest2 = new OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker2.class).addTag("worker2").build();
        workRequest3 = new OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker3.class).addTag("worker3").build();




        workManager = WorkManager.getInstance(getApplicationContext());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonThreadStarter:
                mStopLoop = true;
                //workManager.enqueue(workRequest);
                WorkManager.getInstance(getApplicationContext()).beginWith(workRequest1).then(workRequest2).then(workRequest3).enqueue();
                break;
            case R.id.buttonStopthread:
                //workManager.cancelWorkById(workRequest.getId());
                workManager.cancelAllWorkByTag("worker3");
                break;
        }
    }
}

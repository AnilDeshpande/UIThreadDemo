package uithreadsdemo.youtube.com.uithreaddemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button buttonStart, buttonStop;
    private TextView textViewthreadCount;
    int count = 0;

    Handler handler;

    private boolean mStopLoop;

    LooperThread looperThread;
    CustomHandlerThread customHandlerThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"Thread id of Main thread: "+Thread.currentThread().getId());

        buttonStart = (Button) findViewById(R.id.buttonThreadStarter);
        buttonStop = (Button) findViewById(R.id.buttonStopthread);

        textViewthreadCount = (TextView) findViewById(R.id.textViewthreadCount);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        looperThread=new LooperThread();
        looperThread.start();

        customHandlerThread=new CustomHandlerThread("HandlerThread");
        customHandlerThread.start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonThreadStarter:
                mStopLoop = true;
                //executeOnCustomLooper();
                executeOnCustoLopperWithCustomHandler();
               break;
            case R.id.buttonStopthread: mStopLoop = false;
                                        break;

        }
    }

    public void executeOnCustoLopperWithCustomHandler(){

        looperThread.handler.post(new Runnable() {
            @Override
            public void run() {
                while (mStopLoop){
                    try{
                        Thread.sleep(1000);
                        count++;
                        //looperThread.handler.sendMessage(getMessageWithCount(""+count));
                        customHandlerThread.mHandler.sendMessage(getMessageWithCount(""+count));
                        Log.i(TAG,"Thread id of Runnable posted: "+Thread.currentThread().getId());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG,"Thread id of runOnUIThread: "+Thread.currentThread().getId()+", Count : "+count);
                                textViewthreadCount.setText(" "+count);
                            }
                        });
                    }catch (InterruptedException exception){
                        Log.i(TAG,"Thread for interrupted");
                    }

                }
            }
        });
    }

    public void executeOnCustomLooper(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (mStopLoop){
                    try{
                        Log.i(TAG,"Thread id of thread that sends message: "+Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;
                        Message message=new Message();
                        message.obj=""+count;
                        looperThread.handler.sendMessage(message);
                    }catch (InterruptedException exception){
                        Log.i(TAG,"Thread for interrupted");
                    }

                }
            }
        }).start();

    }

    public void executeOnCustomHandlerLooper(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (mStopLoop){
                    try{
                        Log.i(TAG,"Thread id of thread that sends message: "+Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;
                        Message message=new Message();
                        message.obj=""+count;
                        looperThread.handler.sendMessage(message);
                    }catch (InterruptedException exception){
                        Log.i(TAG,"Thread for interrupted");
                    }

                }
            }
        }).start();

    }

    private Message getMessageWithCount(String count){
        Message message=new Message();
        message.obj=""+count;
        return message;
    }
}

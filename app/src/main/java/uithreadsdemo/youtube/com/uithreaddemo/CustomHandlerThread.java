package uithreadsdemo.youtube.com.uithreaddemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by anildeshpande on 8/3/17.
 */

public class CustomHandlerThread extends HandlerThread {

    private static final String TAG = CustomHandlerThread.class.getSimpleName();

    public Handler mHandler;

    public CustomHandlerThread(String name){
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i(TAG,"Thread id when message is posted: "+ Thread.currentThread().getId()+", Count : "+msg.obj);
            }
        };
    }

}

package servicetest.naik.mahesh.servicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView  tt  = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tt = (TextView) findViewById(R.id.textView);


        ResponseReceiver mDownloadStateReceiver =
                new ResponseReceiver();
        // Registers the DownloadStateReceiver and its intent filters
        IntentFilter statusIntentFilter = new IntentFilter(MyIntentService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mDownloadStateReceiver,
                statusIntentFilter);

        // Updating from Broadcast receiver
        MyIntentService mt  =  new MyIntentService();
        mt.startActionBaz(this , "","");

        //Updating UI from Messenger
      Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("messenger", new Messenger(handler));
        startService(intent);


    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle reply = msg.getData();
            tt.setText(""+reply.getInt("RESULT_ID"));
        }
    };


    private class ResponseReceiver extends BroadcastReceiver
    {
        // Prevents instantiation
        private ResponseReceiver() {
        }
        // Called when the BroadcastReceiver gets an Intent it's registered to receive

        public void onReceive(Context context, Intent intent) {

            tt.setText(""+intent.getIntExtra(MyIntentService.EXTENDED_DATA_STATUS,0));

        }
    }
}

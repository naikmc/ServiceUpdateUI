package servicetest.naik.mahesh.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS

    private static final String ACTION_BAZ = "servicetest.naik.mahesh.servicetest.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "servicetest.naik.mahesh.servicetest.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "servicetest.naik.mahesh.servicetest.extra.PARAM2";
    // Defines a custom Intent action
    public static final String BROADCAST_ACTION =
            "com.example.android.threadsample.BROADCAST";

    // Defines the key for the status "extra" in an Intent
    public static final String EXTENDED_DATA_STATUS =
            "com.example.android.threadsample.STATUS";
    public MyIntentService() {
        super("MyIntentService");
    }



    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }


            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Messenger messenger = (Messenger) bundle.get("messenger");
                if (messenger != null) {
                    for (int i = 0; i < 5; i++) {
                        Message msg = Message.obtain();
                        msg.what = 1;
                        Bundle bundle1 = msg.getData();
                        bundle1.putInt("RESULT_ID", i);
                        Log.i("Message @@@@### = ", "no." + i);
                        try {
                            Thread.sleep(1500);
                            messenger.send(msg);
                        } catch (Exception e) {
                            Log.i("error", "error");
                        }
                    }
                }
            }
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        for(int i =0 ;i<5;i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent localIntent =
                    new Intent(BROADCAST_ACTION)
                            // Puts the status into the Intent
                            .putExtra(EXTENDED_DATA_STATUS, i);
            // Broadcasts the Intent to receivers in this app.
            Log.i("Message @@@@ = ", "no."+i);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        }
    }
}

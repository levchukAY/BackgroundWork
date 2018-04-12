package com.artiomlevchuk.backgroundwork.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.artiomlevchuk.backgroundwork.R;
import com.artiomlevchuk.backgroundwork.service.MessengerBoundService;

public class MessengerBoundServiceActivity extends AppCompatActivity {

    /** Messenger for communicating with the service. */
    Messenger mService = null;

    boolean mBound;

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessengerBoundService.MSG_REPLY_HI:
                    Toast.makeText(getApplicationContext(), "Hi, Client!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_bound_service);

        findViewById(R.id.button_bind).setOnClickListener(view -> {
            bindService(new Intent(this, MessengerBoundService.class), mConnection,
                    Context.BIND_AUTO_CREATE);
        });

        findViewById(R.id.button_send).setOnClickListener(view -> {
            if (!mBound) return;
            // Create and send a message to the service, using a supported 'what' value
            Message msg = Message.obtain(null, MessengerBoundService.MSG_SAY_HELLO, 0, 0);
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.button_unbind).setOnClickListener(view -> {
            if (mBound) {
                try {
                    Message msg = Message.obtain(null,
                            MessengerBoundService.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service
                    // has crashed.
                }
                unbindService(mConnection);
                mBound = false;
            }
        });
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            mService = new Messenger(service);
            mBound = true;

            try {
                Message msg = Message.obtain(null, MessengerBoundService.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
        }
    };
}

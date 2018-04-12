package com.artiomlevchuk.backgroundwork.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import java.util.ArrayList;

public class MessengerBoundService extends Service {

    /** Command to the service to display a message */
    public static final int MSG_SAY_HELLO = 1;
    public static final int MSG_REPLY_HI = 2;

    public static final int MSG_REGISTER_CLIENT = 1001;
    public static final int MSG_UNREGISTER_CLIENT = 1002;

    ArrayList<Messenger> mClients = new ArrayList<>();

    public MessengerBoundService() { }

    /**
     * Handler of incoming messages from clients.
     * Служба реализует объект Handler, который получает
     * обратный вызов для каждого вызова от клиента.
     * Служба получает каждый объект Message в своем объекте Handler —
     * в частности, в методе handleMessage().*/
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    for (Messenger client : mClients) {
                        try {
                            client.send(Message.obtain(null,
                                    MSG_REPLY_HI, 0, 0));
                        } catch (RemoteException e) {
                            // The client is dead.  Remove it from the list;
                            // we are going through the list from back to front
                            // so this is safe to do inside the loop.
                            mClients.remove(client);
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Hello, Service!", Toast.LENGTH_SHORT).show();
                    break;
                case MSG_REGISTER_CLIENT:
                    mClients.add(msg.replyTo);
                    break;
                case MSG_UNREGISTER_CLIENT:
                    mClients.remove(msg.replyTo);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     * Объект Handler используется для создания объекта Messenger (
     * который является ссылкой на объект Handler).
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     * Объект Messenger создает объект IBinder, который служба возвращает клиентам из метода onBind().
     * Клиенты используют полученный объект IBinder для создания экземпляра объекта Messenger (который
     * ссылается на объект Handler службы), используемого клиентом для отправки объектов Message в службу.
     */
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }
}

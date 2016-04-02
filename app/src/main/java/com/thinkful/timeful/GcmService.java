package com.thinkful.timeful;


import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;


/**
 * Service used for receiving GCM messages. When a message is received this service will log it.
 */
public class GcmService extends GcmListenerService {

    private LoggingService.Logger logger;

    public GcmService() {
        logger = new LoggingService.Logger(this);
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        sendNotification("Received GCM Message: " + data.toString());
    }

    @Override
    public void onDeletedMessages() {
        sendNotification("Deleted messages on server");
    }

    @Override
    public void onMessageSent(String msgId) {
        sendNotification("Upstream message sent. Id=" + msgId);
    }

    @Override
    public void onSendError(String msgId, String error) {
        sendNotification("Upstream message send error. Id=" + msgId + ", error" + error);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
        logger.log(Log.INFO, msg);
    }
}

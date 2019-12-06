package com.debug.messagereceiver.smsReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.debug.messagereceiver.MainActivity;
import com.debug.messagereceiver.PaytmMessageParser;
import com.debug.messagereceiver.Transaction;

import java.util.UUID;

public class MySMSReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = MySMSReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intent Received: " + intent.getAction());
        if (intent.getAction().equals(SMS_RECEIVED)) {
            StringBuilder stringBuilder = new StringBuilder();

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] myPdu = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[myPdu.length];

                String phoneNo="";
                String message="";
                String text="";
                for (int i = 0; i < myPdu.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[]) myPdu[i], format);
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) myPdu[i]);
                    }
                    message = messages[i].getMessageBody();
                    phoneNo = messages[i].getOriginatingAddress();
                    text = "Message: " + message + " " + "From: " + phoneNo + "\n";
                    stringBuilder.append(text);
                }

                Transaction transaction = PaytmMessageParser.parsePaytmMessage(message,phoneNo);
                transaction.setTransactionId(UUID.randomUUID().toString());
                transaction.setCategory("Others");
                MainActivity.myAppDatabase.myDao().addUser(transaction);
                Toast.makeText(context, stringBuilder, Toast.LENGTH_LONG).show();

            }
        }
    }
}

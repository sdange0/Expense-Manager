package com.debug.messagereceiver;

import android.widget.Switch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PaytmMessageParser {

    public static Transaction parseMessage(String message, String source){

        switch(source){
            case "VMAxisBK" :
                return parseAxisMessage(message,source);

            case "AXiPaytm":
                return parsePaytmMessage(message,source);
        }
        return null;
    }

    public static Transaction parsePaytmMessage(String message,String source){
        Transaction transaction = new Transaction();

        if(message.startsWith("Paid")){

            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(message);
            m.find();
            transaction.setAmount(m.group());

            int beni_start = message.indexOf("to");
            int beni_end = message.indexOf(" on");
            transaction.setBeneficiary(message.substring(beni_start+3,beni_end));

            DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date date = new Date();
            transaction.setTransactionDate(sdf.format(date));

            transaction.setTransaction_source(source);

            return transaction;
        }
        return null;
    }

    public static Transaction parseAxisMessage(String message,String source){

        Transaction transaction = new Transaction();
        int amount_index = message.indexOf("Rs. ");
        int amount_index_end = message.indexOf(" on");
        transaction.setAmount(message.substring(amount_index+3,amount_index_end));

        int beni_start = message.indexOf("Info: ");
        int beni_end = message.substring(beni_start).indexOf(".");
        transaction.setBeneficiary( message.substring(beni_start+6,beni_start+beni_end));

        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        transaction.setTransactionDate(sdf.format(date));
        //transaction.setBeneficiary("other");
        transaction.setTransaction_source(source);

        return transaction;
    }
}

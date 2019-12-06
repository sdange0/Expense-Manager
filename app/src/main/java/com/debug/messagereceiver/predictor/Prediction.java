package com.debug.messagereceiver.predictor;

import android.os.Build;

import androidx.annotation.RequiresApi;
import com.debug.messagereceiver.Transaction;
import com.debug.messagereceiver.dao.Festival;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Prediction {

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    public static int calculateUsageDuration(List<Transaction> transactions , String date2) throws ParseException {

        String date1 = transactions.get(0).getTransactionDate();         //first transaction
        int months=0;
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();
        date2=dtf.format(now);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = format.parse(date1);
        Date d2 = format.parse(date2);

        //in milliseconds
        long diff = d2.getTime() - d1.getTime();

        long diffDays = diff / (24 * 60 * 60 * 1000);
        months= (int) ((diffDays/30)+1);
        return months;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int[] previousCategoryAmountTotal(List<Transaction> transactions, List<Integer> currentsum) throws ParseException {

        String date2 = new String();
        //int[] currentsum = new int[8];
        int[] sum = new int[8];

        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();
        date2=dtf.format(now);
        String mon=date2.substring(0, 2);         //current date
        String year=date2.substring(6,10);

        for (Transaction transaction : transactions){

            if(transaction.getCategory().equals("Food"))
            {
                if(mon.equals(transaction.getTransactionDate().substring(0, 2)) &&
                        year.equals(transaction.getTransactionDate().substring(6, 10)) ) {
                    currentsum.add(0,currentsum.get(0)+ Integer.parseInt(transaction.getAmount()));
                }
                else {
                    sum[0]=sum[0]+ Integer.parseInt(transaction.getAmount());
                }
            }
            else if(transaction.getCategory().equals("Bills"))
            {
                if(mon.equals(transaction.getTransactionDate().substring(0, 2)) &&
                        year.equals(transaction.getTransactionDate().substring(6, 10)) ) {
                    currentsum.add(1,currentsum.get(0)+ Integer.parseInt(transaction.getAmount()));
                }
                else {
                    sum[1]=sum[1]+ Integer.parseInt(transaction.getAmount());
                }
            }
            else if(transaction.getCategory().equals("EMI"))
            {
                if(mon.equals(transaction.getTransactionDate().substring(0, 2)) &&
                        year.equals(transaction.getTransactionDate().substring(6, 10)) ) {
                    currentsum.add(2,currentsum.get(0)+ Integer.parseInt(transaction.getAmount()));
                }
                else {
                    sum[2]=sum[2]+ Integer.parseInt(transaction.getAmount());
                }
            }
            else if(transaction.getCategory().equals("Entertainment"))
            {
                if(mon.equals(transaction.getTransactionDate().substring(0, 2)) &&
                        year.equals(transaction.getTransactionDate().substring(6, 10)) ) {
                    currentsum.add(3,currentsum.get(0)+ Integer.parseInt(transaction.getAmount()));
                }
                else {
                    sum[3]=sum[3]+ Integer.parseInt(transaction.getAmount());
                }
            }
            else if(transaction.getCategory().equals("Fuel"))
            {
                if(mon.equals(transaction.getTransactionDate().substring(0, 2)) &&
                        year.equals(transaction.getTransactionDate().substring(6, 10)) ) {
                    currentsum.add(4,currentsum.get(0)+ Integer.parseInt(transaction.getAmount()));
                }
                else {
                    sum[4]=sum[4]+ Integer.parseInt(transaction.getAmount());
                }
            }
            else if(transaction.getCategory().equals("Health"))
            {
                if(mon.equals(transaction.getTransactionDate().substring(0, 2)) &&
                        year.equals(transaction.getTransactionDate().substring(6, 10)) ) {
                    currentsum.add(5,currentsum.get(0)+ Integer.parseInt(transaction.getAmount()));
                }
                else {
                    sum[5]=sum[5]+ Integer.parseInt(transaction.getAmount());
                }
            }
            else if(transaction.getCategory().equals("Travel"))
            {
                if(mon.equals(transaction.getTransactionDate().substring(0, 2)) &&
                        year.equals(transaction.getTransactionDate().substring(6, 10)) ) {
                    currentsum.add(6,currentsum.get(0)+ Integer.parseInt(transaction.getAmount()));
                }
                else {
                    sum[6]=sum[6]+ Integer.parseInt(transaction.getAmount());
                }
            }
            else
            {
                if(mon.equals(transaction.getTransactionDate().substring(0, 2)) &&
                        year.equals(transaction.getTransactionDate().substring(6, 10)) ) {
                    currentsum.add(7,currentsum.get(0)+ Integer.parseInt(transaction.getAmount()));
                }
                else {
                    sum[7]=sum[7]+ Integer.parseInt(transaction.getAmount());
                }
            }

        }
        return sum;
    }

    public static void addFestival(List<Festival> fest){
        fest.add(new Festival("Diwali","12"));
        fest.add(new Festival("Birthday","12"));
    }

    public static Boolean isSpecialEventInMonth(List<Festival> festivals , String currentMonth , String currentYear){

        Festival festival;
        for(int i=0;i<festivals.size();i++)
        {
            festival=festivals.get(i);
            if(currentMonth.equals(festival.month)) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getNotification(List<Transaction>transactions) throws ParseException {

        List<Integer> currentsum= new ArrayList<>(8);
        String currentMonth="";
        String currentYear="";
        int[] sum=new int[8];
        List festivals = new ArrayList();
        Boolean flag;
        int budget=10000;
        int exceed = 0;
        int months=0;
        String date2;
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();
        date2=dtf.format(now);
        currentMonth = date2.substring(0,2);
        months = calculateUsageDuration(transactions,date2);

        sum = previousCategoryAmountTotal(transactions,currentsum);
        addFestival(festivals);
        for(int i=0;i<8;i++)
        {
            if((i==0) && isSpecialEventInMonth(festivals,currentMonth,currentYear ))
                sum[i]=sum[i]*2;
            sum[i]=sum[i]/months;
            System.out.println("average spending is"+sum[i]+" current spending is"+currentsum.get(i));
            if(sum[i]>currentsum.get(i))
                System.out.println("You can spend "+(sum[i]-currentsum.get(i))+"more");
            else
                System.out.println("You are spending more");
            exceed=exceed+currentsum.get(i);
            if(exceed>budget)
                System.out.println("your budget has exceded");
        }

    }*/


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<String> getNotification(List<Transaction> transactions){
        List<Festival> fest= new ArrayList<Festival>();
        //List<Pending> bill=new ArrayList<Pending>();
        fest.add(new Festival("Diwali","12"));
        fest.add(new Festival("Birthday","12"));
        long budget=13000;
        long[] sum;
        long count;
        sum = new long[12];
        long[] currentsum;
        currentsum = new long[12];

        //Item dt=user.get(0);
        String date1=transactions.get(0).getTransactionDate();         //first transaction
        //dt=user.get(user.size()-1);
        //String date2=dt.date;         //last transaction
        String date2;
        long months=0;
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime now=LocalDateTime.now();
        date2=dtf.format(now);
        // System.out.println(dtf.format(now));
        System.out.println(date2);
        String mon=date2.substring(0, 2);         //current date
        String year=date2.substring(6,10);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(date1);
            d2 = format.parse(date2);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            //long diffSeconds = diff / 1000 % 60;
            //long diffMinutes = diff / (60 * 1000) % 60;
            //long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            months=(diffDays/30)+1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < transactions.size(); i++)
        {
            //Item data = user.get(i);
            //System.out.println(data.amount+" "+data.category+" "+data.date);
            if(transactions.get(i).getCategory().equals("Food"))
            {
                if(mon.equals(transactions.get(i).getTransactionDate().substring(0, 2)) && year.equals(transactions.get(i).getTransactionDate().substring(6, 10)) )
                {
                    currentsum[0]=currentsum[0]+Integer.parseInt(transactions.get(i).getAmount());
                }
                else
                {
                    sum[0]=sum[0]+Integer.parseInt(transactions.get(i).getAmount());
                }
            }
            else if(transactions.get(i).getCategory().equals("Bills"))
            {
                if(mon.equals(transactions.get(i).getTransactionDate().substring(0, 2)) && year.equals(transactions.get(i).getTransactionDate().substring(6, 10)) )
                {
                    currentsum[1]=currentsum[1]+Integer.parseInt(transactions.get(i).getAmount());
                }
                else
                {
                    sum[1]=sum[1]+Integer.parseInt(transactions.get(i).getAmount());
                }
            }
            else if(transactions.get(i).getCategory().equals("EMI"))
            {
                if(mon.equals(transactions.get(i).getTransactionDate().substring(0, 2)) && year.equals(transactions.get(i).getTransactionDate().substring(6, 10)) )
                {
                    currentsum[2]=currentsum[2]+Integer.parseInt(transactions.get(i).getAmount());
                }
                else
                {
                    sum[2]=sum[2]+Integer.parseInt(transactions.get(i).getAmount());
                }
            }
            else if(transactions.get(i).getCategory().equals("Entertainment"))
            {
                if(mon.equals(transactions.get(i).getTransactionDate().substring(0, 2)) && year.equals(transactions.get(i).getTransactionDate().substring(6, 10)) )
                {
                    currentsum[3]=currentsum[3]+Integer.parseInt(transactions.get(i).getAmount());
                }
                else
                {
                    sum[3]=sum[3]+Integer.parseInt(transactions.get(i).getAmount());
                }
            }
            else if(transactions.get(i).getCategory().equals("Fuel")) {
                if (mon.equals(transactions.get(i).getTransactionDate().substring(0, 2)) && year.equals(transactions.get(i).getTransactionDate().substring(6, 10))) {
                    currentsum[4] = currentsum[4] + Integer.parseInt(transactions.get(i).getAmount());
                } else {
                    sum[4] = sum[4] + Integer.parseInt(transactions.get(i).getAmount());
                }
            }
            else if(transactions.get(i).getCategory().equals("Health"))
            {
                if(mon.equals(transactions.get(i).getTransactionDate().substring(0, 2)) && year.equals(transactions.get(i).getTransactionDate().substring(6, 10)) )
                {
                    currentsum[5]=currentsum[5]+Integer.parseInt(transactions.get(i).getAmount());
                }
                else
                {
                    sum[5]=sum[5]+Integer.parseInt(transactions.get(i).getAmount());
                }
            }
            else if(transactions.get(i).getCategory().equals("Travel"))
            {
                if(mon.equals(transactions.get(i).getTransactionDate().substring(0, 2)) && year.equals(transactions.get(i).getTransactionDate().substring(6, 10)) )
                {
                    currentsum[6]=currentsum[6]+Integer.parseInt(transactions.get(i).getAmount());
                }
                else
                {
                    sum[6]=sum[6]+Integer.parseInt(transactions.get(i).getAmount());
                }
            }
            else
            {
                if(mon.equals(transactions.get(i).getTransactionDate().substring(0, 2)) && year.equals(transactions.get(i).getTransactionDate().substring(6, 10)) )
                {
                    currentsum[7]=currentsum[7]+Integer.parseInt(transactions.get(i).getAmount());
                }
                else
                {
                    sum[7]=sum[7]+Integer.parseInt(transactions.get(i).getAmount());
                }
            }

        }
        int flag=0;
        for(int i=0;i<fest.size();i++)
        {
            Festival info=fest.get(i);
            if(mon.equals(info.getMonth()))
            {
                flag=1;
                break;
            }
        }
        long exceed=0;

        List<String> suggetions = new ArrayList<>();
        for(int i=0;i<8;i++)
        {
            if((i==0) && flag==1 )
                sum[i]=sum[i]*2;
            sum[i]=sum[i]/months;
            //System.out.println("average spending is"+sum[i]+" current spending is"+currentsum[i]);
            if(sum[i]>currentsum[i])
            {
                if(i==0)
                suggetions.add("You can spend "+(sum[i]-currentsum[i])+"more on Food");
                else if(i==1)
                    suggetions.add("You can spend "+(sum[i]-currentsum[i])+"more on Bill");
                else if(i==2)
                    suggetions.add("You can spend "+(sum[i]-currentsum[i])+"more on EMI");
                else if(i==3)
                    suggetions.add("You can spend "+(sum[i]-currentsum[i])+"more on Entertainment");
                else if(i==4)
                    suggetions.add("You can spend "+(sum[i]-currentsum[i])+"more on Fuel");
                else if(i==5)
                    suggetions.add("You can spend "+(sum[i]-currentsum[i])+"more on Health");
                else if(i==6)
                    suggetions.add("You can spend "+(sum[i]-currentsum[i])+"more on Travelling");
                else
                    suggetions.add("You can spend "+(sum[i]-currentsum[i])+"more on Other category");
                //System.out.println("You can spend "+(sum[i]-currentsum[i])+"more");
            }
            else {
                if(i==0)
                suggetions.add("You are spending more on Food");
                else if(i==1)
                    suggetions.add("You are spending more on Bills");
                else if(i==2)
                    suggetions.add("You are spending more on EMI");
                else if(i==3)
                    suggetions.add("You are spending more on Entertainment");
                else if(i==4)
                    suggetions.add("You are spending more on Fuel");
                else if(i==5)
                    suggetions.add("You are spending more on Health");
                else if(i==6)
                    suggetions.add("You are spending more on Travelling");
                else
                    suggetions.add("You are spending more on Other Category");

            }
                //System.out.println("You are spending more");
            exceed=exceed+currentsum[i];
            if(exceed>budget)
                suggetions.add("your budget has exceded");
                //System.out.println("your budget has exceded");

        }
        if(exceed<budget)
            suggetions.add("You left with total Rs. "+(budget-exceed));
            //System.out.println("You are saving Rs"+(budget-exceed));
        return suggetions;
    }
}

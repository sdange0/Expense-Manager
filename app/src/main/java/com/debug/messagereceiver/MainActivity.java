package com.debug.messagereceiver;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.debug.messagereceiver.History.ColumnChart;
import com.debug.messagereceiver.History.History;
import com.debug.messagereceiver.History.PieChart;
import com.debug.messagereceiver.RecyclerView.RecyclerViewAdapter;
import com.debug.messagereceiver.RecyclerView.SuggetionAdapter;
import com.debug.messagereceiver.database.MyAppDatabase;
import com.debug.messagereceiver.predictor.Prediction;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements Serializable{

    private static final int REQ_CODE_MESSAGE = 101;
    public static MyAppDatabase myAppDatabase;
    private Button newwindow,closePopupBtn,signoutt,openmonth1,opencat1;
    private LinearLayout linearLayout1;
    PopupWindow popupWindow;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        mAuth = FirebaseAuth.getInstance();
        myAppDatabase = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"userdb")
                .allowMainThreadQueries().build();


        // Check for permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, REQ_CODE_MESSAGE);
            }
        }

        Button button = (Button) findViewById(R.id.button);
        Button history = (Button) findViewById(R.id.button2);
        Button assign = (Button) findViewById(R.id.assign);
        Button suggetions = (Button) findViewById(R.id.suggetions);
        signoutt=(Button) findViewById(R.id.signout);


       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Transactions.class);
                startActivity(intent);
            }

        });
*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup,null);

                closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);
                openmonth1 = (Button) customView.findViewById(R.id.openmonth);
                opencat1 = (Button) customView.findViewById(R.id.opencat);
                //instantiate popup window
                popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                    }
                });
              openmonth1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MainActivity.this, PieChart.class);
                        startActivity(intent);
                    }
                });
                opencat1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       Intent intent = new Intent(MainActivity.this, ColumnChart.class);
                        startActivity(intent);
                    }
                });

            }
        });

        signoutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                mAuth.signOut();
                Intent activity = new Intent(getApplicationContext(),loginactivity.class);
                activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(activity);
            }
        });


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, History.class);
                startActivity(intent);

            }
        });

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AssignCategories.class);
                startActivity(intent);
            }
        });

        suggetions.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Suggest.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_MESSAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please grant permission", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void show(List<Transaction> transactions){
        Toast.makeText(this,transactions.toString(),Toast.LENGTH_LONG);
    }
}

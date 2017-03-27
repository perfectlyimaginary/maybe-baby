package com.mytrendin.searchsms;

import android.Manifest;
import android.app.LauncherActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.BoolRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText number;
    Button add_btn;
    RecyclerView rvList;
    Boolean permission = false;
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        number = (EditText)findViewById(R.id.number);
        add_btn = (Button)findViewById(R.id.btn_add);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    permission = checkContactsPermission();
                    if (permission) {
                        List<ListItem> list= new ArrayList();
                        ListItem listItem;
                        final String num = number.getText().toString();
                        Uri uriSms = Uri.parse("content://sms/inbox");
                        final Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"}, null, null, null);

                        while (cursor.moveToNext()) {
                            String address = cursor.getString(1);
                            if (address.equals(num)) {
                                String msg = cursor.getString(3);
                                listItem = new ListItem();
                                listItem.setNumber(address);
                                listItem.setMessage(msg);
                                list.add(listItem);

                            }

                        }
                        ListAdapter listAdapter = new ListAdapter(list, getBaseContext());
                        rvList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        rvList.setAdapter(listAdapter);
                    }

                }
            }
        });

        }
    public boolean checkContactsPermission() {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.READ_SMS)) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{android.Manifest.permission.READ_SMS},
                                MY_PERMISSIONS_REQUEST_READ_SMS);
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{android.Manifest.permission.READ_SMS},
                                MY_PERMISSIONS_REQUEST_READ_SMS);
                    }
                    return false;
                } else {
                    return true;
                }

            }
            @Override
            public void onRequestPermissionsResult(int requestCode,
                                                   String permissions[], int[] grantResults) {
                switch (requestCode) {
                    case MY_PERMISSIONS_REQUEST_READ_SMS: {
                        if (grantResults.length > 0
                                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            if (ContextCompat.checkSelfPermission(this,
                                    android.Manifest.permission.READ_SMS)
                                    == PackageManager.PERMISSION_GRANTED) {


                                return;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "permission denied",
                                    Toast.LENGTH_LONG).show();
                        }
                        return;
                    }
                }
            }




}
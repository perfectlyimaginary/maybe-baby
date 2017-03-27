package com.example.shree.mymap;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText number;
    Button add_btn;
    ListView lViewSMS;

    final ArrayList  sms = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lViewSMS = (ListView) findViewById(R.id.listViewSMS);
        number = (EditText)findViewById(R.id.number);
        add_btn = (Button)findViewById(R.id.btn_add);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sms.clear();
                final String num = number.getText().toString();
                Uri uriSms = Uri.parse("content://sms/inbox");
                final Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);

               // cursor.moveToFirst();
                while  (cursor.moveToNext())
                {
                    String address = cursor.getString(1);
                    //Toast.makeText(getApplicationContext(),"Add: "+address+"\nnum: "+num,Toast.LENGTH_SHORT).show();
                    if(address.equals(num))
                    {
                        String body = cursor.getString(3);

                        sms.add("Address= " + address + "\n SMS = " + body);

                    //    Toast.makeText(getApplicationContext(),"i am in ",Toast.LENGTH_SHORT).show();
                    }

                }

                 ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1,sms);
                lViewSMS.setAdapter(adapter);
            }
        });




    }

}
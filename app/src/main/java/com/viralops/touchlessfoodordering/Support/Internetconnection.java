package com.viralops.touchlessfoodordering.Support;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.viralops.touchlessfoodordering.MainActivity;
import com.viralops.touchlessfoodordering.R;


public class Internetconnection extends AppCompatActivity {

TextView home;
     String value="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet_page);
        final String value=getIntent().getStringExtra("openvalue");

        home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("openvalue",value);
                startActivity(intent);
              finish();
            }
        });
      /*  IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);
*/
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("openvalue",value);
        startActivity(intent);
        finish();
    }
}

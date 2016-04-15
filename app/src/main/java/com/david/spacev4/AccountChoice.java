package com.david.spacev4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.david.spacev4.AdminLogin.Login;
import com.david.spacev4.UserLogin.UserLoginScreen;

public class AccountChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account_choice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View view) {
        Intent newActivity = null;
        switch (view.getId()) {    //depending on the button pressed the correct activity is launched
            case R.id.AdminButton:
                Intent intent1 = new Intent(getBaseContext(), Login.class);
                startActivity(intent1);
                break;
            case R.id.UserButton:
                Intent intent2 = new Intent(getBaseContext(), UserLoginScreen.class);
                startActivity(intent2);
                break;

        }
        if (newActivity != null) startActivity(newActivity);
    }
}

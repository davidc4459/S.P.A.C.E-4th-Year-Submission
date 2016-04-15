package com.david.spacev4.AdminLogin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.david.spacev4.R;
import com.example.david.myapplication.backend.adminLoginApi.AdminLoginApi;
import com.example.david.myapplication.backend.adminLoginApi.model.AdminLogin;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class CreateAccount extends AppCompatActivity {
    String Username;
    TextView Login;
    String Password;
    TextView LoginPassword;
    String EncryptedPassword;
    String PlainTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    public void onClick(View view) {
        Intent newActivity = null;
        switch (view.getId()) {    //depending on the button pressed the correct activity is launched
            case R.id.createbtn:
                this.Login = (TextView) findViewById(R.id.usernametext);
                Username = Login.getText().toString();
                new AllUsersAsyncTask(this).execute();
                break;
            case R.id.cancelbtn:
                Intent intent2 = new Intent(getBaseContext(), com.david.spacev4.AdminLogin.Login.class);
                startActivity(intent2);
                finish();
                break;
        }
        if (newActivity != null) startActivity(newActivity);
    }
    private void CreateAccount()
    {
        this.Login = (TextView) findViewById(R.id.usernametext);
        Username = Login.getText().toString();
        this.LoginPassword = (TextView) findViewById(R.id.passwordtext);
        Password = LoginPassword.getText().toString();


        try {
            DESKeySpec keySpec = new DESKeySpec(
                    "sdh5jHD3hwe49F8Erh".getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            EncryptedPassword = Base64.encodeToString(cipher.doFinal(Password
                    .getBytes("UTF-8")), Base64.DEFAULT);

        }
        catch(Exception e){}

        if (Username.matches("") || Password.matches("")){}
        else {
            AdminLogin account = new AdminLogin();
            account.setAdminID(Username);
            account.setAdminPasword(EncryptedPassword);
            new uploadAsyncTask().execute(new Pair<Context, AdminLogin>(this, account));
            Intent intent = new Intent(getBaseContext(), com.david.spacev4.AdminLogin.Login.class);
            startActivity(intent);
            finish();
        }
    }
    private void AccountConflict() {
        this.Login = (TextView) findViewById(R.id.usernametext);
        Login.setText("");
        Toast.makeText(getApplicationContext(),
                "User Name already exists!", Toast.LENGTH_LONG).show();
        EditText userName = (EditText)findViewById(R.id.usernametext);
        userName.setError("username already exists!");
    }

    class AllUsersAsyncTask extends AsyncTask<Void, Void, List<AdminLogin>> {
        private AdminLoginApi myApiService = null;
        private Context context;
        private final static String TAG = "EndpointAsyncTask";
        private TextView allNotesText;
        String allNotes = "";
        int error = 0;
        public AllUsersAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<AdminLogin> doInBackground(Void... params) {
            if (myApiService == null) { // Only do this once
                Log.d(TAG, ">>>>>>>>>>>>>>>>>creating connection");
                AdminLoginApi.Builder builder = new AdminLoginApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://space-1092.appspot.com/_ah/api/");
                myApiService = builder.build();
            }

            try {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>Trying to execute");
                return myApiService.list().execute().getItems();
            } catch (IOException e) {
                return Collections.EMPTY_LIST;
            }
        }

        @Override
        protected void onPostExecute(List<AdminLogin> result)
        {
            for (AdminLogin q : result) {
                if (q.getAdminID()== null ){}
                else {
                    if (q.getAdminID().equals(Username)) {
                        Log.d(TAG, ">>>>>>>>>>>>>>>>> conflict in usernames");
                        error = 1;
                    }
                }
            }
            if (error == 0)
            {
                CreateAccount();
            }
            else
            {
                AccountConflict();
            }
        }
    }

    class uploadAsyncTask extends AsyncTask<Pair<Context,AdminLogin>, Void, AdminLogin> {
        private AdminLoginApi myApiService = null;
        private Context context;

        @Override
        protected AdminLogin doInBackground(Pair<Context, AdminLogin>... params) {
            if(myApiService == null) {  // Only do this once
                AdminLoginApi.Builder builder = new AdminLoginApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://space-1092.appspot.com/_ah/api/");
                // end options for devappserver
                myApiService = builder.build();
            }

            context = params[0].first;
            AdminLogin account = params[0].second;


            try {
                myApiService.insert(account).execute().getAdminID();
                return account;
            } catch (IOException e) {
                account.setAdminID(e.getMessage());
                return account;
            }
        }
        @Override
        protected void onPostExecute(AdminLogin result) {
        }
    }

}

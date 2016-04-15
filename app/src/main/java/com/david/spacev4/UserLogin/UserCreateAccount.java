package com.david.spacev4.UserLogin;

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
import com.example.david.myapplication.backend.userLoginApi.UserLoginApi;
import com.example.david.myapplication.backend.userLoginApi.model.UserLogin;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class UserCreateAccount extends AppCompatActivity {
    String Username;
    TextView Login;
    String EncryptedUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    public void onClick(View view) {
        Intent newActivity = null;
        switch (view.getId()) {    //depending on the button pressed the correct activity is launched
            case R.id.createbtn:
                this.Login = (TextView) findViewById(R.id.usernametext);
                Username = Login.getText().toString();

                try {
                    DESKeySpec keySpec = new DESKeySpec(
                            "sdh5jHD3hwe49F8Erh".getBytes("UTF-8"));
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                    SecretKey key = keyFactory.generateSecret(keySpec);

                    Cipher cipher = Cipher.getInstance("DES");
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    EncryptedUserName = Base64.encodeToString(cipher.doFinal(Username
                            .getBytes("UTF-8")), Base64.DEFAULT);

                }
                catch(Exception e){}
                new AllUsersAsyncTask(this).execute();
                break;
            case R.id.cancelbtn:
                Intent intent2 = new Intent(getBaseContext(), UserLoginScreen.class);
                startActivity(intent2);
                finish();
                break;
        }
        if (newActivity != null) startActivity(newActivity);
    }
    private void CreateAccount()
    {

        if (Username.matches("") ){}
        else {
            UserLogin account = new UserLogin();
            account.setUserID(EncryptedUserName);

            Log.d("Upload ", ">>>>>>>>>>>>>>>>> trying to send" + EncryptedUserName);
            new uploadAsyncTask().execute(new Pair<Context, UserLogin>(this, account));

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

    class AllUsersAsyncTask extends AsyncTask<Void, Void, List<UserLogin>> {
        private UserLoginApi myApiService = null;
        private Context context;
        private final static String TAG = "EndpointAsyncTask";
        private TextView allNotesText;
        String allNotes = "";
        int error = 0;
        public AllUsersAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<UserLogin> doInBackground(Void... params) {
            if (myApiService == null) { // Only do this once
                Log.d(TAG, ">>>>>>>>>>>>>>>>>creating connection");
                UserLoginApi.Builder builder = new UserLoginApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
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
        protected void onPostExecute(List<UserLogin> result)
        {
            for (UserLogin q : result) {
                if (q.getUserID()== null ){}
                else {
                    if (q.getUserID().equals(Username)) {
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

    class uploadAsyncTask extends AsyncTask<Pair<Context,UserLogin>, Void, UserLogin> {
        private UserLoginApi myApiService = null;
        private Context context;

        @Override
        protected UserLogin doInBackground(Pair<Context, UserLogin>... params) {
            if(myApiService == null) {  // Only do this once
                UserLoginApi.Builder builder = new UserLoginApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://space-1092.appspot.com/_ah/api/");
                // end options for devappserver
                myApiService = builder.build();
            }

            context = params[0].first;
            UserLogin account = params[0].second;


            try {
                Log.d("Upload ", ">>>>>>>>>>>>>>>>> trying to send");
                myApiService.insert(account).execute().getUserID();
                return account;
            } catch (IOException e) {
                Log.d("Upload ", ">>>>>>>>>>>>>>>>> failed to send");
                account.setUserID(e.getMessage());
                return account;
            }
        }
        @Override
        protected void onPostExecute(UserLogin result) {
            if (result.getUserID().equals(EncryptedUserName))
            {
                Intent intent = new Intent(getBaseContext(), com.david.spacev4.UserLogin.UserLoginScreen.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Login = (TextView) findViewById(R.id.usernametext);
                Login.setText(result.getUserID());
            }
        }
    }

}
package com.david.spacev4.AdminLogin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.david.spacev4.AdminPages.CreateYouthClub;
import com.david.spacev4.AdminPages.adminHomePage;
import com.david.spacev4.R;
import com.example.david.myapplication.backend.adminLoginApi.AdminLoginApi;
import com.example.david.myapplication.backend.youthClubApi.YouthClubApi;
import com.example.david.myapplication.backend.youthClubApi.model.YouthClub;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Login extends AppCompatActivity {
    public static String Username;
    TextView Login;
    String Password;
    TextView LoginPassword;
    String PlainTextPassword = "";
    int ClubCount;
    Context context;

    public static final List<String> YOUTH_CLUB_LIST = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void onClick(View view) {
        Intent newActivity = null;
        switch (view.getId()) {    //depending on the button pressed the correct activity is launched
            case R.id.Login:

                HandleLoginButtonClick();

                break;
            case R.id.CreateAccountButton:
                Intent intent4 = new Intent(getBaseContext(), CreateAccount.class);
                startActivity(intent4);
                finish();
                break;
        }
        if (newActivity != null) startActivity(newActivity);
    }
    private void HandleLoginButtonClick() {
        this.Login = (TextView) findViewById(R.id.usernametext);
        Username = Login.getText().toString();
        this.LoginPassword = (TextView) findViewById(R.id.passwordtext);
        Password = LoginPassword.getText().toString();
        if (Username.matches("")  ||  Password .matches(""))
        {

        }
        else
        {
            new CheckPasswordAsyncTask().execute("Username");

        }
    }
    class CheckPasswordAsyncTask extends AsyncTask<String, Void, String> {
        private AdminLoginApi myApiService = null;
        private Context context;
        private final static String TAG = "EndpointAsyncTask";
        private TextView allNotesText;
        String allNotes = "";

        public CheckPasswordAsyncTask() {
        }
        @Override
        protected String doInBackground(String... params) {
            if (myApiService == null)
            { // Only do this once
                Log.d(TAG, ">>>>>>>>>>>>>>>>>creating connection");
                AdminLoginApi.Builder builder = new AdminLoginApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://space-1092.appspot.com/_ah/api/");
                myApiService = builder.build();
            }

            try
            { Log.d(TAG, ">>>>>>>>>>>>>>>>>Trying to execute");
                return myApiService.get(Username).execute().getAdminPasword();
            }
            catch (IOException e) {
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            if (result.matches("error")){
                Toast.makeText(getApplicationContext(),
                        "User Name did not exist!", Toast.LENGTH_LONG).show();
                EditText userName = (EditText) findViewById(R.id.usernametext);
                userName.setError("User Name did not exist!");
            }
            else {
                try {
                    DESKeySpec keySpec = new DESKeySpec(
                            "sdh5jHD3hwe49F8Erh".getBytes("UTF-8"));
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                    SecretKey key = keyFactory.generateSecret(keySpec);

                    byte[] encryptedWithoutB64 = Base64.decode(result, Base64.DEFAULT);
                    Cipher cipher = Cipher.getInstance("DES");
                    cipher.init(Cipher.DECRYPT_MODE, key);
                    byte[] plainTextPwdBytes = cipher.doFinal(encryptedWithoutB64);
                    PlainTextPassword = new String(plainTextPwdBytes);
                } catch (Exception e) {
                }
                if (PlainTextPassword.equals(Password)) {
                    new CheckYouthClubAsyncTask(context).execute();

                } else {

                    Toast.makeText(getApplicationContext(),
                            "Wrong Password!", Toast.LENGTH_LONG).show();
                    EditText password = (EditText) findViewById(R.id.passwordtext);
                    password.setError("Password did not match!");

                }
            }
        }
    }
    class CheckYouthClubAsyncTask extends AsyncTask<Void, Void, List<YouthClub>> {
        private YouthClubApi myApiService = null;
        private Context context;
        private final static String TAG = "EndpointAsyncTask";
        private TextView allNotesText;
        String allNotes = "";

        public CheckYouthClubAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<YouthClub> doInBackground(Void... params) {
            if (myApiService == null) { // Only do this once
                Log.d(TAG, ">>>>>>>>>>>>>>>>>creating connection");
                YouthClubApi.Builder builder = new YouthClubApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://space-1092.appspot.com/_ah/api/");

                myApiService = builder.build();
            }

            try {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>Trying to execute");
                return myApiService.list().execute().getItems();
            } catch (IOException e) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>Failed to execute");
                return Collections.EMPTY_LIST;
            }
        }

        @Override
        protected void onPostExecute(List<YouthClub> result) {
            int i = result.size() - 1;
            ClubCount = 0;
            YOUTH_CLUB_LIST.clear();
            while (i >= 0){
                Log.d(TAG, ">>>>>>>>>>>>>>>>>filling list");
                YouthClub q = result.get(i);
                if (q.getAdminName().matches(Username)) {
                    Log.d(TAG,q.getAdminName());
                    Log.d(TAG,Username);
                    ClubCount ++;
                    YOUTH_CLUB_LIST.add(q.getYouthClub());
                }
                i--;
            }
            Log.d(TAG, ""+ ClubCount);
            Log.d(TAG, ">>>>>>>>>>>>>>>>>check youth clubs " + ClubCount);
            if (ClubCount == 0) {

                Intent intent = new Intent(getBaseContext(), CreateYouthClub.class);
                intent.putExtra("Username", Username);
                startActivity(intent);
                finish();
            }
            else{

                Intent intent1 = new Intent(getBaseContext(), adminHomePage.class);
                intent1.putExtra("Username", Username);
                startActivity(intent1);
                finish();

            }
        }
    }



}

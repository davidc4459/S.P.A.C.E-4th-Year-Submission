package com.david.spacev4.UserLogin;

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

import com.david.spacev4.R;
import com.david.spacev4.UserPages.FollowPage;
import com.david.spacev4.UserPages.userMainMenu;
import com.example.david.myapplication.backend.followingListApi.FollowingListApi;
import com.example.david.myapplication.backend.followingListApi.model.FollowingList;
import com.example.david.myapplication.backend.userLoginApi.UserLoginApi;
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

public class UserLoginScreen extends AppCompatActivity {
    public static String Username;
    Context context;
    int FollowCount;
    TextView Login;
    String PlainTextUsername;

    public static final List<String> YOUTH_CLUB_FOLLOW_LIST = new ArrayList<String>();

    public static String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_screen);
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
                Intent intent4 = new Intent(getBaseContext(), UserCreateAccount.class);
                startActivity(intent4);
                finish();
                break;
        }
        if (newActivity != null) startActivity(newActivity);
    }
    private void HandleLoginButtonClick() {
        this.Login = (TextView) findViewById(R.id.usernametext);
        Username = Login.getText().toString();
        try {
            DESKeySpec keySpec = new DESKeySpec(
                    "sdh5jHD3hwe49F8Erh".getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            Username = Base64.encodeToString(cipher.doFinal(Username
                    .getBytes("UTF-8")), Base64.DEFAULT);

        }
        catch(Exception e){}

        if (Username.matches("") )
        {

        }
        else
        {
            new CheckUserNameAsyncTask().execute("Username");

        }
    }
    class CheckUserNameAsyncTask extends AsyncTask<String, Void, String> {
        private UserLoginApi myApiService = null;
        private Context context;
        private final static String TAG = "EndpointAsyncTask";
        private TextView allNotesText;
        String allNotes = "";

        public CheckUserNameAsyncTask() {
        }
        @Override
        protected String doInBackground(String... params) {
            if (myApiService == null)
            { // Only do this once
                Log.d(TAG, ">>>>>>>>>>>>>>>>>creating connection");
                UserLoginApi.Builder builder = new UserLoginApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://space-1092.appspot.com/_ah/api/");
                myApiService = builder.build();
            }

            try
            {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>Trying to execute");
                return myApiService.get(Username).execute().getUserID();
            }
            catch (IOException e) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>Error");
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            Log.d(TAG, ">>>>>>>>>>>>>>>>>Checking user name");

            if (result.equals(Username))
            {

                UserID = Username;
                Log.d(TAG, Username);
                Log.d(TAG, ">>>>>>>>>>>>>>>>>UserName Matched");
                new CheckFollowListAsyncTask(context).execute();

            }
            else {
                Toast.makeText(getApplicationContext(),
                        "Wrong Username!", Toast.LENGTH_LONG).show();
                EditText userName = (EditText)findViewById(R.id.usernametext);
                userName.setError("User Name did not exist!");

            }
        }
    }
    class CheckFollowListAsyncTask extends AsyncTask<Void, Void, List<FollowingList>> {
        private FollowingListApi myApiService = null;
        private Context context;
        private final static String TAG = "EndpointAsyncTask";
        private TextView allNotesText;
        String allNotes = "";

        public CheckFollowListAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<FollowingList> doInBackground(Void... params) {
            if (myApiService == null) { // Only do this once
                Log.d(TAG, ">>>>>>>>>>>>>>>>>creating connection");
                FollowingListApi.Builder builder = new FollowingListApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
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
        protected void onPostExecute(List<FollowingList> result) {
            int i = result.size() - 1;
            FollowCount = 0;
            YOUTH_CLUB_FOLLOW_LIST.clear();
            while (i >= 0){
                Log.d(TAG, ">>>>>>>>>>>>>>>>>filling list");
                FollowingList q = result.get(i);
                if (q.getUserID().matches(Username)) {
                    Log.d(TAG,q.getUserID());
                    Log.d(TAG,Username);
                    FollowCount ++;
                    YOUTH_CLUB_FOLLOW_LIST.add(q.getYouthClub());
                }
                i--;
            }

            Log.d(TAG, ""+ FollowCount);
            Log.d(TAG, ">>>>>>>>>>>>>>>>>check youth clubs " + FollowCount);
            if (FollowCount == 0) {
                Intent intent = new Intent(getBaseContext(), FollowPage.class);
                intent.putExtra("Username", Username);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent1 = new Intent(getBaseContext(), userMainMenu.class);
                intent1.putExtra("Username", Username);
                startActivity(intent1);
                finish();

            }
        }
    }


}

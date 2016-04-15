package com.david.spacev4.UserPages;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.david.spacev4.R;
import com.example.david.myapplication.backend.followingListApi.FollowingListApi;
import com.example.david.myapplication.backend.followingListApi.model.FollowingList;
import com.example.david.myapplication.backend.youthClubApi.YouthClubApi;
import com.example.david.myapplication.backend.youthClubApi.model.YouthClub;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FollowPage extends AppCompatActivity {
    Spinner dropdown;
    Spinner dropdown2;
    private Context context;
    int FollowIDint;
    String FollowID;
    String Username;

    public static final List<String> YOUTH_CLUB_LIST = new ArrayList<String>();
    ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Choose One","Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon", "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        YOUTH_CLUB_LIST.add("Choose One After Search");
        dropdown2 = (Spinner)findViewById(R.id.spinner2);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, YOUTH_CLUB_LIST);
        dropdown2.setAdapter(adapter2);
    }
    public void onClick(View view) {
        Intent newActivity = null;
        switch (view.getId()) {    //depending on the button pressed the correct activity is launched
            case R.id.SearchButton:

                new FollowYouthClubAsyncTask(context).execute();
                adapter2.notifyDataSetChanged();
                break;
            case R.id.FollowButton:
                String YouthClub = dropdown2.getSelectedItem().toString();
                Log.d("follow page", ">>>>>>>>>>>>>>>>>" + YouthClub);
                if (YouthClub.equals("Choose One After Search")){}
                else if (YouthClub.equals("no youth clubs in area")){}
                else{
                    FollowingList NewFollow = new FollowingList();
                    NewFollow.setYouthClub(YouthClub);
                    Username = getIntent().getStringExtra("Username");
                    NewFollow.setUserID(Username);
                    new uploadAsyncTask().execute(new Pair<Context, FollowingList>(this, NewFollow));
                    Intent intent = new Intent(getBaseContext(), userMainMenu.class);
                    intent.putExtra("Username", Username);
                    startActivity(intent);
                    finish();
                }
                break;

        }
        if (newActivity != null) startActivity(newActivity);
    }
    class FollowYouthClubAsyncTask extends AsyncTask<Void, Void, List<YouthClub>> {
        private YouthClubApi myApiService = null;
        private Context context;
        private final static String TAG = "EndpointAsyncTask";
        private TextView allNotesText;
        String allNotes = "";

        public FollowYouthClubAsyncTask(Context context) {
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
            String Area = dropdown.getSelectedItem().toString();
            YOUTH_CLUB_LIST.clear();

            YOUTH_CLUB_LIST.remove("Choose One After Search");

            while (i >= 0){
                Log.d(TAG, ">>>>>>>>>>>>>>>>>filling list");
                YouthClub q = result.get(i);
                if (q.getArea().matches(Area)) {
                    YOUTH_CLUB_LIST.add(q.getYouthClub());
                }
                i--;
            }
            if (YOUTH_CLUB_LIST.size() == 0)
            {
                YOUTH_CLUB_LIST.add("no youth clubs in area");
            }

            dropdown2.setAdapter(adapter2);
        }
    }
    class uploadAsyncTask extends AsyncTask<Pair<Context,FollowingList>, Void, FollowingList> {
        private FollowingListApi myApiService = null;
        private Context context;

        @Override
        protected FollowingList doInBackground(Pair<Context, FollowingList>... params) {
            if(myApiService == null)
            {  // Only do this once
                FollowingListApi.Builder builder = new FollowingListApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://space-1092.appspot.com/_ah/api/");
                // end options for devappserver
                myApiService = builder.build();
            }
            try {
                List<FollowingList> result = myApiService.list().execute().getItems();
                for (FollowingList q : result) {
                    FollowID = q.getFollowID();
                }

            }
            catch (IOException e) {
            }
            FollowIDint = Integer.parseInt(FollowID);
            FollowIDint ++;
            FollowID = Integer.toString(FollowIDint);

            context = params[0].first;
            FollowingList NewFollow = params[0].second;
            NewFollow.setFollowID(FollowID);

            try {
                Log.d("follow page", ">>>>>>>>>>>>>>>>>Connection");
                myApiService.insert(NewFollow).execute().getYouthClub();
                return NewFollow;
            } catch (IOException e) {
                Log.d("follow page", ">>>>>>>>>>>>>>>>>Error");
                return NewFollow;
            }
        }

        @Override
        protected void onPostExecute(FollowingList result)
        {
            com.david.spacev4.UserLogin.UserLoginScreen.YOUTH_CLUB_FOLLOW_LIST.add(result.getYouthClub());
        }
    }

}

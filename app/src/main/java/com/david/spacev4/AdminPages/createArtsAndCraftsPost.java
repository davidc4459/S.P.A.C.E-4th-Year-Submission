package com.david.spacev4.AdminPages;

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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.david.spacev4.R;
import com.example.david.myapplication.backend.artsAndCraftsPostApi.ArtsAndCraftsPostApi;
import com.example.david.myapplication.backend.artsAndCraftsPostApi.model.ArtsAndCraftsPost;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.List;

public class createArtsAndCraftsPost extends AppCompatActivity {
    int PostIDInt;
    int PostIDIntCheck = 0;
    String PostID;
    private TimePicker ActivityTime;
    String TimeString;
    int hour;
    int minute;
    private DatePicker ActivityDate;
    String DateString;
    int Day;
    int Month;
    String ActivityTypeString;
    TextView ActiviteyTypeText;
    String SuporvisorString;
    TextView SupervisorsText;
    String AgeGroupString;
    TextView AgeGroupText;
    String InformationString;
    TextView informationText;
    Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_arts_and_crafts_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dropdown = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, com.david.spacev4.AdminLogin.Login.YOUTH_CLUB_LIST);
        dropdown.setAdapter(adapter);
    }

    public void onClick(View view) {
        Intent newActivity = null;
        switch (view.getId()) {    //depending on the button pressed the correct activity is launched
            case R.id.Post:
                HandlePostButtonClick();
                Intent intent1 = new Intent(getBaseContext(), adminHomePage.class);
                startActivity(intent1);
                finish();
                break;

        }
        if (newActivity != null) startActivity(newActivity);
    }
    private void HandlePostButtonClick() {
        this.ActiviteyTypeText = (TextView) findViewById(R.id.ActivityTypeeditText);
        if( ActiviteyTypeText.getText().toString().length() == 0 ){
            ActivityTypeString = "Blank ";
        }
        else {ActivityTypeString = ActiviteyTypeText.getText().toString();}

        this.SupervisorsText = (TextView) findViewById(R.id.SupervisorseditText);
        if( SupervisorsText.getText().toString().length() == 0 ){
            SuporvisorString = "Blank ";
        }
        else {SuporvisorString = SupervisorsText.getText().toString();}

        this.AgeGroupText = (TextView) findViewById(R.id.AgeGroupeditText);
        if( AgeGroupText.getText().toString().length() == 0 ){
            AgeGroupString = "Blank ";
        }
        else {AgeGroupString = AgeGroupText.getText().toString();}

        this.ActivityDate = (DatePicker) findViewById(R.id.datePicker);
        Day = ActivityDate.getDayOfMonth();
        Month = ActivityDate.getMonth();
        Month ++;
        DateString = Integer.toString(Day) + "/" + Integer.toString(Month);

        this.ActivityTime = (TimePicker) findViewById(R.id.timePicker);
        hour = ActivityTime.getCurrentHour();
        minute = ActivityTime.getCurrentMinute();
        TimeString = Integer.toString(hour) + ":" + Integer.toString(minute);


        this.informationText = (TextView) findViewById(R.id.InformationeditText2);
        InformationString = informationText.getText().toString();
        if( informationText.getText().toString().length() == 0 ){
            InformationString = "Blank ";
        }
        else {InformationString = informationText.getText().toString();}

        String YouthClubName = dropdown.getSelectedItem().toString();

        ArtsAndCraftsPost post = new ArtsAndCraftsPost();

        post.setPostID("1");
        post.setActivityType(ActivityTypeString);
        post.setOrganisers(SuporvisorString);
        post.setAgeGroup(AgeGroupString);
        post.setDate(DateString);
        post.setTime(TimeString);
        post.setInformation(InformationString);
        post.setYouthClub(YouthClubName);


        new uploadAsyncTask().execute(new Pair<Context, ArtsAndCraftsPost>(this, post));

    }
    class uploadAsyncTask extends AsyncTask<Pair<Context,ArtsAndCraftsPost>, Void, ArtsAndCraftsPost> {
        private ArtsAndCraftsPostApi myApiService = null;
        private Context context;

        @Override
        protected ArtsAndCraftsPost doInBackground(Pair<Context, ArtsAndCraftsPost>... params) {
            if(myApiService == null)
            {  // Only do this once
                ArtsAndCraftsPostApi.Builder builder = new ArtsAndCraftsPostApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://space-1092.appspot.com/_ah/api/");
                // end options for devappserver
                myApiService = builder.build();
            }


            try {
                List<ArtsAndCraftsPost> result = myApiService.list().execute().getItems();
                for (ArtsAndCraftsPost q : result) {
                    PostID = q.getPostID();
                    PostIDIntCheck = Integer.parseInt(PostID);
                    if (PostIDIntCheck > PostIDInt )
                    {
                        PostIDInt = PostIDIntCheck;
                    }
                }

            }
            catch (IOException e) {
            }
            PostIDInt ++;
            Log.d("async task", ">>>>>>>>>>>>>>>>>" + PostIDInt);
            PostID = Integer.toString(PostIDInt);

            context = params[0].first;
            ArtsAndCraftsPost post = params[0].second;
            post.setPostID(PostID);

            try {
                Log.d("async task", ">>>>>>>>>>>>>>>>> trying to send");
                myApiService.insert(post).execute().getPostID();
                return post;
            } catch (IOException e) {
                Log.d("async task", ">>>>>>>>>>>>>>>>> railed");
                return post;
            }
        }

        @Override
        protected void onPostExecute(ArtsAndCraftsPost result)
        {

        }
    }

}

package com.david.spacev4.AdminPages;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.david.spacev4.R;
import com.example.david.myapplication.backend.talksAndClassesPostApi.TalksAndClassesPostApi;
import com.example.david.myapplication.backend.talksAndClassesPostApi.model.TalksAndClassesPost;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.List;


public class createTalksAndClassesPost extends AppCompatActivity {
    int PostIDInt;
    String PostID;
    int PostIDIntCheck = 0;
    private TimePicker ActivityTime;
    String TimeString;
    int hour;
    int minute;
    private DatePicker ActivityDate;
    String DateString;
    int Day;
    int Month;
    String SpeakerString;
    TextView SpeakerText;
    String TopicString;
    TextView TopicText;
    String AgeGroupString;
    TextView AgeGroupText;
    String InformationString;
    TextView informationText;
    Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_talks_and_classes_post);
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
        this.SpeakerText = (TextView) findViewById(R.id.SpeakereditText);
        if( SpeakerText.getText().toString().length() == 0 ){
            SpeakerString = "Blank ";
        }
        else {SpeakerString = SpeakerText.getText().toString();}

        this.TopicText = (TextView) findViewById(R.id.TopiceditText);
        if( TopicText.getText().toString().length() == 0 ){
            TopicString = "Blank ";
        }
        else {TopicString = TopicText.getText().toString();}


        this.AgeGroupText = (TextView) findViewById(R.id.AgeGroupeditText);
        if( AgeGroupText.getText().toString().length() == 0 ){
            AgeGroupString = "None ";
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

        this.informationText = (TextView) findViewById(R.id.InformationeditText);
        if( informationText.getText().toString().length() == 0 ){
            InformationString = "None ";
        }
        else {InformationString = informationText.getText().toString();}


        String YouthClubName = dropdown.getSelectedItem().toString();

        TalksAndClassesPost post = new TalksAndClassesPost();

        post.setPost("1");
        post.setSpeakerName(SpeakerString);
        post.setTopic(TopicString);
        post.setAgeGroup(AgeGroupString);
        post.setDate(DateString);
        post.setTime(TimeString);
        post.setInformation(InformationString);
        post.setYouthClub(YouthClubName);

        new uploadAsyncTask().execute(new Pair<Context, TalksAndClassesPost>(this, post));

    }
    class uploadAsyncTask extends AsyncTask<Pair<Context,TalksAndClassesPost>, Void, TalksAndClassesPost> {
        private TalksAndClassesPostApi myApiService = null;
        private Context context;
        @Override
        protected TalksAndClassesPost doInBackground(Pair<Context, TalksAndClassesPost>... params) {
            if(myApiService == null)
            {  // Only do this once
                TalksAndClassesPostApi.Builder builder = new TalksAndClassesPostApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://space-1092.appspot.com/_ah/api/");
                // end options for devappserver
                myApiService = builder.build();
            }
            try {
                List<TalksAndClassesPost> result = myApiService.list().execute().getItems();
                for (TalksAndClassesPost q : result) {
                    PostID = q.getPost();
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
            PostID = Integer.toString(PostIDInt);

            context = params[0].first;
            TalksAndClassesPost post = params[0].second;
            post.setPost(PostID);

            try {
                myApiService.insert(post).execute().getPost();
                return post;
            } catch (IOException e) {
                return post;
            }
        }

        @Override
        protected void onPostExecute(TalksAndClassesPost result)
        {

        }
    }

}


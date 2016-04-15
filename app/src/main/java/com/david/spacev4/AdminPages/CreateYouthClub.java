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
import android.widget.Spinner;
import android.widget.TextView;

import com.david.spacev4.R;
import com.example.david.myapplication.backend.youthClubApi.YouthClubApi;
import com.example.david.myapplication.backend.youthClubApi.model.YouthClub;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;


import java.io.IOException;

public class CreateYouthClub extends AppCompatActivity {
    String YouthClub;
    TextView YouthClubtext;
    String Area;
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_youth_club);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon", "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

    }
    public void onClick(View view) {
        Intent newActivity = null;
        switch (view.getId()) {    //depending on the button pressed the correct activity is launched
            case R.id.CreateButton:
                HandleCreateButtonClick();
                Intent intent1 = new Intent(getBaseContext(), adminHomePage.class);
                startActivity(intent1);
                break;

        }
        if (newActivity != null) startActivity(newActivity);
    }
    private void HandleCreateButtonClick() {
        YouthClubtext = (TextView) findViewById(R.id.YouthClub);
        this.YouthClub = YouthClubtext.getText().toString();

        String Area = dropdown.getSelectedItem().toString();

        YouthClub NewYouthClub = new YouthClub();

        NewYouthClub.setYouthClub(YouthClub);
        NewYouthClub.setAdminName(com.david.spacev4.AdminLogin.Login.Username);
        NewYouthClub.setArea(Area);

        new uploadAsyncTask().execute(new Pair<Context, YouthClub>(this, NewYouthClub));

    }
    class uploadAsyncTask extends AsyncTask<Pair<Context,YouthClub>, Void, YouthClub> {
        private YouthClubApi myApiService = null;
        private Context context;

        @Override
        protected YouthClub doInBackground(Pair<Context, YouthClub>... params) {
            if(myApiService == null)
            {  // Only do this once
                YouthClubApi.Builder builder = new YouthClubApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://space-1092.appspot.com/_ah/api/");
                // end options for devappserver
                myApiService = builder.build();
            }

            context = params[0].first;
            YouthClub NewYouthClub = params[0].second;

            try {
                myApiService.insert(NewYouthClub).execute().getYouthClub();
                return NewYouthClub;
            } catch (IOException e) {

                return NewYouthClub;
            }
        }

        @Override
        protected void onPostExecute(YouthClub result)
        {

            com.david.spacev4.AdminLogin.Login.YOUTH_CLUB_LIST.add(result.getYouthClub());

        }
    }
}

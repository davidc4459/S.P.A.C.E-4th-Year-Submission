package com.david.spacev4.AdminPages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.david.spacev4.AccountChoice;
import com.david.spacev4.R;

public class adminHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void onClick(View view) {
        Intent newActivity = null;
        switch (view.getId()) {    //depending on the button pressed the correct activity is launched
            case R.id.CreateYouthClubPage:
                Intent intent = new Intent(getBaseContext(), CreateYouthClub.class);
                startActivity(intent);
                break;
            case R.id.TalksAndClassesButton:
                Intent intent1 = new Intent(getBaseContext(), createTalksAndClassesPost.class);
                startActivity(intent1);
                break;
            case R.id.ArtsAndCraftsButton:
                Intent intent2 = new Intent(getBaseContext(), createArtsAndCraftsPost.class);
                startActivity(intent2);
                break;
            case R.id.CompetitionsButton:
                Intent intent3 = new Intent(getBaseContext(), createCompetitionPost.class);
                startActivity(intent3);
                break;
            case R.id.logoutbutton:
                Intent intent4 = new Intent(getBaseContext(), AccountChoice.class);
                startActivity(intent4);
                finish();
                break;
        }
        if (newActivity != null) startActivity(newActivity);
    }

}

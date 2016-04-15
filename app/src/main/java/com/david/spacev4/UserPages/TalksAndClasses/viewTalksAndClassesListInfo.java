package com.david.spacev4.UserPages.TalksAndClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.david.spacev4.R;

public class viewTalksAndClassesListInfo extends AppCompatActivity implements
        TalksAndClassesListFragmentation.Callbacks {
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_talks_and_classes_list_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.circle_detail_container) != null) {

            mTwoPane = true;

            ((TalksAndClassesListFragmentation) getSupportFragmentManager().findFragmentById(
                    R.id.circle_list)).setActivateOnItemClick(true);
        }
    }
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(TalksAndClassesDetailFragmentation.ARG_ITEM_ID, id);
            TalksAndClassesListFragmentation fragment = new TalksAndClassesListFragmentation();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.circle_detail_container, fragment).commit();

        } else {
            Intent detailIntent = new Intent(this, viewTalksAndClassesListExtraInfo.class);
            detailIntent.putExtra(TalksAndClassesDetailFragmentation.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
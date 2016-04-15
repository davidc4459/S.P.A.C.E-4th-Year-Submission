package com.david.spacev4.UserPages.ArtsAndCrafts;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.david.spacev4.R;

public class viewArtsAndCraftsListInfo extends AppCompatActivity implements
        ArtsAndCraftsListFragmentation.Callbacks {
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_arts_and_crafts_list_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.circle_detail_container) != null) {

            mTwoPane = true;

            ((ArtsAndCraftsListFragmentation) getSupportFragmentManager().findFragmentById(
                    R.id.circle_list)).setActivateOnItemClick(true);
        }
    }
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ArtsAndCraftsDetailFragmentation.ARG_ITEM_ID, id);
            ArtsAndCraftsDetailFragmentation fragment = new ArtsAndCraftsDetailFragmentation();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.circle_detail_container, fragment).commit();

        } else {
            Intent detailIntent = new Intent(this, viewArtsAndCraftsListExtraInfo.class);
            detailIntent.putExtra(ArtsAndCraftsDetailFragmentation.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
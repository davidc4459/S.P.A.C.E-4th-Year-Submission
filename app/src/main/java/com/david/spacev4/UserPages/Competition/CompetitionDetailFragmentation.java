package com.david.spacev4.UserPages.Competition;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.david.spacev4.R;
import com.example.david.myapplication.backend.competitionPostApi.model.CompetitionPost;

public class CompetitionDetailFragmentation extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private CompetitionPost mItem;
    private Context context;

    public CompetitionDetailFragmentation() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = CompetitionListFragmentation.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_arts_and_crafts_detail_fragmentation,
                container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.circle_detail))
                    .setText(mItem.getYouthClub());
            ((TextView) rootView.findViewById(R.id.circle_view))
                    .setText("Competition Type: " + mItem.getCompetitionType() + "\nOrganisers: " + mItem.getOrganisers() + "\nAgeGroup: " + mItem.getAgeGroup() + "\nDate: " + mItem.getDate() + "\nTime: " + mItem.getTime() + "\nInformation: " + mItem.getInformation());
        }

        return rootView;
    }
}


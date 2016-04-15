package com.david.spacev4.UserPages.ArtsAndCrafts;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.david.myapplication.backend.artsAndCraftsPostApi.ArtsAndCraftsPostApi;
import com.example.david.myapplication.backend.artsAndCraftsPostApi.model.ArtsAndCraftsPost;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtsAndCraftsListFragmentation extends ListFragment {
    private Context context;
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;
    public static final List<ArtsAndCraftsPost> ITEMS = new ArrayList<ArtsAndCraftsPost>();
    public static final Map<String, ArtsAndCraftsPost> ITEM_MAP = new HashMap<String, ArtsAndCraftsPost>();

    public interface Callbacks {
        public void onItemSelected(String id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    public ArtsAndCraftsListFragmentation() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ITEMS.clear();
        ITEM_MAP.clear();
        new ArtsAndCraftsPostAsyncTask(context).execute();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState
                    .getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position,
                                long id) {
        super.onListItemClick(listView, view, position, id);

        mCallbacks.onItemSelected(ITEMS.get(position).getPostID());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
    class ArtsAndCraftsPostAsyncTask extends AsyncTask<Void, Void, List<ArtsAndCraftsPost>> {
        private ArtsAndCraftsPostApi myApiService = null;
        private Context context;
        private final static String TAG = "EndpointAsyncTask";
        private TextView allNotesText;
        String allNotes = "";

        public ArtsAndCraftsPostAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<ArtsAndCraftsPost> doInBackground(Void... params) {
            if (myApiService == null) { // Only do this once
                Log.d(TAG, ">>>>>>>>>>>>>>>>>creating connection");
                ArtsAndCraftsPostApi.Builder builder = new ArtsAndCraftsPostApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
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
        protected void onPostExecute(List<ArtsAndCraftsPost> result) {
            int i = result.size() - 1;

            List<String> allActivitys= new ArrayList<String>();;

            while (i >= 0){
                Log.d(TAG, ">>>>>>>>>>>>>>>>>filling list");
                ArtsAndCraftsPost q = result.get(i);
                if ( com.david.spacev4.UserLogin.UserLoginScreen.YOUTH_CLUB_FOLLOW_LIST.contains(q.getYouthClub())){
                    q.getActivityType();
                    allActivitys.add(q.getActivityType());
                    ITEMS.add(q);
                    ITEM_MAP.put(q.getPostID(), q);
                }
                i--;
            }
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1, allActivitys));

        }
    }

}
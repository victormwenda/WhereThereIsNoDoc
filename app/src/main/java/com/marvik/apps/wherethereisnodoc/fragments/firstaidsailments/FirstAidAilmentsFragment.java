package com.marvik.apps.wherethereisnodoc.fragments.firstaidsailments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.marvik.apps.coreutils.fragments.FragmentWrapper;
import com.marvik.apps.wherethereisnodoc.R;
import com.marvik.apps.wherethereisnodoc.datamodels.firstaids.FirstAidsInfo;
import com.marvik.apps.wherethereisnodoc.intents.Intents;
import com.marvik.apps.wherethereisnodoc.listadapters.firstaids.ailments.FirstAidsAilmentsAdapter;

import java.util.List;

/**
 * FirstAidAilmentsFragment - Ailments that have first aid options
 * Created by victor on 1/11/2016.
 */
public class FirstAidAilmentsFragment extends FragmentWrapper {

    //list view that holds all the ailments with first aids
    private ListView mLvAilments;

    //callbacks called when events occurs
    private FirstAidAilmentsItemClickCallbacks firstAidAilmentsItemClickCallbacks;

    //List of all ailments with first aids

    private List<FirstAidsInfo> firstAidsAilmentsList;

    /**
     * Called when the fragment is created
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {
        getActivity().registerReceiver(receiver, new IntentFilter(Intents.Broadcasts.ACTION_NEW_FIRST_AID_ADDED));
    }

    /**
     * Used for setting the title of the Activity
     *
     * @return activity title
     */
    @Nullable
    @Override
    public String getActivityTitle() {
        return getActivity().getString(R.string.fragment_firstaidsailments);
    }

    /**
     * Callback used to receive any bundle passed to the fragment when this fragment was created
     */
    @Override
    public void receiveBundle() {

    }

    /**
     * Used for creating a custom view for the fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Nullable
    @Override
    public View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getActivity().getLayoutInflater().inflate(getParentLayout(), container, false);
    }

    /**
     * Initializes the child views for the fragment being created.
     *
     * @param view
     */
    @Override
    public View initFragmentViews(View view) {
        mLvAilments = (ListView) view.findViewById(R.id.fragment_firstaid_ailments_listView_ailments);
        mLvAilments.setOnItemClickListener(firstAidAilmentsClickListener);
        return view;
    }

    /**
     * Callback used to hold methods that consume all the contents of the bundle passed to the fragment
     */
    @Override
    public void consumeBundle() {

    }

    /**
     * Callback called to attach data to all the views in the fragment
     */
    @Override
    public void attachViewsData() {

        firstAidsAilmentsList = getTransactionsManager().getAllFistAids();

        mLvAilments.setAdapter(new FirstAidsAilmentsAdapter(getActivity(), R.layout.list_firstaids_ailments, firstAidsAilmentsList));
    }

    /**
     * Called when a fragment is first attached to its context.
     * {#onCreate(Bundle)} will be called after this.
     */
    @Override
    public void onAttachFragment() {
        firstAidAilmentsItemClickCallbacks = (FirstAidAilmentsItemClickCallbacks) getActivity();
    }


    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResumeFragment() {

    }

    /**
     * Called to provide implementation to initiate the syncing of the contents of the current fragment
     */
    @Override
    public void performPartialSync() {
        //Fetch First Aids
        getTransactionsManager().getUtils().getWebServicesManager().testSyncFirstAids(getTransactionsManager());
    }

    /**
     * Called when only the contents of the current fragment are synced
     */
    @Override
    public void onPerformPartialSync() {
        attachViewsData();
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to  Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPauseFragment() {

    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after onStop()} and before onDetach()}.
     */
    @Override
    public void onDestroyFragment() {
        getActivity().unregisterReceiver(receiver);
    }

    /**
     * Returns the layout resource id for the layout used to populate the view for this fragment
     *
     * @return the layout resource id
     */
    @Override
    public int getParentLayout() {
        return R.layout.fragment_firstaid_ailments;
    }

    public interface FirstAidAilmentsItemClickCallbacks {
        void onFirstAidAilmentsClick(int firstAidId);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intents.Broadcasts.ACTION_NEW_FIRST_AID_ADDED)) {
                onPerformPartialSync();
            }
        }
    };
    private AdapterView.OnItemClickListener firstAidAilmentsClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent == mLvAilments) {
                int firstAidId = firstAidsAilmentsList.get(position).getFirstaidId();
                firstAidAilmentsItemClickCallbacks.onFirstAidAilmentsClick(firstAidId);
            }
        }
    };

}
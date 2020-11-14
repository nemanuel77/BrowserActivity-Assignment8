package temple.edu;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class PageListFragment extends Fragment {
    //initial elements
   View myView;
   ListView myList;

   //internal list for adapter to utilize
   ArrayList<String> listItems = new ArrayList<String>();
   //list adapter with primary component listItems
   ArrayAdapter<String> listAdapter;

   //for interaction with BrowserActivity
    getListData parentActivity;


    public PageListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           /* mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    /*
    Define attachment behavior for fragment
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PageListFragment.getListData)
            parentActivity = (PageListFragment.getListData) context;
        else
            throw new RuntimeException("Activity doesn't implement PageListFragment");
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("theList", listItems);

        //this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //initialize list and adapter
        myView = inflater.inflate(R.layout.fragment_page_list, container, false);
        myList = myView.findViewById(R.id.pageListView);

        //check savedinstancestate
        if (savedInstanceState != null){

            listItems = (ArrayList<String>) savedInstanceState.getSerializable("theList");
            Log.d("OOO", "FIRED: ONSAVE IS NOT NULL, ATTACHED: " +listItems);
            listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
            myList.setAdapter(listAdapter);
        }
        else{

            listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
            myList.setAdapter(listAdapter);

        }

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("BBB", "YOU CLICKED " + position);
                parentActivity.sendPositionToActivity(position);
            }
        });


        myList.setAdapter(listAdapter);


        return myView;
    }

    public void sendURLtoList(ArrayList<String> theList) {

        if (listAdapter != null){
            listItems.addAll(theList);
            listAdapter.notifyDataSetChanged();
        }
        else {

            Log.d("AAA","YO THE LIST IS NULL MY DUDE");

        }





    }

    /*
    SEND POSITION OF SELECTED LIST ITEM TO PARENT ACTIVITY.
    MATCH POSITION OF INDEX TO FRAGMENT INDEX.
     */

    public interface getListData{

        void sendPositionToActivity(int index);


    }
}
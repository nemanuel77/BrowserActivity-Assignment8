package temple.edu;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagerFragment extends Fragment {

    ViewPager viewPager;
    SwipeAdapter swipeAdapter;
    View myView;
    List<Fragment> testList = new ArrayList<>();
    FragmentManager fm;
    getFragments parentActivity;



    // TODO: Rename and change types of parameters
    private ArrayList<PageViewerFragment> internalArrayList = new ArrayList<PageViewerFragment>();
    /* private ArrayList<PageViewerFragment> internalArrayList;*/

    public PagerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PagerFragment newInstance(String param1, String param2) {
        PagerFragment fragment = new PagerFragment();

        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
       super.onSaveInstanceState(outState);
       outState.putSerializable("Array", internalArrayList);

        //this.setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            internalArrayList = (ArrayList<PageViewerFragment>) savedInstanceState.getSerializable("Array");
            Log.d("ZZZ","saveInstanceState FIRED " + savedInstanceState);
        }
        else{
            internalArrayList = new ArrayList<PageViewerFragment>();
            Log.d("YYY","saveInstanceState NOT FIRED.");
        }

       

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_pager, container, false);
        viewPager = myView.findViewById(R.id.viewPager);

        swipeAdapter = new SwipeAdapter(getChildFragmentManager(), internalArrayList);
        viewPager.setAdapter(swipeAdapter);
        Log.d("onCreateView method", "before saveInstanceState");


        myView.setBackgroundColor(Color.CYAN);

        if (savedInstanceState != null) {
            //internalArrayList = getArguments();
            //internalArrayList = getArguments().getParcelableArrayList("Array");
            viewPager.setAdapter(swipeAdapter);
            //viewPager.setCurrentItem();
            viewPager.getAdapter().notifyDataSetChanged();
            Log.d("inside saveInstanceState, ", "internal array is size " + internalArrayList.size());
        }

        //Toast.makeText(getContext(), "length jawn is " + internalArrayList.size(), Toast.LENGTH_LONG).show();

        return myView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PagerFragment.getFragments)
            parentActivity = (PagerFragment.getFragments) context;
        else
            throw new RuntimeException("Activity doesn't implement PagerFragment");
    }



    public void addToInternalArrayStorage(PageViewerFragment theFragment){
        internalArrayList.add(theFragment);
        swipeAdapter.notifyDataSetChanged();
        Log.d("QQQ", "internal array now reads at " +internalArrayList.size());
    }



    public interface getFragments {

        void addFragment(PageViewerFragment theFragment);



    }


}
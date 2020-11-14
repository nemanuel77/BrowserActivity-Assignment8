package temple.edu;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SwipeAdapter extends FragmentStatePagerAdapter {

    private ArrayList<PageViewerFragment> myFragmentList;

    public SwipeAdapter(@NonNull FragmentManager fm, ArrayList<PageViewerFragment> theArrayList) {
        super(fm);
        myFragmentList = theArrayList;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return myFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return myFragmentList.size();
    }

    public void addToList(PageViewerFragment theFragment) {
        myFragmentList.add(theFragment);

        Log.d("CCC", "size of myFragmentList is "+myFragmentList.size());
        notifyDataSetChanged();
    }

    public void removeFromList(PageViewerFragment theFragment) {
        if (myFragmentList.contains(theFragment)) {
            myFragmentList.remove(theFragment);
            notifyDataSetChanged();
        }
    }


    public void updateList(ArrayList<PageViewerFragment> updatedList) {
        myFragmentList.clear();
        this.myFragmentList = updatedList;
        notifyDataSetChanged();
    }

}

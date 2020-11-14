package temple.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements
        PageControlFragment.getURLAddress,
        PageViewerFragment.loadWebPageData,
        BrowserControlFragment.createNewPVF,
        PagerFragment.getFragments,
        PageListFragment.getListData{

    //define resources
    //Resources res = getResources();

    //define fragments to be used in application
    PageControlFragment PCF = new PageControlFragment();
    PageViewerFragment PVE = new PageViewerFragment();
    BrowserControlFragment BCF = new BrowserControlFragment();
    PageListFragment PLF = new PageListFragment();


    Bundle myBundle = new Bundle();
    ArrayList<PageViewerFragment> myPVFList = new ArrayList<>();
    ArrayList<String> myStringURLList = new ArrayList<String>();

    PagerFragment pagerFragment = new PagerFragment();

    Fragment tempFragment;

    FragmentManager fm;

    //boolean to check phone orientation(landscape/mobile)
    Boolean isLandscape;

    int activityIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBundle = new Bundle();

        if (savedInstanceState != null){
            myStringURLList = (ArrayList<String>) savedInstanceState.getSerializable("theSavedURLArray");
        }

        //pagerList = new ArrayList<>();

        //check for a container that is only drawn in landscape orientation.
        isLandscape = findViewById(R.id.page_list) != null;

        //initialize fragment manager
        fm = getSupportFragmentManager();


        //set up BrowserControlFragment
        if ((tempFragment = fm.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment)
            BCF = (BrowserControlFragment) tempFragment;
        else {
            BCF = new BrowserControlFragment();
            fm.beginTransaction()
                    .add(R.id.browser_control, BCF)
                    .commit();
        }

        //set up PageControlFragment
        if ((tempFragment = fm.findFragmentById(R.id.page_control)) instanceof PageControlFragment)
            PCF = (PageControlFragment) tempFragment;
        else {
            PCF = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.page_control, PCF)
                    .commit();
        }

        //set up PagerFragment
        if ((tempFragment = fm.findFragmentById(R.id.page_viewer)) instanceof PagerFragment) {
            pagerFragment = (PagerFragment) tempFragment;

        }
        else {
            pagerFragment = new PagerFragment();
            fm.beginTransaction()
                    .add(R.id.page_viewer, pagerFragment)
                    .commit();
        }


        //build PageList (if in landscape orientation)
        if (isLandscape)

            if ((tempFragment = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment)
                PLF = (PageListFragment) tempFragment;
            else {

                fm.beginTransaction()
                        .add(R.id.page_list, PLF)
                        .commit();

            }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("theSavedURLArray", myStringURLList);
    }

    /*
        Browser Fragment sends string to Page Viewer Element
        for processing
         */
    @Override
    public void sendURL(String string) {
        if (PVE.webView != null){
            PVE.getURLFromParent(string);
        }
        else{
            Toast.makeText(this, "Please instantiate a new browser first.", Toast.LENGTH_LONG).show();
        }


    }





    /*
   Browser goes backwards
    */
    @Override
    public void goBack() {
        if (PVE.webView != null){
            PVE.goBack();
        }
        else{
            Toast.makeText(this, "Please instantiate a new browser first.", Toast.LENGTH_LONG).show();
        }
    }

    /*
   Browser goes forward
    */
    @Override
    public void goForward() {

        if (PVE.webView != null){
            PVE.goForward();
        }
        else{
            Toast.makeText(this, "Please instantiate a new browser first.", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void sendURLToTxt(String string) {
        PCF.getNewURL(string);

    }

    @Override
    public void sendWebTitle(String url, int index) {
        myStringURLList.set(index, url);
        Log.d("EEE", "Item at "+index+ " is: " +myStringURLList.get(index));
        if (isLandscape != null){
            try{PLF.sendURLtoList(myStringURLList);
                Log.d("MMM", "FIRED IN ACTIVITY");
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    /*

    Creates a new Page Viewer Fragment and adds it to the adapter
    in Pager Fragment.

     */
    @Override
    public void newPVFPage() {
        PVE = PageViewerFragment.newInstance(activityIndex);
        myStringURLList.add("New Tab");
        Log.d("EEE", "Item at "+activityIndex+ " is: " +myStringURLList.get(activityIndex));
        activityIndex++;

        addFragment(PVE);

    }

    //ADDS FRAGMENT TO PAGER FRAGMENTS INTERNAL ARRAY LIST
    @Override
    public void addFragment(PageViewerFragment theFragment) {
        //pagerFragment.swipeAdapter.addToList(theFragment);
        pagerFragment.addToInternalArrayStorage(theFragment);

        Log.d("CCC", "size of pagerFragment list is "+pagerFragment.swipeAdapter.getCount());


    }

    /*
    When a list view item is clicked, the index of
    the clicked item is sent to the activity here.
    the viewpager can set the current item viewed to the
    clicked list view item.
     */
    @Override
    public void sendPositionToActivity(int index) {
        pagerFragment.viewPager.setCurrentItem(index);
    }
}
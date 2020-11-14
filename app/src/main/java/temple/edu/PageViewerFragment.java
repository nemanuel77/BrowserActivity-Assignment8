package temple.edu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;



public class PageViewerFragment extends Fragment {


    View myView;
    WebView webView;
    String theUrl;

    //int index;

    loadWebPageData parentActivity;

    private String theUrlKey;
    private int index;


    public PageViewerFragment() {
        // Required empty public constructor
    }

    public static PageViewerFragment newInstance(int indexOfPVE){
        PageViewerFragment internalPVE = new PageViewerFragment();
        Bundle args = new Bundle();
        args.putInt("theIndex", indexOfPVE);
        internalPVE.setArguments(args);

        return internalPVE;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle;
        if ((bundle = getArguments()) != null) {
            index = bundle.getInt("theIndex");
            theUrlKey = bundle.getString("theUrlAtIndex");
            //webView.restoreState(savedInstanceState);
            Log.d("OOO", "restored with values: " +index+ ","+theUrlKey);
            //Log.d("OOP", "savedInstance is  " + savedInstanceState);
        }
        /*else {
            Log.d("OPP", "savedInstance is  " + savedInstanceState);
            webView.restoreState(savedInstanceState);
        }*/

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theIndex",index);
        outState.putString("theUrlAtIndex",theUrlKey);
        webView.saveState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof loadWebPageData)
            parentActivity = (loadWebPageData) context;
        else
            throw new RuntimeException("Activity doesn't implement PVF interface");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_page_viewer, container, false);
        myView.setBackgroundColor(Color.RED);
        webView = myView.findViewById(R.id.web_PVF);
        webView.getSettings().setJavaScriptEnabled(true);



        if (savedInstanceState != null) {
            Log.d("WWW", "FIRED: WEB SAVED STATE NOT NULL");
            webView.restoreState(savedInstanceState);
        }

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //parentActivity.updateURL(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                theUrlKey = view.getTitle();
                Log.d("TTT", "WEB TITLE IS " +theUrlKey);
                parentActivity.sendWebTitle(theUrlKey, index);
                Log.d("SSS", "Sending to parent activity: "+theUrlKey +", "+index);
            }
        });

        return myView;

        /*webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                theUrl = request.getUrl().toString();
                //Toast.makeText(getActivity(), theUrl, Toast.LENGTH_LONG).show();
                //you moved this outside of the setclient
                parentActivity.sendURLToTxt(theUrl);

                return super.shouldOverrideUrlLoading(view, request);
            }
        });*/

        //theUrlKey = webView.getTitle();

    }

    public void getURLFromParent(String string) {
        try{
            webView.loadUrl(string);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //theUrlKey = webView.getTitle();
        //Log.d("UUU", "URL  TITLE IS: " +theUrlKey);
        //undisable this if this works

        //ok this doesnt work. you probably should just send the index
        //parentActivity.sendWebTitle(theUrlKey, index);
        //not this one
        //parentActivity.sendURLToTxt(theUrl);



    }

    public void goBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            Toast.makeText(getActivity(), "There is no webpage to go back to.", Toast.LENGTH_LONG).show();
        }
    }

    public void goForward() {
        if (webView.canGoForward()) {
            webView.goForward();
        } else {
            Toast.makeText(getActivity(), "There is no webpage to go forward to.", Toast.LENGTH_LONG).show();
        }
    }


    public interface loadWebPageData {
        void sendURLToTxt(String string);
        void sendWebTitle(String url, int index);



    }


}

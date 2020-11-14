package temple.edu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageControlFragment extends Fragment {

    private EditText urlInput;
    private ImageButton btnLoad;
    private ImageButton btnNext;
    private ImageButton btnBack;

    //List<String> history;

    getURLAddress parentActivity;

    public PageControlFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PageControlFragment newInstance() {
        PageControlFragment fragment = new PageControlFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           urlInput.setText(savedInstanceState.getString("theURL"));
        }

        String urlString;


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof getURLAddress)
            parentActivity = (getURLAddress) context;
        else
            throw new RuntimeException("Activity doesn't implement getURLAddress");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_page_control, container, false);


        urlInput = myView.findViewById(R.id.txtURL);
        btnLoad = myView.findViewById(R.id.imgBtn_Load);
        btnNext = myView.findViewById(R.id.imgBtn_Next);
        btnBack = myView.findViewById(R.id.imgBtn_Back);

        if (savedInstanceState != null) {
            String setText = savedInstanceState.getString("URL");
            urlInput.setText(setText);
        }

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String theURL = "https://www.google.com";
                 String theURL = urlInput.getText().toString();
                if (theURL.contains("http://") || theURL.contains("https://")) {
                    Toast.makeText(getActivity(), theURL, Toast.LENGTH_LONG).show();
                    parentActivity.sendURL(theURL);

                    //history.add(theURL);
                } else {
                    theURL = "https://" + theURL;
                    Toast.makeText(getActivity(), theURL, Toast.LENGTH_LONG).show();
                    parentActivity.sendURL(theURL);
                    //history.add(theURL);
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String theURL = urlInput.getText().toString();
                parentActivity.goBack();

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //String theURL = urlInput.getText().toString();
                parentActivity.goForward();
            }
        });


        return myView;
    }

    public void getNewURL(String string) {
        urlInput.setText(string);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("theURL", urlInput.getText().toString());

    }

    public interface getURLAddress {
        void sendURL(String string);

        void goBack();

        void goForward();
    }

}
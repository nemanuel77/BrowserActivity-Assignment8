package temple.edu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.Browser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowserControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowserControlFragment extends Fragment {

    View myView;
    ImageButton myImageBtn;

    createNewPVF parentActivity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   /* private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/

    // TODO: Rename and change types of parameters
   /* private String mParam1;
    private String mParam2;*/

    public BrowserControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrowserControl.
     */
    // TODO: Rename and change types and number of parameters
    public static BrowserControlFragment newInstance(String param1, String param2) {
        BrowserControlFragment fragment = new BrowserControlFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BrowserControlFragment.createNewPVF)
            parentActivity = (BrowserControlFragment.createNewPVF) context;
        else
            throw new RuntimeException("Activity doesn't implement sendURLToFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_browser_control, container, false);
        myImageBtn = myView.findViewById(R.id.btnNewBrowser);

        myImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this will eventually need to make a new instance of a page fragment
                //use an interface to interact with the browser to create a new page viewer fragment
                //put the toast in the browseractivity side to read the array length
                //Toast.makeText(getActivity(), "ay you clicked this shit", Toast.LENGTH_LONG).show();
                parentActivity.newPVFPage();
            }
        });

        return myView;
    }

    public interface createNewPVF {
        //add to list in future

        //build new PVF
        void newPVFPage();

    }
}
package com.example.android.edonate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CharityProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CharityProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharityProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //firebase instance variables:
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    //initialize other variables
    private TextView Email;
    private TextView Uid;
    private TextView Name;
    private TextView CellNo;
    private Button EditInformation;

    private OnFragmentInteractionListener mListener;

    public CharityProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CharityProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static CharityProfile newInstance(String param1, String param2) {
        CharityProfile fragment = new CharityProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.charity_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //attach variables to form elements:
        Email = (TextView)getView().findViewById(R.id.profileEmail);
        //Uid = (TextView)getView().findViewById(R.id.userID);
        Name = (TextView)getView().findViewById(R.id.charityName);
        CellNo = (TextView)getView().findViewById(R.id.charityCellNo);
        EditInformation = (Button)getView().findViewById(R.id.edit_charity_info);

        //Initialize firebase authentication:
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        EditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditInformation.class));
            }
        });


        //display user details
        if (user != null){
            String email = user.getEmail();
            String uid = user.getUid();
            Email.setText(email);
            //Uid.setText(uid);

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference uidRef = rootRef.child("users").child(uid);

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("fname").getValue(String.class);
                    String cellno = dataSnapshot.child("cellNo").getValue(String.class);
                    Name.setText(name);
                    CellNo.setText(cellno);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Error: ", databaseError.getMessage());
                }

            };
            uidRef.addListenerForSingleValueEvent(valueEventListener);

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

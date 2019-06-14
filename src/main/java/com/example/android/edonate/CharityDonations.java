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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CharityDonations extends Fragment {
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

    private OnFragmentInteractionListener mListener;

    public CharityDonations() {

    }

    public static CharityDonations newInstance(String param1, String param2) {
        CharityDonations fragment = new CharityDonations();
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
        //return view;
        return inflater.inflate(R.layout.donations_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Initialize firebase authentication:
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        final ArrayList<Donation> donations = new ArrayList<Donation>();


        final DonationAdapter adapter = new DonationAdapter(getContext(), donations);
        if (user != null){
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("donations");

            //ValueEventListener valueEventListener = new ValueEventListener() {
             rootRef.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                     for (DataSnapshot data : dataSnapshot.getChildren()) {
                         Donation dono = data.getValue(Donation.class);
                         /*
                         String name1 = data.child("name").getValue(String.class);
                         Log.v("Name is: ",name1);
                         String details1 = data.child("details").getValue(String.class);
                         Log.v("Detail is: ",details1);
                         String quantity1 = data.child("quantity").getValue(String.class);
                         Log.v("Quantity is: ",quantity1);
                         String type1 = data.child("type").getValue(String.class);
                         Log.v("Type is: ",type1);
                         */
                         donations.add(dono);  //WHY IS THIS NOT WORKING??
                         adapter.setDonations(donations);
                     }

                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {
                     Log.d("Error: ", databaseError.getMessage());

                 }
             });
        }

        ListView listView = (ListView)getActivity().findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Donation donationXYZ = donations.get(position);
                String name1 = donationXYZ.getName();
                String details1 = donationXYZ.getDetails();
                String quantity1 = donationXYZ.getQuantity();
                String type1 = donationXYZ.getType();
                String donor1= donationXYZ.getDonorID();
                String photo1 = donationXYZ.getPhotoUrl();

                Intent intent = new Intent(getContext(),DonationDetails.class);
                intent.putExtra("THE_NAME",name1);
                intent.putExtra("THE_DETAILS",details1);
                intent.putExtra("THE_QUANTITY", quantity1);
                intent.putExtra("THE_TYPE", type1);
                intent.putExtra("THE_DONOR", donor1);
                intent.putExtra("THE_PHOTO", photo1);

                startActivity(intent);
            }
        });

        /*
        // Initialize message ListView and its adapter
        List<Donation> donations = new ArrayList<>();
        mDonationAdapter = new DonationAdapter(getContext(), R.layout.list_item, donations);
        mDonationListView.setAdapter(mDonationAdapter);
        */

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

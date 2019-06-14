package com.example.android.edonate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.telephony.MbmsDownloadSession.RESULT_CANCELLED;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class DonorDonate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_PHOTO_PICKER =  2;

    ProgressDialog nDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //variables to store data from the form
    private EditText name;
    private EditText quantity;
    private EditText details;
    private Spinner type;
    private Button confirmDonation;
    private ImageButton mPhotoPickerButton;

    public String downloadUrl;
    public String donorID;

    //Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDonationsDatabaseReference;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mDonationPhotosStorageReference;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public DonorDonate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DonorDonate.
     */
    // TODO: Rename and change types and number of parameters
    public static DonorDonate newInstance(String param1, String param2) {
        DonorDonate fragment = new DonorDonate();
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
        return inflater.inflate(R.layout.donor_donate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Initialize firebase components:
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        //to set to specific location within the database and storage
        mDonationsDatabaseReference = mFirebaseDatabase.getReference().child("donations");
        mDonationPhotosStorageReference = mFirebaseStorage.getReference().child("donation_photos");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        donorID= user.getUid();

        //to get data from the form:
        name=(EditText)getView().findViewById(R.id.name);
        quantity=(EditText)getView().findViewById(R.id.quantity);
        details=(EditText)getView().findViewById(R.id.details);
        type=(Spinner)getView().findViewById(R.id.donation_category);
        mPhotoPickerButton = (ImageButton) getView().findViewById(R.id.photoPickerButton);
        confirmDonation = (Button)getView().findViewById(R.id.button_confirm_donation);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                photoPickerIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(photoPickerIntent, "Pick a picture using"), RC_PHOTO_PICKER );
            }
        });

        confirmDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Donation donation = new Donation(name.getText().toString(), quantity.getText().toString(), details.getText().toString(), type.getSelectedItem().toString(), downloadUrl);
                //mDonationsDatabaseReference.push().setValue(donation);
                nDialog = new ProgressDialog(getContext());
                nDialog.setMessage("Posting Donation..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                Toast.makeText(getActivity(), "Donation posted successfully!",
                        Toast.LENGTH_SHORT).show();
                nDialog.dismiss();

                //to clear the input fields
                name.setText("");
                quantity.setText("");
                details.setText("");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER) {
            Uri selectedImageUri = data.getData();

            //get a reference of the image file
            final StorageReference photoRef = mDonationPhotosStorageReference.child(selectedImageUri.getLastPathSegment());

            //upload image file to firebase storage
            photoRef.putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl = uri.toString();
                                //This is your image url do whatever you want with it.
                                Donation donation = new Donation(name.getText().toString(), quantity.getText().toString(), details.getText().toString(), type.getSelectedItem().toString(), downloadUrl, donorID);
                                mDonationsDatabaseReference.push().setValue(donation);  //this is not being done at the right time. should be done when 'Confirm Donation' button clciked!
                            }
                        });
                    }
                }
            });


                    // When the image has successfully uploaded, we get its download URL
                    //String downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                    // Set the download URL to the message box, so that the user can send it to the database
                    //Donation donation = new Donation(name.getText().toString(), quantity.getText().toString(), details.getText().toString(), type.getSelectedItem().toString(), downloadUrl);
                    //mDonationsDatabaseReference.push().setValue(donation);



        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

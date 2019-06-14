package com.example.android.edonate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

public class DonationDetails extends AppCompatActivity{
    private TextView Name;
    private TextView Quantity;
    private TextView Details;
    private TextView Type;
    private TextView Donor;
    private ImageView Image;


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_details);

        Name = (TextView) findViewById(R.id.item_name_1);
        Quantity = (TextView)findViewById(R.id.item_quantity_1);
        Details=(TextView)findViewById(R.id.item_details_1);
        Type = (TextView)findViewById(R.id.item_type_1);
        Donor = (TextView)findViewById(R.id.item_donor_1);
        Image = (ImageView)findViewById(R.id.item_image);

        String name= getIntent().getStringExtra("THE_NAME");
        String quantity= getIntent().getStringExtra("THE_QUANTITY");
        String details= getIntent().getStringExtra("THE_DETAILS");
        String type = getIntent().getStringExtra("THE_TYPE");
        String donorID=getIntent().getStringExtra("THE_DONOR");
        String photoID=getIntent().getStringExtra("THE_PHOTO");


        //to retieve donor name
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("users").child(donorID);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("fname").getValue(String.class);
                Donor.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Error: ", databaseError.getMessage());
            }
        };

        uidRef.addListenerForSingleValueEvent(valueEventListener);

        new DownloadImageTask(Image).execute(photoID);

        Name.setText(name);
        Quantity.setText(quantity);
        Details.setText(details);
        Type.setText(type);



    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

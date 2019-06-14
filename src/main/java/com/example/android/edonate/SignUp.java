package com.example.android.edonate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    ProgressDialog nDialog;

    //variables to store data from the form
    private EditText fname;
    private EditText lname;
    private EditText email;
    private EditText cellNo;
    private EditText password;
    private EditText confirmPassword;
    private Spinner type;
    private Button signUp;

    //Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        //Initialize firebase components:
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        //to set to specific location within the database and storage
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("users");

        //Initialize firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //to get data from the form:
        // TextWatcher would let us check validation error on the fly
        fname=(EditText)findViewById(R.id.fname);
        fname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                FormValidation.hasText(fname);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        lname=(EditText)findViewById(R.id.lname);
        lname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                FormValidation.hasText(lname);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        email=(EditText)findViewById(R.id.email);
        email.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                FormValidation.isEmailAddress(email, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        cellNo=(EditText)findViewById(R.id.mobile_number);
        cellNo.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                FormValidation.isPhoneNumber(cellNo, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        password=(EditText)findViewById(R.id.password);
        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                FormValidation.isPassword(password, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        confirmPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                FormValidation.isPassword(confirmPassword, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });


        type=(Spinner)findViewById(R.id.user_category);
        signUp = (Button)findViewById(R.id.button_sign_up_2);

        //Send button sends a message and clears the EditText
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });
    }

    //to register user to the Firebase 'users'
    public void RegisterUser(){
        nDialog = new ProgressDialog(SignUp.this);
        nDialog.setMessage("Signing Up..");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            //check if successful
                            if (task.isSuccessful()) {
                                User user = new User(fname.getText().toString(), lname.getText().toString(), email.getText().toString(), cellNo.getText().toString(), password.getText().toString(), type.getSelectedItem().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                                mUsersDatabaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);  //add user details to the database
                                Toast.makeText(SignUp.this, "Sign Up successful!",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                nDialog.dismiss();
                            }else{
                                Toast.makeText(SignUp.this, "Couldn't Sign Up, please try again.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
}

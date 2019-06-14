package com.example.android.edonate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditInformation extends AppCompatActivity {
    private EditText newEmail;
    private EditText newPassword;
    private EditText newCellNo;
    private FirebaseAuth mAuth;
    private EditText oldPassword;
    private FirebaseUser currentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private Button Update;

    ProgressDialog nDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_information);

        newEmail = (EditText)findViewById(R.id.new_email);
        newPassword = (EditText)findViewById(R.id.new_password);
        newCellNo=(EditText)findViewById(R.id.new_cell_no);
        oldPassword=(EditText)findViewById(R.id.old_password);
        Update = (Button)findViewById(R.id.button_update_info);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == Update){
                    UpdateUser();
                }
            }
        });

    }

    public void UpdateUser(){
        nDialog = new ProgressDialog(EditInformation.this);
        nDialog.setMessage("Updating Information..");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
        final String NewEmail = newEmail.getText().toString().trim();
        final String NewPassword = newPassword.getText().toString().trim();
        final String OldPassword = oldPassword.getText().toString().trim();
        final String NewCellNo = newCellNo.getText().toString().trim();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("users").child(uid);
        uidRef.child("email").setValue(NewEmail);
        uidRef.child("password").setValue(NewPassword);
        uidRef.child("cellNo").setValue(NewCellNo);

        currentUser= FirebaseAuth.getInstance().getCurrentUser();

        //currentUser.updatePassword(NewPassword);  //program is running but password not being updated

        //Changes made above are reflected in the database but not in the sign in. Need to make changes to the authentication tab as well!
        nDialog.dismiss();
        Toast.makeText(EditInformation.this, "Information updated successfully",
                Toast.LENGTH_SHORT).show();
        newEmail.setText("");
        newPassword.setText("");
        newCellNo.setText("");

        //New code:
        final FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email,OldPassword);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Log.d("task"," reauthenticate successful");
                    user.updatePassword(NewPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Log.d("task","failed");

                            }else {

                                currentUser.updateEmail(NewEmail);
                                Log.d("task","succesfful");
                            }
                        }
                    });
                }else {


                    Log.d("task",task.getException().getMessage());
                }
            }

        });
    }
}






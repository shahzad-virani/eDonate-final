package com.example.android.edonate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ProgressDialog nDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBackPressed() {   //this is a very crude implementation!!
    }

    public void signIn(View view) {
        nDialog = new ProgressDialog(MainActivity.this);
        nDialog.setMessage("Loading..");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();

        Intent intent = new Intent(this, SignIn.class);
        nDialog.dismiss();
        startActivity(intent);
    }

    public void signUp(View view) {
        nDialog = new ProgressDialog(MainActivity.this);
        nDialog.setMessage("Loading..");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();

        Intent intent = new Intent(this, SignUp.class);
        nDialog.dismiss();
        startActivity(intent);
    }

public void aboutUs(View view) {
    nDialog = new ProgressDialog(MainActivity.this);
    nDialog.setMessage("Loading..");
    nDialog.setIndeterminate(false);
    nDialog.setCancelable(true);
    nDialog.show();

        Intent intent = new Intent(this, AboutUs.class);
        nDialog.dismiss();
        startActivity(intent);
    }
}

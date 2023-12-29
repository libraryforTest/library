package com.example.ecomarket.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.R;
import com.google.android.material.navigation.NavigationView;

public class Account extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    ImageView imageView;
    TextView userMail, fullName;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");
        final String username =  getIntent().getStringExtra("Username");

   final int id =  getIntent().getIntExtra("id",1);

        imageView =  findViewById(R.id.images);
        userMail =  findViewById(R.id.auseremail);
        fullName =  findViewById(R.id.ausername);






        userMail.setText(logged);
        fullName.setText(username);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Account.this, "", Toast.LENGTH_SHORT).show();
            }
        });


        navigationView = findViewById(R.id.userAccountMenu);

        assert role != null;
        if(role.equals("user")){

            navigationView.getMenu().findItem(R.id.addBooks).setVisible(false);

        }

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        final String logged = (String) getIntent().getStringExtra("Mail");
        final String role = (String) getIntent().getStringExtra("ROLE");
        final int idUser =  getIntent().getIntExtra("idUser",1);

        int id = menuItem.getItemId();
        if (id == R.id.addBooks){
            Intent addbooks = new Intent(Account.this, AddUser.class);
            addbooks.putExtra("Mail",logged);
            addbooks.putExtra("ROLE",role);
            addbooks.putExtra("id", idUser);
            startActivity(addbooks);
            finish();
        }else if(id == R.id.requests){
            Toast.makeText(this, "check requests", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.editProfile){
            Intent edit = new Intent(Account.this, EditAccount.class);
            edit.putExtra("Mail",logged);
            edit.putExtra("ROLE",role);
            edit.putExtra("id", idUser);
            startActivity(edit);
            finish();
        }else if(id == R.id.logOut){
            logOutAlert();
        }
        return false;
    }

    public void logOutAlert() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Account.this);

        // Set the message show for the Alert time
        builder.setMessage("Want to Logout?");

        // Set Alert Title
        builder.setTitle("Ecomarker");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("remember", "false");
                                editor.putString("email", "");
                                editor.putString("password", "");
                                editor.apply();
                                startActivity(new Intent(Account.this, LogIn.class));
                                finish();
                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    public void onBackPressed(){
        String role =  getIntent().getStringExtra("ROLE");
        final String logged =  getIntent().getStringExtra("Mail");

        assert role != null;
        if(role.equals("Admin")){
            Intent intent = new Intent(Account.this, AppNotification.class);
            intent.putExtra("Mail",logged);
            intent.putExtra("ROLE",role);
            startActivity(intent);
            finish();
        }else if(role.equals("user")){
            Intent intent = new Intent(Account.this, AppHome.class);
            intent.putExtra("Mail",logged);
            intent.putExtra("ROLE",role);
            startActivity(intent);
            finish();
        }

    }
}
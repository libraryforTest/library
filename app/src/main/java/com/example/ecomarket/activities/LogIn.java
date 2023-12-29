package com.example.ecomarket.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomarket.BD.UserDB;
import com.example.ecomarket.Model.User;
import com.example.library.R;

public class LogIn extends AppCompatActivity {
    TextView lemail;
    Button logInBtn;
    CheckBox rememberMe;
    TextView signUp;
    TextView lpass;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        lemail =  findViewById(R.id.luserMail);
        lpass = findViewById(R.id.luserPassword);
        progressBar = findViewById(R.id.lprogressBar);
        signUp = findViewById(R.id.lsignUpBtn);
        rememberMe = findViewById(R.id.rememberMe);


        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        String mail = preferences.getString("email", "");
        String pass = preferences.getString("password", "");
        lemail.setText(mail);
        lpass.setText(pass);

        if(checkbox.equals("true")){
            getData();
        }



        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){

                    String email = lemail.getText().toString();
                    String password = lpass.getText().toString();
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();
                }else if(!buttonView.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.putString("email", "");
                    editor.putString("password", "");
                    editor.apply();
                }
            }
        });

        logInBtn = findViewById(R.id.mainlogInBtn);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, signUp.class));
                finish();
            }
        });



    }



    void getData() {


        final String  email, password;

        email = lemail.getText().toString();
        password = lpass.getText().toString();

        if(!email.equals("") || !password.equals(""))
        {
            User u = new User();
            u.setEmail(email);
            u.setPwd(password);

            UserDB userdb = new UserDB(getBaseContext());
            userdb.opendb();
            User userExist = userdb.getUserWithLog(u);

            if (userExist != null) {
                progressBar.setVisibility(View.VISIBLE);
                checkUserRole(userExist);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LogIn.this, "Invalid credentials, please try again", Toast.LENGTH_SHORT).show();
            }




        }else {
            Toast.makeText(LogIn.this, "All Blanks must be filled", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkUserRole(User user) {

        if (user.getRole().equals("admin")) {
            Intent intent = new Intent(LogIn.this, AppHome.class);
            intent.putExtra("Mail", user.getEmail());
            intent.putExtra("ROLE", "Admin");
            intent.putExtra("Username", user.getUsername());
            intent.putExtra("idUser", user.getId());
            startActivity(intent);
            finish();
        } else if (user.getRole().equals("user")) {
            Intent intent = new Intent(LogIn.this, AppHome.class);
            intent.putExtra("Mail", user.getEmail());
            intent.putExtra("Username", user.getUsername());
            intent.putExtra("ROLE", "User"); // Assuming the role for a regular user is "User"
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LogIn.this, "All Blanks must be filled", Toast.LENGTH_SHORT).show();
        }


    }

    public void onBackPressed() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ? 😢");

        // Set Alert Title
        builder.setTitle("Ecomarket");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(true);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // When the user click yes button
                // then app will close
                finish();
            }
        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If user click no
                // then dialog box is canceled.
                dialog.cancel();
            }
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }


    // Constructor or setter for progressBar

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setProgressBarVisibility(int visibility) {
        if (progressBar != null) {
            progressBar.setVisibility(visibility);
        }
    }


}
package com.example.ecomarket.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomarket.BD.UserDB;
import com.example.ecomarket.Model.User;
import com.example.library.R;

public class EditAccount extends AppCompatActivity {


    Button deleteAccount, saveName, savePwd;
    TextView changedName, newChPassword, conChPassword;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        deleteAccount =  findViewById(R.id.deleteAccount);
        saveName =  findViewById(R.id.saveNewName);
        savePwd =  findViewById(R.id.saveNewPwd);
        changedName =  findViewById(R.id.changedUName);



        savePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });
        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeName();

            }
        });


    }

    public void changePassword() {
        newChPassword =  findViewById(R.id.changeNewpassword);
        conChPassword =  findViewById(R.id.changeConfirmUpassword);
        final int idUser =  getIntent().getIntExtra("idUser",1);
        final String newPass, conPass;
        newPass = newChPassword.getText().toString();
        conPass = conChPassword.getText().toString();




        if (!newPass.equals("") || !conPass.equals("")){
            if(!(newPass.length() < 8) || !(conPass.length() < 8)){
                if(newPass.equals(conPass)){


                    UserDB userdb= new UserDB(getBaseContext());
                    userdb.opendb();
                    int l = userdb.updatePasswordById(idUser,newPass);

                    newChPassword.setText("");
                    conChPassword.setText("");
                    Toast.makeText(EditAccount.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                }

                else{
                    newChPassword.setText("");
                    conChPassword.setText("");
                    Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Password must be min 8 characters", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeName() {
        final String logged =  getIntent().getStringExtra("Mail");
        final int idUser =  getIntent().getIntExtra("idUser",1);
        final String userName = changedName.getText().toString();

        String namePattern = "(?=^.{0,50}$)^[a-zA-Z-]+\\s[a-zA-Z-]+$";
        if(userName.matches(namePattern)){
            UserDB userdb= new UserDB(getBaseContext());
            userdb.opendb();
            User u = new User();
            u.setUsername(userName);
            int l = userdb.updateUserById(idUser,u);
            changedName.setText("");
            Toast.makeText(EditAccount.this, "Name changed successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Enter a valid Full Name", Toast.LENGTH_SHORT).show();
        }


    }


    public void deleteDialog() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(EditAccount.this);

        // Set the message show for the Alert time
        builder.setMessage("This action can not be undone");

        // Set Alert Title
        builder.setTitle("⚠ Delete Account");

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
                            public void onClick(DialogInterface dialog, int which)
                            {
                                deleleUserInfo();
                                Toast.makeText(EditAccount.this, "Account Deleted", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(EditAccount.this, signUp.class));
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

    public void deleleUserInfo() {
        final String logged =  getIntent().getStringExtra("Mail");
        final int idUser =  getIntent().getIntExtra("idUser",1);
        UserDB userdb= new UserDB(getBaseContext());
        userdb.opendb();

        int l = userdb.deleteUserById(idUser);

    }

    public void onBackPressed(){
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");
        Intent intent = new Intent(EditAccount.this, Account.class);
        intent.putExtra("Mail",logged);
        intent.putExtra("ROLE",role);
        startActivity(intent);
        finish();
    }


}


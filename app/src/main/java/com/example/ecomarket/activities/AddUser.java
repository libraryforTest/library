package com.example.ecomarket.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecomarket.BD.UserDB;
import com.example.ecomarket.Model.User;
import com.example.library.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class AddUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



      String add_info_url = "http://192.168.1.6/library/book_info_upload.php";
    String add_cover_url = "http://192.168.1.6/library/add_book.php";
    String validate_url = "http://192.168.1.6/library/validate_book.php";
    String categorySelected;
    Uri uri;
    Bitmap bitmap;
    Button addbookBtn;
    EditText Title, Author, Isbn, Description,remaining;
    ImageView bookCover, bookcover2;
    Spinner categorySpinner;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Title = findViewById(R.id.bookTitle);
        remaining = findViewById(R.id.present);
        Author = findViewById(R.id.bookAuthor);
        Isbn = findViewById(R.id.isbn);
        Description = findViewById(R.id.description);
        bookCover = findViewById(R.id.bookCover);
        bookcover2 = findViewById(R.id.book_cover2);
        addbookBtn = findViewById(R.id.addbookBtn);
        categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bookCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);



        addbookBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validate();
            }

        });

        bookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(AddUser.this);
            }
        });

    }



    public  void validate(){
        final String Username, email, Password;
        Username = Title.getText().toString();
        email = Author.getText().toString();
        Password = Isbn.getText().toString();

        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");

        final String username =  getIntent().getStringExtra("name");

        final int idUser =  getIntent().getIntExtra("idUser",1);

        if(!Username.equals("") || !email.equals("") || !Password.equals("")){




            User u = new User();
            u.setEmail(email);
            u.setUsername(Username);
            u.setRole(categorySelected);
            u.setPwd(Password);
            UserDB userdb= new UserDB(getBaseContext());
            userdb.opendb();
            long l = userdb.insertUser(u);

            Toast.makeText(AddUser.this, "User added !", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AddUser.this, Account.class);
            intent.putExtra("Mail",logged);
            intent.putExtra("ROLE",role);
            intent.putExtra("Username",username);
            intent.putExtra("idUser", idUser);
            startActivity(intent);
            finish();

        }else {
            Toast.makeText(AddUser.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {

            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)){
                uri = imageuri;
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            else {
                startCrop(imageuri);
            }

        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
//                userImage.setImageURI(result.getUri());

                Uri uri = result.getUri();

                try {

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    bookCover.setImageBitmap(bitmap);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }


    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(11, 16)
                .setMultiTouchEnabled(false)
                .start(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categorySelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }


    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String response) {

                super.onPostExecute(response);
                // Setting image as transparent after done uploading.
                bookCover.setImageResource(R.drawable.book_cover);

            }

            @Override
            protected String doInBackground(Void... params) {
                String title = Title.getText().toString();
                AddUser.ImageProcessClass imageProcessClass = new AddUser.ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();
                HashMapParams.put("ImageData", ConvertImage);
                HashMapParams.put("title", title);
                HashMapParams.put("coverName", title.replace(" ", ""));
                return imageProcessClass.ImageHttpRequest(add_cover_url, HashMapParams);
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public void onBackPressed(){
        final String logged =  getIntent().getStringExtra("Mail");
        final String role =  getIntent().getStringExtra("ROLE");
        Intent intent = new Intent(AddUser.this, Account.class);
        intent.putExtra("Mail",logged);
        intent.putExtra("ROLE",role);
        startActivity(intent);
        finish();
    }
}
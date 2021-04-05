package com.example.apiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ImageView img ;
    private TextView name, gender, address, email, phone ,dob ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.profile_img);
        name = findViewById(R.id.name);
        gender = findViewById(R.id.gender);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, "https://randomuser.me/api", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results") ;
                    name.setText("Name : " + jsonArray.getJSONObject(0).getJSONObject("name").get("title").toString() + " " + jsonArray.getJSONObject(0).getJSONObject("name").get("first").toString() + " " + jsonArray.getJSONObject(0).getJSONObject("name").get("last").toString());
                    gender.setText("Gender : " + jsonArray.getJSONObject(0).get("gender").toString());
                    email.setText("Email : " + jsonArray.getJSONObject(0).get("email").toString());
                    phone.setText("Phone : " + jsonArray.getJSONObject(0).get("phone").toString());
                    dob.setText("Date Of Birth : " + jsonArray.getJSONObject(0).getJSONObject("dob").get("date").toString().substring(0, 10));
                    address.setText("Address : " + jsonArray.getJSONObject(0).getJSONObject("location").getJSONObject("street").get("name").toString() + ", " + jsonArray.getJSONObject(0).getJSONObject("location").get("city").toString() + ", " + jsonArray.getJSONObject(0).getJSONObject("location").get("state").toString() + ", " + jsonArray.getJSONObject(0).getJSONObject("location").get("postcode").toString());
                    String large = jsonArray.getJSONObject(0).getJSONObject("picture").get("large").toString() ;
                    Picasso.get().load(large).into(img);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) ;

        requestQueue.add(jsonArrayRequest);
    }
}
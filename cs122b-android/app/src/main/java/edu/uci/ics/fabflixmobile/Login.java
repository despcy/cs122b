package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import edu.uci.ics.fabflixmobile.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.fabflixmobile.pojo.UserResp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Login extends ActionBarActivity {

    private EditText username;
    private EditText password;
    private TextView message;
    private Button loginButton;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // upon creation, inflate and initialize the layout
        setContentView(R.layout.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        message = findViewById(R.id.message);
        loginButton = findViewById(R.id.login);
        /**
         * In Android, localhost is the address of the device or the emulator.
         * To connect to your machine, you need to use the below IP address
         * **/
        url = "http://10.0.0.223:8080/api/";

        //assign a listener to call a function to handle the user request when clicking a button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    public void login() {

        message.setText("Trying to login");
        // Use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        //request type is POST
        final StringRequest loginRequest = new StringRequest(Request.Method.POST, url + "android_login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO should parse the json response to redirect to appropriate functions.
                System.out.println(response);
                if (response.contains("\"message\":0")) {
                    ObjectMapper mapper = new ObjectMapper();
                    UserResp cur_user = new UserResp();
                    try {
                        cur_user = mapper.readValue(response, UserResp.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    User u = cur_user.getData();

                    System.out.println("the id is "+u.getId());
                    Log.d("login.success", response);

                    //initialize the activity(page)/destination
                    Intent mainPage = new Intent(Login.this, MainPageActivity.class);
                    //without starting the activity/page, nothing would happen
                    startActivity(mainPage);
                }else {
                    Toast.makeText(getApplicationContext(),"incorrect username and password",Toast.LENGTH_LONG);
                }


            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("login.error", error.toString());
                        Toast.makeText(getApplicationContext(),"login error",Toast.LENGTH_SHORT);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                // Post request form data
                final Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());

                return params;
            }
        };

        // !important: queue.add is where the login request is actually sent
        queue.add(loginRequest);

    }
}
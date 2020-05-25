package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainPageActivity extends Activity {

    private EditText title;
    private EditText year;
    private EditText directer;
    private EditText star;
    private Button searchButton;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        title = findViewById(R.id.editText);
        year = findViewById(R.id.editText1);
        directer = findViewById(R.id.editText2);
        star = findViewById(R.id.editText3);
        searchButton = findViewById(R.id.searchbtn);

        url = Constant.host+"/api/";

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

//        Intent intent=new Intent(this,ListViewActivity.class);
//        startActivity(intent);
    }

    public void search(){
        // Use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        //request type is POST
        final StringRequest loginRequest = new StringRequest(Request.Method.GET, url + "search?title="+title.getText()+"&year="+year.getText()+"&director="+directer.getText()+"&star="+star.getText()+"&page=1&pagesize=20&sort=title_rating&order=asc_desc", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO should parse the json response to redirect to appropriate functions.
                System.out.println(response);
                if (response.contains("\"message\":0")) {

                    Log.d("search.success", response);//

                    String tmpsurl=url + "search?title="+title.getText()+"&year="+year.getText()+"&director="+directer.getText()+"&star="+star.getText()+"&pagesize=20&sort=title_rating&order=asc_desc&page=";
                    //initialize the activity(page)/destination
                    Intent listPage = new Intent(MainPageActivity.this, ListViewActivity.class);
                    listPage.putExtra("data", response);
                    listPage.putExtra("url",tmpsurl);

                    startActivity(listPage);
                }else {
                    Toast t = Toast.makeText(getApplicationContext(),"search fail!",Toast.LENGTH_LONG);
                    t.show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.d("login.error", error.toString());
                Toast t = Toast.makeText(getApplicationContext(),"search error",Toast.LENGTH_SHORT);
                t.show();
            }
        });

        // !important: queue.add is where the login request is actually sent
        queue.add(loginRequest);

    }

}

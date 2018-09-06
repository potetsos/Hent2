package no.ul.hent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    public TextView output;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //hentNett hentObj = new hentNett();
        output = (TextView) findViewById(R.id.outputTextID);
        output.setText("");

        mQueue = Volley.newRequestQueue(this);

        jsonParse();
        //output.setText("hei");



        //String string = hentObj.hentData();
        //output.setText(string);


        
    }

    private void jsonParse(){
        String url = "https://www.nlm.no/api/nlm/v1/list/67560/article/0/15/priority/1";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {



            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("list");


                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject aktivitet = jsonArray.getJSONObject(i);

                        String titel = aktivitet.getString("title");
                        String intro = aktivitet.getString("intro");
                        intro = android.text.Html.fromHtml(intro).toString();
                        //String firstName = employee.getString("firstname");
                        //int age = employee.getInt("age");
                        //String mail = employee.getString("mail");

                        //output.append(firstName + ", "+String.valueOf(age) +", " +mail + "\n");
                        output.append(titel + "\n"+intro + "\n\n");
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                //headers.put("key", "Value");
                //return headers;
                HashMap<String, String> headers = new HashMap<String, String>();
                String creds = String.format("apps:b6686995ad");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                return headers;
            }
        };






        mQueue.add(request);
    }



}

package adobe.com.adobecafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends Activity {

    String session_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.application), Context.MODE_PRIVATE);
        session_id = sharedPreferences.getString(getString(R.string.session_id),null);

        if(session_id==null)
        {
            Intent login = new Intent("LoginActivity");
            startActivity(login);
        }
        else
        {
            Log.d("hahahaha",session_id);
            RequestSingletonQue queue = RequestSingletonQue.getInstance(getApplicationContext());
            String url = "http://192.168.43.24/hackathon/check_login.php";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Log.d("hahahaha",response);
                            try {
                                JSONObject obj = new JSONObject(response);
                                int success = obj.getInt("success");
                                String message = obj.getString("message");
                                Toast.makeText(getApplicationContext(),message , Toast.LENGTH_LONG).show();
                                if(success == 1)
                                {
                                    Intent login = new Intent("ChooseActivity");
                                    startActivity(login);
                                }
                                else
                                {
                                    Intent login = new Intent("LoginActivity");
                                    startActivity(login);
                                }
                            } catch (Throwable t) {
                                Log.e("My App", "Could not parse malformed JSON: \"" + response + "\"");
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("ERROR","error => "+error.toString());
                        }
                    }
            ) {
                // this is the relevant method
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("session_id", session_id);

                    return params;
                }
            };
            queue.add(postRequest);
        }
    }
}

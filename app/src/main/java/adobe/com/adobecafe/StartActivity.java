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

    String session_id,email_id,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.application), Context.MODE_PRIVATE);
        session_id = sharedPreferences.getString(getString(R.string.session_id),null);
        email_id = sharedPreferences.getString(getString(R.string.login_id),null);
        password = sharedPreferences.getString(getString(R.string.password),null);

        if(session_id==null)
        {
            Intent login = new Intent("LoginActivity");
            startActivity(login);
        }
        else
        {
            RequestSingletonQue queue = RequestSingletonQue.getInstance(getApplicationContext());
            String url = "http://hackathon.netai.net/login.php";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            response = sanitizeString(response);
                            if(response.equals("-1")) {
//                                    Toast toast = Toast.makeText(getApplicationContext(), "Wrong ID/Password", Toast.LENGTH_SHORT);
//                                    toast.show();
                                Log.d("D","wrong id passwords");
                                Toast.makeText(getApplicationContext(),"wrong id password", Toast.LENGTH_LONG).show();

                            } else if(response.equals("0")){
                                Log.d("gfd","some error occurred");
                                Toast.makeText(getApplicationContext(),"some error occurred", Toast.LENGTH_LONG).show();
                            }
                            else{

                                SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.application), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(getString(R.string.session_id), response);
                                Log.d("hahahaha", "jlwdskjwndkoiwdklw");
                                Log.d("hahahaha", "as,mnkand"+response);
                                session_id = sharedpreferences.getString(getString(R.string.session_id),"hahaha");
                                Log.d("hahahaha",session_id);
                                Intent login = new Intent("ChooseActivity");
                                startActivity(login);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("ERROR","error => "+error.toString());
                            Toast.makeText(getApplicationContext(),"some error occurred", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
            ) {
                // this is the relevant method
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("login_id", email_id);
                    params.put("password", password);

                    return params;
                }
            };
            queue.add(postRequest);
        }
    }

    String sanitizeString(String response)
    {
        return response.replace("\r\n<!-- Hosting24 Analytics Code -->\r\n<script type=\"text/javascript\" src=\"http://stats.hosting24.com/count.php\"></script>\r\n<!-- End Of Analytics Code -->\r\n","");
    }
}

package adobe.com.adobecafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText emailID, password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailID = (EditText) findViewById(R.id.emailId);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RequestSingletonQue queue = RequestSingletonQue.getInstance(getApplicationContext());
                String url = "http://hackathon.netai.net/login.php";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("-1")) {
//                                    Toast toast = Toast.makeText(getApplicationContext(), "Wrong ID/Password", Toast.LENGTH_SHORT);
//                                    toast.show();
                                    Log.d("D","wrong id passwords");
                                    Toast.makeText(getApplicationContext(),"wrong id password", Toast.LENGTH_LONG).show();

                                } else if(response.equals("0")){
                                    Log.d("gfd","some error occurred");
                                    Toast.makeText(getApplicationContext(),"some error occurred", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.application), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    Log.d("hahahaha",response);
                                    editor.putString(getString(R.string.session_id), response);
                                    editor.putString(getString(R.string.login_id),emailID.getText().toString());
                                    editor.putString(getString(R.string.password),password.getText().toString());
                                    editor.commit();
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
                            }
                        }
                ) {
                    // this is the relevant method
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("login_id", emailID.getText().toString());
                        params.put("password", password.getText().toString());

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }
}
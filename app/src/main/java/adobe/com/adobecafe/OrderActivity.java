package adobe.com.adobecafe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    ListView listView;
    String session_id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ArrayList<Product> a = new ArrayList<>();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.application), Context.MODE_PRIVATE);
        session_id = sharedPreferences.getString(getString(R.string.session_id),null);
        RequestSingletonQue queue = RequestSingletonQue.getInstance(getApplicationContext());
        String url = "http://hackathon.netai.net/read_snacks.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hahahahaha",response);
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

        listView = (ListView)findViewById(R.id.listView);
        OrderListAdapter orderListAdapter = new OrderListAdapter(this,getItemId(a.size()),a);
        listView.setAdapter(orderListAdapter);

    }

    Integer[] getItemId(int n)
    {
        Integer[] a = new Integer[n];
        for (int i=0;i<n;i++)
        {
            a[i] = i;
        }
        return a;
    }
}



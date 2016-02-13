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
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    ListView listView;
    String session_id;
    Button orderButton;
    ArrayList<Product> a;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setTitle("Order");
        a = new ArrayList<>();
        a.add(new Product("asasas","saasasas","0"));
        a.add(new Product("kjfo","saasasas","0"));
        a.add(new Product("asalkvmdkvsas","saasasas","0"));
        a.add(new Product("flvkmdlk","saasasas","0"));
        a.add(new Product("vdmfkv","saasasas","0"));
        a.add(new Product("ekdjei","saasasas","0"));
        a.add(new Product("kfkv","saasasas","0"));

/*

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
                        try
                        {
                            JSONObject obj = new JSONObject(response);
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
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
                params.put("time","evening");
                return params;
            }
        };
        queue.add(postRequest);
*/

        listView = (ListView)findViewById(R.id.listView);
        orderButton = (Button)findViewById(R.id.order_button);
        final OrderListAdapter orderListAdapter = new OrderListAdapter(this,getItemId(a.size()),a);
        listView.setAdapter(orderListAdapter);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] quantity = orderListAdapter.getQuantity();
                for (int i=0;i<quantity.length;i++)
                {
                    a.get(i).quantity = quantity[i]+"";
                }
                Gson gson = new Gson();
                String jsonorderlist = gson.toJson(a);
                Intent receipt = new Intent("ReceiptActivity");
                receipt.putExtra("productlist",jsonorderlist);
                startActivity(receipt);
            }
        });

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



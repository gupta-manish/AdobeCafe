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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity implements OrderListAdapter.OrderListAdapterInterface {

    ListView listView;
    String session_id;
    Button orderButton;
    ArrayList<Product> a;
    RelativeLayout page_layout,wait_layout;
    Context context;
    OrderListAdapter orderListAdapter;

    ArrayList<Product> orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setTitle("Order");

        context = this;
        page_layout = (RelativeLayout)findViewById(R.id.page_layout);
        wait_layout = (RelativeLayout)findViewById(R.id.wait_layout);
        listView = (ListView)findViewById(R.id.listView);
        orderButton = (Button)findViewById(R.id.order_button);
        orderButton.setEnabled(false);
        orderList = new ArrayList<>();
        /*a = new ArrayList<>();
        a.add(new Product("Egg Chowmein","20","0"));
        a.add(new Product("Paneer Kulcha","16","0"));
        a.add(new Product("Papri Chaat","20","0"));
        a.add(new Product("Omelette","20","0"));
        a.add(new Product("Half Fry","16","0"));
        a.add(new Product("Full fry","20","0"));
        a.add(new Product("Bread Pakoda","18","0"));*/


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.application), Context.MODE_PRIVATE);
        session_id = sharedPreferences.getString(getString(R.string.session_id),null);
        RequestSingletonQue queue = RequestSingletonQue.getInstance(getApplicationContext());
        String url = "http://hackathon.netai.net/read_snacks.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        response = sanitizeString(response);
                        Log.d("hahahahaha",response);
                        try
                        {
                            JSONObject obj = new JSONObject(response);
                            String success = obj.getString("success");
                            if(success.contains("0"))
                            {
                                orderList.add(new Product("Egg Chowmein","20","0"));
                                orderList.add(new Product("Paneer Kulcha","16","0"));
                                orderList.add(new Product("Papri Chaat","20","0"));
                                orderList.add(new Product("Omelette","20","0"));
                                orderList.add(new Product("Half Fry","16","0"));
                                orderList.add(new Product("Full fry","20","0"));
                                orderList.add(new Product("Bread Pakoda","18","0"));
                            }
                            else
                            {
                                JSONArray arr = obj.getJSONArray("snacks");
                                for(int i =0;i<arr.length();i++)
                                {
                                    orderList.add(new Product(arr.getJSONObject(i).getString("name"),arr.getJSONObject(i).getString("price"),"0"));
                                }
                            }

                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                        orderListAdapter = new OrderListAdapter(context,getItemId(orderList.size()),orderList);
                        listView.setAdapter(orderListAdapter);
                        wait_layout.setVisibility(View.INVISIBLE);
                        page_layout.setVisibility(View.VISIBLE);
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
/*
        final OrderListAdapter orderListAdapter = new OrderListAdapter(this,getItemId(a.size()),a);
        listView.setAdapter(orderListAdapter);*/
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] quantity = orderListAdapter.getQuantity();
                for (int i=0;i<quantity.length;i++)
                {
                    orderList.get(i).quantity = quantity[i]+"";
                }
                Gson gson = new Gson();
                String jsonorderlist = gson.toJson(orderList);
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

    String sanitizeString(String response)
    {
        return response.replace("\r\n<!-- Hosting24 Analytics Code -->\r\n<script type=\"text/javascript\" src=\"http://stats.hosting24.com/count.php\"></script>\r\n<!-- End Of Analytics Code -->\r\n","");
    }

    public void setOrderClickable(boolean b)
    {
        orderButton.setEnabled(b);
    }
}



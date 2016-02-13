package adobe.com.adobecafe;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class ReceiptActivity extends AppCompatActivity {

    ListView listView;
    TextView order_number;
    TextView total_cost;
    String sendString;
    RelativeLayout page_layout,wait_layout;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        order_number = (TextView)findViewById(R.id.order_number);

        page_layout = (RelativeLayout)findViewById(R.id.page_layout);
        wait_layout = (RelativeLayout)findViewById(R.id.wait_layout);
        total_cost = (TextView)findViewById(R.id.total_cost);
        context = this;

        wait_layout.setVisibility(View.INVISIBLE);
        page_layout.setVisibility(View.VISIBLE);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(getString(R.string.application), Context.MODE_PRIVATE);
        int ord = prefs.getInt(getString(R.string.order_number),23);
        order_number.setText("ORDER NO : " + ord++);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getString(R.string.order_number),ord);

        int tc = 0;
        String jsonOrderList = getIntent().getStringExtra("productlist");
        listView = (ListView)findViewById(R.id.listView);
        setTitle("Receipt");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>(){}.getType();
        ArrayList<Product> orderList = gson.fromJson(jsonOrderList, type);
        for(int i=0;i<orderList.size()-1;i++)
        {
            tc = tc + Integer.parseInt(orderList.get(i).price)*Integer.parseInt(orderList.get(i).quantity);
            //sendString = sendString + orderList.get(i).dish+";"+orderList.get(i).quantity+"|";
        }
        tc = tc + Integer.parseInt(orderList.get(orderList.size()-1).price)*Integer.parseInt(orderList.get(orderList.size()-1).quantity);
        //sendString = sendString + orderList.get(orderList.size()-1).dish+";"+orderList.get(orderList.size()-1).quantity;



        total_cost.setText("TOTAL COST : "+tc);
        final ReceiptListAdapter orderListAdapter = new ReceiptListAdapter(this,getItemId(orderList.size()),orderList);
        listView.setAdapter(orderListAdapter);

        /*RequestSingletonQue queue = RequestSingletonQue.getInstance(getApplicationContext());
        String url = "http://hackathon.netai.net/send_snacks.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        response = sanitizeString(response);
                        order_number.setText("ORDER NO : " + response);
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
                params.put("order", sendString);
                return params;
            }
        };
        queue.add(postRequest);*/

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
}

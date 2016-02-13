package adobe.com.adobecafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        String jsonOrderList = getIntent().getStringExtra("productlist");
        listView = (ListView)findViewById(R.id.listView);
        setTitle("Receipt");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>(){}.getType();
        ArrayList<Product> orderList = gson.fromJson(jsonOrderList, type);
        final ReceiptListAdapter orderListAdapter = new ReceiptListAdapter(this,getItemId(orderList.size()),orderList);
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

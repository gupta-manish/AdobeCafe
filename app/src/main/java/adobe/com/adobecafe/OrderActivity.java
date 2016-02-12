package adobe.com.adobecafe;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ArrayList<Product> a = new ArrayList<>();
        a.add(new Product("kjdlkjncads","wdwdwdwd","0"));
        a.add(new Product("kjdlkjncads", "wdwdwdwd", "0"));
        a.add(new Product("kjdlkjncads","wdwdwdwd","0"));
        a.add(new Product("kjdlkjncads","wdwdwdwd","0"));
        a.add(new Product("kjdlkjncads","wdwdwdwd","0"));
        a.add(new Product("kjdlkjncads","wdwdwdwd","0"));
        a.add(new Product("kjdlkjncads","wdwdwdwd","0"));
        a.add(new Product("kjdlkjncads","wdwdwdwd","0"));

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



package adobe.com.adobecafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LunchDinnerActivity extends AppCompatActivity {


    Button select;
    String values[];
    String[] comboMeal = {"Chapatti","Dal","Paneer","Salad","Raita","Gobi","Chicken","Jalebi"};
    String[] delightMeal = {"Kulche","Cholle Mattar","Jalebi","Raita","Salad"};
    String[] healthyMeal = {"Corn Salad","Bread","Veg Sandwich"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);
        final ListView listview = (ListView) findViewById(R.id.listView);
        select = (Button)findViewById(R.id.select);
        final String lunch_or_dinner = getIntent().getStringExtra("lunch_or_dinner");
        String menu = getIntent().getStringExtra("menu");

        setTitle(lunch_or_dinner + " " + menu);

        switch (menu)
        {
            case "combo" :
                values = comboMeal;
                break;
            case "healthy":
                values = healthyMeal;
                break;
            case "delight":
                values = delightMeal;
                break;
        }

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);

        listview.setAdapter(adapter);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Selected " + lunch_or_dinner + " menu", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
}

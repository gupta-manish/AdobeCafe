package adobe.com.adobecafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {

    Button order,lunch,dinner;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        order = (Button)findViewById(R.id.order);
        lunch = (Button)findViewById(R.id.lunch);
        dinner = (Button)findViewById(R.id.dinner);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order = new Intent("OrderActivity");
                startActivity(order);
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lunch = new Intent("LunchDinnerMenuChooseActivity");
                lunch.putExtra("lunch_or_dinner","lunch");
                startActivity(lunch);
            }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dinner = new Intent("LunchDinnerMenuChooseActivity");
                dinner.putExtra("lunch_or_dinner","dinner");
                startActivity(dinner);
            }
        });

    }
}

package adobe.com.adobecafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LunchDinnerMenuChooseActivity extends AppCompatActivity implements View.OnClickListener {

    Button combo,healthy,delight;
    String lunch_or_dinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_dinner_menu_choose);
        lunch_or_dinner = getIntent().getStringExtra("lunch_or_dinner");

        combo = (Button)findViewById(R.id.combo);
        healthy = (Button)findViewById(R.id.healthy);
        delight = (Button)findViewById(R.id.delight);

        combo.setOnClickListener(this);
        healthy.setOnClickListener(this);
        delight.setOnClickListener(this);


    }


    @Override
    public void onClick(View v)
    {
        String menu = "";
        int id = v.getId();
        switch (id)
        {
            case R.id.combo:
                menu = "combo";
                break;
            case R.id.healthy:
                menu = "healthy";
                break;
            case R.id.delight:
                menu = "delight";
                break;
        }
        Intent lunchDinner = new Intent("LunchDinnerActivity");
        lunchDinner.putExtra("lunch_or_dinner",lunch_or_dinner);
        lunchDinner.putExtra("menu",menu);
        startActivity(lunchDinner);
    }
}

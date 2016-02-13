package adobe.com.adobecafe;

/**
 * Created by manigupt on 12-Feb-16.
 */
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;


/**
 * Created by manish on 4/4/15.
 */
public class OrderListAdapter extends ArrayAdapter<Integer>
{
    private Context context;
    LayoutInflater layInf;
    private ArrayList<Product> productList;
    private int[] quantity;
    String response;
    public OrderListAdapter(Context context, Integer[] itemId,
                               ArrayList<Product> productList) {
        super(context, R.layout.activity_order, R.id.listView, itemId);
        // TODO Auto-generated constructor stub
        this.context = context;
        quantity = new int[productList.size()];
        this.productList = productList;
        layInf = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = layInf.inflate(R.layout.list_row_item, parent, false);
        }
        TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
        itemName.setText(productList.get(position).dish);

        TextView price = (TextView) convertView.findViewById(R.id.priceString);
        price.setText("Rs." + productList.get(position).price);

        final Button incQuantity = (Button) convertView
                .findViewById(R.id.incQuantity);
        final Button decQuantity = (Button) convertView
                .findViewById(R.id.decQuantity);


        final TextView quantityView = (TextView) convertView
                .findViewById(R.id.quantity);

        quantityView.setText(quantity[position] + "");

        incQuantity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                quantity[position]++;
                quantityView.setText(quantity[position] + "");

            }
        });

        decQuantity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(quantity[position]<=0)
                {
                    return;
                }
                quantity[position]--;
                quantityView.setText(quantity[position] + "");
            }
        });




        return convertView;

    }

    public int[] getQuantity()
    {
        return quantity;
    }


}

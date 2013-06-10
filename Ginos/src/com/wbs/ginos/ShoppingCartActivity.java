package com.wbs.ginos;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class ShoppingCartActivity extends Activity{
	Product p;
	static List<Product> list=new ArrayList<Product>();
	static String price;
	static float totalprice=0;
	ProductAdapter adapter;
	TextView tv;
	NumberFormat currency = NumberFormat.getCurrencyInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart);
		
		ListView listView = (ListView)findViewById(R.id.itemlist);
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		p = new Product(b.getString("productname"), b.getDouble("price"));
		list.add(p);
		
		
		adapter = new ProductAdapter(this, list);
		listView.setAdapter(adapter);
		
		totalprice+=b.getDouble("price");
		price = currency.format(totalprice);
		tv = (TextView)findViewById(R.id.totalprice);
		tv.setText("Total: "+price.toString());
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			   public void onItemClick(AdapterView<?> parent, View view,
			     int position, long id) {
			    // When clicked, show a toast with the TextView text
			    Product product = (Product) parent.getItemAtPosition(position);
			    product.toggleChecked();
			    adapter.notifyDataSetInvalidated();
			   }
			  });
        Button gohome = (Button)findViewById(R.id.gohome);        
		gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
	}

	
	
	public void removeFromCart(View v)
	{
		for(int i=list.size()-1; i>=0; i--) {
			
			if(list.get(i).selected) {
				totalprice-=list.get(i).price;
				list.remove(i);
			}
		}
		adapter.notifyDataSetChanged();
		price = currency.format(totalprice);
		tv.setText("Total: "+price);
		tv.refreshDrawableState();
	}


	public void checkout(View V)
	{
	//Implement your checkout view here	
	}
}
	
class ProductAdapter extends ArrayAdapter<Product>{
	
	private Context context;
	private List<Product> values;
	public ProductAdapter(Context context, List<Product> values) {
	    super(context, R.layout.item, values);
	    this.context = context;
	    this.values = values;
	  }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item, parent, false);
		TextView item = (TextView)rowView.findViewById(R.id.itemText);
		TextView price = (TextView)rowView.findViewById(R.id.itemprice);
		CheckBox box = (CheckBox)rowView.findViewById(R.id.checkedItem);
		item.setText(values.get(position).productname);
		price.setText(values.get(position).price.toString());
		box.setChecked(values.get(position).selected);
		return rowView;
	}
	
}
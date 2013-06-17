/**
 * Displaying the item information with Add to Cart Button. 
 */
package com.wbs.ginos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
 
public class SingleItemActivity extends ListActivity {
	protected boolean bool = false;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> selquesList;
    Intent intent;
    String singleItemID;
    String singleItemCat;
    
    TextView errorcode;
    private String url_all_selcats = "http://ginos.getfreehosting.co.uk/app//fetchsingleitem.php";
    
    
    
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_SELITEMS = "menu";
    private static final String TAG_newPID = "itemID";
    private static final String TAG_NAME = "itemname";
    private static final String TAG_INFO = "iteminfo";
    private static final String TAG_PRICE = "price";
    private static final String TAG_SMALLPRICE = "small";
    private static final String TAG_MEDIUMPRICE = "medium";
    private static final String TAG_LARGE = "large";
    private static final String TAG_xLARGE = "xlarge";
    
    private String price, name, smallprice, mediumprice, largeprice, xlargeprice;
    
    JSONArray selques = null;
 
    public String[] dataToPass(String name, String... price)
    {
    	RadioGroup radio = (RadioGroup)findViewById(R.id.group1);
    	int checked = radio.getCheckedRadioButtonId();
    	
    	String pricetopass = "";
    	switch(checked)
    	{
    	case R.id.small: pricetopass = price[0];name+= " (small)";
    	break;
    	case R.id.medium: pricetopass = price[1];name+= " (medium)";
    	break;
    	case R.id.large: pricetopass = price[2];name+= " (large)";
    	break;
    	case R.id.xlarge: pricetopass = price[3];name+= " (extra large)";
    	break;
    	}
    	String s[] = {name, pricetopass};
    	return s;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item);
        Log.i("SingleItemActivity","Inside oncreate");
        selquesList = new ArrayList<HashMap<String, String>>();
       

        new LoadAllSelques().execute();
        ListView lv = getListView();
        Button gohome = (Button)findViewById(R.id.gohome);        
        		gohome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                });
        
        Button cartbtn =(Button)findViewById(R.id.cartbtn);

        cartbtn.setOnClickListener(new Button.OnClickListener(){
        	@Override
        	 public void onClick(View v) {
        	Intent i = new Intent(getApplicationContext(), ShoppingCartActivity.class);
        	Bundle b = new Bundle();
        	String s[] = new String[2];
        	if(!bool){
        	s = dataToPass(name, smallprice, mediumprice, largeprice, xlargeprice);
        	}
        	else
        	{
        		s[0] = name;
        		s[1] = price;
        	}
        	b.putString("productname", s[0]);
        	Log.i("value of price: ", s[1]);
        	b.putDouble("price", Double.valueOf(s[1]));
        	i.putExtras(b);
        	startActivity(i);
        	}});
        	lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
            	//Display more information with add to cart option.
            }
        });
    }
    
    class LoadAllSelques extends AsyncTask<String, String, String> {
    	Intent intent = getIntent();
    	int check = intent.getIntExtra("int_value", 0);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SingleItemActivity.this);
            pDialog.setMessage("Loading items. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
		protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            intent = getIntent();
            singleItemID = intent.getExtras().getString("TAG_SINGLEITEMID");
            singleItemCat=intent.getExtras().getString("TAG_SINGLEITEMCAT");
            params.add(new BasicNameValuePair("itemID",singleItemID));
            params.add(new BasicNameValuePair("catID",singleItemCat));//<<<< add here
            JSONObject json = jParser.makeHttpRequest(url_all_selcats, "GET", params);
            
            Log.d("All Items: ", json.toString());
 
            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	selques = json.getJSONArray(TAG_SELITEMS);
                    for (int i = 0; i < selques.length(); i++) {
                        JSONObject c = selques.getJSONObject(i);
                        String id = c.getString(TAG_newPID);
                        name = c.getString(TAG_NAME);
                        String info = c.getString(TAG_INFO);
                     	price = c.getString(TAG_PRICE);
                     	smallprice = c.getString(TAG_SMALLPRICE);
                        mediumprice = c.getString(TAG_MEDIUMPRICE);
                        largeprice = c.getString(TAG_LARGE);
                        xlargeprice = c.getString(TAG_xLARGE);
                        	
                        	 HashMap<String, String> map = new HashMap<String, String>();
                             // adding each child node to HashMap key => value
                             map.put(TAG_newPID, id);
                             map.put(TAG_NAME, "Name: "+name);
                             map.put(TAG_INFO, "Description: "+info);
                             map.put(TAG_PRICE, "Price: "+price);
                             map.put(TAG_SMALLPRICE, "Price of Small: "+ smallprice);
                             map.put(TAG_MEDIUMPRICE, "Price of Medium: "+ mediumprice);
                             map.put(TAG_LARGE, "Price of Large: "+ largeprice);
                             map.put(TAG_xLARGE, "Price of xLarge: "+ xlargeprice);
                        // adding HashList to ArrayList
                        selquesList.add(map);

                    }
                } else {
                   // nothing was found.
                }
                
                
                	//finish();
                	
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        @Override
		protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
				public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                	
                    if(check<=2)
                    {
                    	Log.i("Check Value","The check value is" + check);
                    	Log.i("Adapter:"," Category Value is less than 2");
                    ListAdapter adapter = new SimpleAdapter(
                    		SingleItemActivity.this, selquesList,
                            R.layout.single_item_layout, new String[] { TAG_newPID,
                                    TAG_NAME, TAG_INFO,TAG_SMALLPRICE,TAG_MEDIUMPRICE,TAG_LARGE,TAG_xLARGE },
                            new int[] { R.id.itemid, R.id.itemname, R.id.iteminfo,R.id.smallprice, R.id.mediumprice, R.id.largeprice,R.id.xlargeprice});
                    // updating listview
                    bool = false;
                    setListAdapter(adapter);
                    }
                    else
                    {
                    	ListAdapter adapter2 = new SimpleAdapter(
                    		SingleItemActivity.this, selquesList,
                            R.layout.singleitemlayout, new String[] { TAG_newPID,
                                    TAG_NAME, TAG_INFO, TAG_PRICE },
                            new int[] { R.id.itemid, R.id.itemname, R.id.iteminfo, R.id.price});
                    	setListAdapter(adapter2);
                    bool = true;
                    }
                }
            });
        }
    }
}
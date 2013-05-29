/**
 * Displaying the item information with Add to Cart Button. 
 */
package com.wbs.ginos;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import android.widget.SimpleAdapter;
import android.widget.TextView;
 
public class SingleItemActivity extends ListActivity {
 

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
    
    
    JSONArray selques = null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item);
        
        selquesList = new ArrayList<HashMap<String, String>>();
        
  		//Intent selectedintent = getIntent();
        //String prevcatid = selectedintent.getExtras().getString("pathid");
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
        
        Button ansbtn =(Button)findViewById(R.id.cartbtn);
        ansbtn.setOnClickListener(new Button.OnClickListener(){
        	@Override
        	 public void onClick(View v) {
        	//View Cart Activity to be called here!
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
                        String name = c.getString(TAG_NAME);
                        String info = c.getString(TAG_INFO);
                     	String price = c.getString(TAG_PRICE);
                     	String smallprice = c.getString(TAG_SMALLPRICE);
                        String mediumprice = c.getString(TAG_MEDIUMPRICE);
                        String largeprice = c.getString(TAG_LARGE);
                        String xlargeprice = c.getString(TAG_xLARGE);
                        	
                        	 HashMap<String, String> map = new HashMap<String, String>();
                             // adding each child node to HashMap key => value
                             map.put(TAG_newPID, id);
                             map.put(TAG_NAME, name);
                             map.put(TAG_INFO, info);
                             map.put(TAG_PRICE, price);
                             map.put(TAG_SMALLPRICE, smallprice);
                             map.put(TAG_MEDIUMPRICE, mediumprice);
                             map.put(TAG_LARGE, largeprice);
                             map.put(TAG_xLARGE, xlargeprice);
                            
                        // adding HashList to ArrayList
                        selquesList.add(map);
                        
                    }
                } else {
                   // nothing was found.
                }
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
                    setListAdapter(adapter);
                    }
                    else
                    {
                    ListAdapter adapter2 = new SimpleAdapter(
                    		SingleItemActivity.this, selquesList,
                            R.layout.single_item_layout, new String[] { TAG_newPID,
                                    TAG_NAME, TAG_INFO, TAG_PRICE },
                            new int[] { R.id.itemid, R.id.itemname, R.id.iteminfo, R.id.price});
                    setListAdapter(adapter2);
                    }
                    
                }
            });
        }
    }
}
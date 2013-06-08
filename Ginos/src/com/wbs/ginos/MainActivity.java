/**
 * MainActivity for the navigation purpose.
 */
package com.wbs.ginos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	/*Declare Buttons*/
	Button viewMenu, eShop, loyalty, aboutUs;
	
	/*
	 * Beginning of Declaration of Utilities
	 */
	ConnectionDetector cd;
    AlertDialogManager alert = new AlertDialogManager(); // Alert dialog manager
    /*
     * End of Utilities
     */
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		cd = new ConnectionDetector(getApplicationContext());
        // Check if connected to internet or not.
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(MainActivity.this,
                    "Awww Snap!",
                    "Seems like you are not connected to the Internet. Please refer to your network settings.", false);
            // stop executing code by return
            return;
        }        
        
		/* findViewById method for buttons.  */
		viewMenu = (Button)findViewById(R.id.placeorderBtn);
		eShop = (Button)findViewById(R.id.eshopBtn);
		loyalty = (Button)findViewById(R.id.loyaltyBtn);
		aboutUs = (Button)findViewById(R.id.aboutBtn);
		
		viewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	viewMenu.setBackgroundResource(R.drawable.placeorderclick);
                Intent i = new Intent(getApplicationContext(), FetchCategoriesActivity.class);
                startActivity(i);
            }
        });
		
		eShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	eShop.setBackgroundResource(R.drawable.eshopclick);
                Intent i = new Intent(getApplicationContext(), EShopActivity.class);
                startActivity(i);
            }
        });
		
		loyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	loyalty.setBackgroundResource(R.drawable.loyaltyclick);
                Intent i = new Intent(getApplicationContext(), LoyaltyActivity.class);
                startActivity(i);
            }
        });
		
		aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	aboutUs.setBackgroundResource(R.drawable.aboutusclick);
                Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);
            }
        });
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		viewMenu.setBackgroundResource(R.drawable.placeorder);
		eShop.setBackgroundResource(R.drawable.eshop);
		loyalty.setBackgroundResource(R.drawable.loyalty);
		aboutUs.setBackgroundResource(R.drawable.aboutus);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	


}

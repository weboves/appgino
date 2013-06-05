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
	Button viewMenu;
	
	/*
	 * Beginning of Declaration of Utilities
	 */
	
	ConnectionDetector cd;
	// Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();
	
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
		
		
		viewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FetchCategoriesActivity.class);
                startActivity(i);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

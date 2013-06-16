package com.wbs.ginos;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalPayment;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class paypal extends FragmentActivity implements OnClickListener  {

	

	
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		PayPal pp = PayPal.getInstance();
		
		pp = PayPal.initWithAppID(this, "APP-80W284485P519543T", PayPal.ENV_NONE);

		CheckoutButton launchPayPalButton = pp.getCheckoutButton(this, PayPal.BUTTON_278x43, CheckoutButton.TEXT_PAY);
		RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(android.view.WindowManager.LayoutParams.WRAP_CONTENT, android.view.WindowManager.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.bottomMargin = 10;
		launchPayPalButton.setLayoutParams(params);
		launchPayPalButton.setOnClickListener((OnClickListener) this);
		((RelativeLayout)findViewById(R.id.buttonname)).addView(launchPayPalButton);

		
	}
	
	public void onClick(View v) {
		PayPalPayment newPayment = new PayPalPayment();
		newPayment.getSubtotal();
		newPayment.setCurrencyType("USD");
		newPayment.setRecipient("my@email.com");
		newPayment.setMerchantName("My Company");
		Intent paypalIntent = PayPal.getInstance().checkout(newPayment, this);
		this.startActivityForResult(paypalIntent, 1);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(resultCode) {
		case Activity.RESULT_OK:
		//The payment succeeded
		String payKey = data.getStringExtra(PayPalActivity.EXTRA_PAY_KEY);
		//Tell the user their payment succeeded
		break;
		case Activity.RESULT_CANCELED:
		//The payment was canceled
		//Tell the user their payment was canceled
		break;
		case PayPalActivity.RESULT_FAILURE:
		//The payment failed -- we get the error from the EXTRA_ERROR_ID and EXTRA_ERROR_MESSAGE
		String errorID = data.getStringExtra(PayPalActivity.EXTRA_ERROR_ID);
		String errorMessage = data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
		//Tell the user their payment was failed.
		}
		}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}

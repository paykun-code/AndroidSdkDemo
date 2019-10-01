package com.techriz.paykunandroidintegration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paykun.sdk.PaykunApiCall;
import com.paykun.sdk.eventbus.Events;
import com.paykun.sdk.eventbus.GlobalBus;
import com.paykun.sdk.helper.PaykunHelper;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class Pay extends AppCompatActivity {

    CardView okay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        EditText mobno = findViewById(R.id.mobileno);
        EditText productName = findViewById(R.id.productname);
        EditText amount = findViewById(R.id.amount);

        okay = findViewById(R.id.okay);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String accessTokenSandbox = Prefs.getString("accesstoken" , null);
                String merchantIdSandbox = Prefs.getString("merchantid" , null);


                if (!TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(mobno.getText().toString()) && !TextUtils.isEmpty(productName.getText().toString()) && !TextUtils.isEmpty(amount.getText().toString())) {


                    JSONObject object = new JSONObject();
                    try {
                        object.put("merchant_id",merchantIdSandbox);
                        object.put("access_token",accessTokenSandbox);
                        object.put("customer_name",name.getText().toString());
                        object.put("customer_email",email.getText().toString());
                        object.put("customer_phone",mobno.getText().toString());
                        object.put("product_name",productName.getText().toString());
                        object.put("order_no",System.currentTimeMillis()); // order no. should have 10 to 30 character in numeric format
                        object.put("amount",amount.getText().toString());  // minimum amount should be 10
                        object.put("isLive",true); // need to send false if you are in sandbox mode
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    new PaykunApiCall.Builder(Pay.this).sendJsonObject(object); // Paykun api to initialize your payment and send info.

                } else
                    Toast.makeText(Pay.this, "Please fill in all the necessary details.", Toast.LENGTH_LONG).show();

            }
        });


    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getResults(Events.PaymentMessage message) {
        if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SUCCESS)){
            // do your stuff here
            // message.getTransactionId() will return your failed or succeed transaction id
            /* if you want to get your transaction detail call message.getTransactionDetail()
             *  getTransactionDetail return all the field from server and you can use it here as per your need
             *  For Example you want to get Order id from detail use message.getTransactionDetail().order.orderId */
            if(!TextUtils.isEmpty(message.getTransactionId())) {
                Toast.makeText(Pay.this, "Your Transaction is succeed with transaction id : "+message.getTransactionId() , Toast.LENGTH_SHORT).show();
                Log.v(" order id "," getting order id value : "+message.getTransactionDetail().order.orderId);
            }
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_FAILED)){
            // do your stuff here
            Toast.makeText(Pay.this,"Your Transaction is failed",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SERVER_ISSUE)){
            // do your stuff here
            Toast.makeText(Pay.this,PaykunHelper.MESSAGE_SERVER_ISSUE,Toast.LENGTH_SHORT).show();
        }else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_ACCESS_TOKEN_MISSING)){
            // do your stuff here
            Toast.makeText(Pay.this,"Access Token missing",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_MERCHANT_ID_MISSING)){
            // do your stuff here
            Toast.makeText(Pay.this,"Merchant Id is missing",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_INVALID_REQUEST)){
            Toast.makeText(Pay.this,"Invalid Request",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_NETWORK_NOT_AVAILABLE)){
            Toast.makeText(Pay.this,"Network is not available",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Register this activity to listen to event.
        GlobalBus.getBus().register(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Unregister from activity
        GlobalBus.getBus().unregister(this);
    }


}

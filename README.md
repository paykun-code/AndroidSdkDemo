# AndroidSdkDemo
this is sample demo which guide to implement Paykun sdk into you own application
you can make payment from your android application for both live and testing(sandbox) purpose.

Below is guide to implement paykun sdk into your app.

#	How to install SDK in your app?

We have distributed our SDK via Maven Central Repositery.You can add paykun sdk directly to your build.gradle file in dependency section using below line:

implementation 'com.paykun.sdk:paykun-checkout-lib:1.0.5'	


#	How to implement SDK into your android app?

First you need to get your merchant id and access token from paykun dashboard.for testing purpose you need to login in paykun dashboard using sandbox mode and get your merchant Id and access token.

You need to pass merchant id,access token,customer name,customer email,customer phone,product name,order no and amount to SDK.

Create json object with following key and values.


#	For testing environment(sandbox)

If you need it for testing purpose you can use our sandbox mode by sending “isLive” parameter to false.when you send “isLive” to false we will consider it as for testing purpose and will not charge for it otherwise sent it as true.
If you need it for testing purpose you should login in pykun dashboard using sandbox mode and get your testing merchant Id and Device Api key(access token).
Kindly follow below step in paykun dashboard to create your sandbox account from live account.

1.	Go to Merchant account(Live)
2.	Navigate to Account drop down(Top Right corner) and select "Test Mode"
3.	Select "Activate Sandbox Account" to activate your sandbox account
4.	After activating sandbox account,you can click on "Sandbox Login" button to go to sandbox dashboard and use generated username and password login.
5.	Now you have to generate your Device API key from sandbox account from Setting-Security tab.
6.	You can get you Merchant Id clicking on profile icon from top right corner.
7.	Now you have to use this API key and Merchant Id in your Demo project for testing purpose.
Note: Remember that you can login in Sandbox account from your Live account.

you can use testing card no ”4111 1111 1111 1111 ” ,any future expiry date and any valid cvv no.

# Pass created json object to SDK using follwing method.

new PaykunApiCall.Builder(activity).sendJsonObject(object);	                                     

we have used Event Bus to receive the result from sdk after or during the checkout process.so kindly follow below step to receive result.

You need to register subscriber in the event bus with register().

You also need to unregister subscriber in onStop() method of activity lifecycle

Implement the getResult() to listen the event from SDK with following way.

@Subscribe(sticky = true, threadMode = ThreadMode.MAIN) 
public void getResults(Events.PaymentMessage message) {
    if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SUCCESS)){
      // do your stuff here
      // message.getTransactionId() will return your failed or succeed transaction id
      if(!TextUtils.isEmpty(message.getTransactionId())) {

        Toast.makeText(MainActivity.this, "Your Transaction is succeed with transaction id : "+message.getTransactionId(),                       Toast.LENGTH_SHORT).show();
      }
    }
    else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_FAILED)){
        // do your stuff here
        Toast.makeText(MainActivity.this,"Your Transaction is failed", Toast.LENGTH_SHORT).show();
    }
    else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SERVER_ISSUE)){
        // do your stuff here

        Toast.makeText(MainActivity.this,PaykunHelper.MESSAGE_SERVER_ISSUE,Toast.L ENGTH_SHORT).show();
    }
    else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_ACCESS_TOKEN_MISSING)){
    
        // do your stuff here 
        Toast.makeText(MainActivity.this,"Access Tokenmissing",Toast.LENGTH_SHORT).show();
    }
    else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_MERCHANT_ID_ MISSING)){
    
        // do your stuff here 
        Toast.makeText(MainActivity.this,"Merchant Id ismissing",Toast.LENGTH_SHORT).show();
    }
    else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_INVALID_REQU EST)){
    
        Toast.makeText(MainActivity.this,"Invalid Request",Toast.LENGTH_SHORT).show();
    }
}



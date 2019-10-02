

# How to integrate Paykun in an Android app?

Integrating Paykun payment gateway and payment links in an Android app is just a matter of 10 minutes. Add the SDK, add some code and you're good to go! To help you we have a YouTube tutorial [here](https://youtu.be/j9ZQhz-laaM)

Get the demo app from here - [Paykun Demo Android App](http://www.mediafire.com/file/0x5yoypi7eu9c6x/app-debug.apk/file)

#	How to install SDK in your app?

We have distributed our SDK via Maven Central Repositery. You can add our latest paykun SDK directly to your build.gradle file in dependency section using below line:

**implementation 'com.paykun.sdk:paykun-checkout-lib:1.1.13'**


#	How to implement SDK into your android app?

First you need to get your merchant id and access token from Paykun dashboard.for testing purpose you need to login in Paykun dashboard using sandbox mode and get your merchant Id and access token.

You need to pass merchant id,access token,customer name,customer email,customer phone,product name,order no and amount to SDK.
Remember that Order Number should be always unique.duplicate Order Number will cause invalid request.

Create Json object with following key and values.


#	Test your Integration (sandbox)

If you need it for testing purpose you can use our sandbox mode by sending “isLive” parameter to false.when you send “isLive” to false we will consider it as for testing purpose and will not charge for it otherwise sent it as true.
If you need it for testing purpose you should login in Paykun dashboard using sandbox mode and get your testing merchant Id and Device API key(access token).
Kindly follow below step in paykun dashboard to create your sandbox account from live account.

also change applicationId from build.gradle file to "com.paykunsandbox" for testing purpose and for production use applicationId
"com.paykunsandbox.live".

1.	Go to Merchant account(Live)
2.	Navigate to Account drop down(Top Right corner) and select "Test Mode"
3.	Select "Generate Sandbox Account" to activate your sandbox account
4.	After activating sandbox account,you can click on "Sandbox Login" button to go to sandbox dashboard and use generated username and password login.
5.	Now you have to generate your Device API key from sandbox account from Setting-Security tab.
6.	You can get you Merchant Id clicking on profile icon from top right corner.
7.	Now you have to use this API key and Merchant Id in your Demo project for testing purpose.
Note: Remember that you can login in Sandbox account from your Live account.

> You can use testing card no ”4111 1111 1111 1111 ” ,any future expiry
> date and any valid cvv no.

#	For Live environment

If you want to do real transaction than set "isLive" parameter to true and use your Live MerchantId and AccessToken from Paykun dashboard.

#	Getting result after successfull or failed transaction in your Android Class or Activity

You have to register and unregister GlobalBus in you activity or fragment class by following way.

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
   
Another step is to Subscribe getResult method by following way.

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getResults(Events.PaymentMessage message) {
        if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SUCCESS)){
            // do your stuff here
            // message.getTransactionId() will return your failed or succeed transaction id
            /* if you want to get your transaction detail call message.getTransactionDetail()
            *  getTransactionDetail return all the field from server and you can use it here as per your need
            *  For Example you want to get Order id from detail use message.getTransactionDetail().order.orderId */
            
            if(!TextUtils.isEmpty(message.getTransactionId())) {
                Toast.makeText(MainActivity.this, "Your Transaction is succeed with transaction id : "+message.getTransactionId(),
                Toast.LENGTH_SHORT).show();
            }
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_FAILED)){
            // do your stuff here
            Toast.makeText(MainActivity.this,"Your Transaction is failed",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_SERVER_ISSUE)){
            // do your stuff here
            Toast.makeText(MainActivity.this,PaykunHelper.MESSAGE_SERVER_ISSUE,Toast.LENGTH_SHORT).show();
        }else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_ACCESS_TOKEN_MISSING)){
            // do your stuff here
            Toast.makeText(MainActivity.this,"Access Token missing",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_MERCHANT_ID_MISSING)){
            // do your stuff here
            Toast.makeText(MainActivity.this,"Merchant Id is missing",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_INVALID_REQUEST)){
            Toast.makeText(MainActivity.this,"Invalid Request",Toast.LENGTH_SHORT).show();
        }
        else if(message.getResults().equalsIgnoreCase(PaykunHelper.MESSAGE_NETWORK_NOT_AVAILABLE)){
            Toast.makeText(MainActivity.this,"Network is not available",Toast.LENGTH_SHORT).show();
        }
    }

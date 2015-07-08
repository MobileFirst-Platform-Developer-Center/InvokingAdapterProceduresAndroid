/*
 *
    COPYRIGHT LICENSE: This information contains sample code provided in source code form. You may copy, modify, and distribute
    these sample programs in any form without payment to IBM® for the purposes of developing, using, marketing or distributing
    application programs conforming to the application programming interface for the operating platform for which the sample code is written.
    Notwithstanding anything to the contrary, IBM PROVIDES THE SAMPLE SOURCE CODE ON AN "AS IS" BASIS AND IBM DISCLAIMS ALL WARRANTIES,
    EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, ANY IMPLIED WARRANTIES OR CONDITIONS OF MERCHANTABILITY, SATISFACTORY QUALITY,
    FITNESS FOR A PARTICULAR PURPOSE, TITLE, AND ANY WARRANTY OR CONDITION OF NON-INFRINGEMENT. IBM SHALL NOT BE LIABLE FOR ANY DIRECT,
    INDIRECT, INCIDENTAL, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR OPERATION OF THE SAMPLE SOURCE CODE.
    IBM HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS OR MODIFICATIONS TO THE SAMPLE SOURCE CODE.

 */
package com.worklight.invokingadapterproceduresandroid;

import java.net.URI;
import java.net.URISyntaxException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLResourceRequest;

public class MainActivity extends Activity {
	private Button buttonConnect = null;
	private Button buttonInvoke = null;
	private static TextView textView = null;
	private static MainActivity _this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        _this = this;

        buttonConnect = (Button)findViewById(R.id.buttonConnect);
        buttonInvoke = (Button)findViewById(R.id.buttonInvoke);
        textView = (TextView)findViewById(R.id.textView);
        
		final WLClient client = WLClient.createInstance(this);
        
        buttonConnect.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				updateTextView("Connecting...");
				client.connect(new MyConnectListener());
			}
		});
        
        buttonInvoke.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				updateTextView("Invoking procedure...");
				
				try {
					//Define the URI of the resource. For a JS HTTP adapter: /adapters/{AdapterName}/{ProcedureName}
					URI adapterPath = new URI("/adapters/RSSReader/getFeeds");
					
					//Create WLResourceRequest object. Choose the HTTP Method (GET, POST, etc).
                    WLResourceRequest request = new WLResourceRequest(adapterPath,WLResourceRequest.GET);
					
                    //For JavaScript-based adapters, use the parameter name "params" to set an array of parameters
                    //For Java adapters or other resources, you may use setQueryParameter for each parameter
                    request.setQueryParameter("params","['technology']");
                    
                    //There are multiple ways to handle the response. Here a listener is used.
                    request.send(new MyInvokeListener());
                    
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
			}
		});
	}

	public static void updateTextView(final String str){
    	Runnable run = new Runnable() {			
			public void run() {
		    	textView.setText(str);				
			}
		};
		_this.runOnUiThread(run);
    }

}

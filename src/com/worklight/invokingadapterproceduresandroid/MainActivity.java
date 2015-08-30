/**
* Copyright 2015 IBM Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
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
					URI adapterPath = new URI("/adapters/RSSReader/getFeed");
					
					//Create WLResourceRequest object. Choose the HTTP Method (GET, POST, etc).
                    WLResourceRequest request = new WLResourceRequest(adapterPath,WLResourceRequest.GET);
					
                    //For JavaScript-based adapters, use the parameter name "params" to set an array of parameters
                    //For Java adapters or other resources, you may use setQueryParameter for each parameter
                    request.setQueryParameter("params","['MobileFirst_Platform']");
                    
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

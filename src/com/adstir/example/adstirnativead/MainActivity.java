/*
Copyright (c) 2014, UNITED, inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.adstir.example.adstirnativead;

import java.util.Random;

import com.ad_stir.nativead.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private AdstirNativeAd nativead;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int no = new Random().nextInt(2);
		if(no == 1){
			setContentView(R.layout.activity_main);
		}else{
			setContentView(R.layout.activity_main2);
		}
		
		TextView adtextView = (TextView) this.findViewById(R.id.nativead_adtext);
		com.ad_stir.common.Log.setLogLevel(android.util.Log.DEBUG);
		
		// インスタンス生成
		nativead = new AdstirNativeAd(this,"MEDIA-XXXXXXXXX",X);
		// ガイドラインで規定されているスポンサー表記を、実装した通りに設定してください。
		nativead.setSponsoredText(adtextView.getText().toString());
		// 広告に必要な要素を要求するパラメーターを設定
		nativead.setTitleLength(20);
		nativead.setDescriptionLength(30);
		nativead.setImage(true);
		// 広告レスポンスを受け取るListenerを設定
		nativead.setListener(new AdstirNativeAdListener(){
			public void onReceive(final AdstirNativeAdResponse response){
				// 広告レスポンスが正常に取得できた時に行う処理を実装
				// AdstirNativeAdListenerのメソッドはすべてバックグラウンドスレッドで動作します。
				runOnUiThread(new Runnable(){
					public void run(){
						TextView titleView = (TextView) findViewById(R.id.nativead_title);
						titleView.setText(response.getTitle());
						TextView descriptionView = (TextView) findViewById(R.id.nativead_desc);
						descriptionView.setText(response.getDescription());
						ImageView imageView = (ImageView) findViewById(R.id.nativead_image);
						response.bindImageToImageView(MainActivity.this, imageView);
						View clickareaView = findViewById(R.id.nativead_clickarea);
						clickareaView.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								// 広告がクリックされたときはclickを呼び出してください
								response.click();
							}
						});
					}
				});
				// 広告を表示するときにimpressionを実装
				response.impression();
			}
			public void onFailed(){
				// 広告レスポンスが正常に取得できなかった時に行う処理を実装
			}
		});
		// 広告を要求
		nativead.getAd();
		
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		nativead.destroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

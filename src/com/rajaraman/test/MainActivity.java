package com.rajaraman.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import com.rajaraman.jschartdemo.R;

public class MainActivity extends Activity implements OnClickListener {

    WebView wv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	Button button = (Button) findViewById(R.id.button1);
	// button.setOnClickListener();

	button.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		String str = getJsonContentFromFile();
		wv.loadUrl("javascript:getJsonData(" + str + ")");
	    }
	});

	// Create the web-view
	wv = (WebView) findViewById(R.id.webView1);
	wv.getSettings().setJavaScriptEnabled(true);
	// Creates the interface "Android". This class can now be referenced in
	// the HTML file.
	wv.addJavascriptInterface(new JsObject(this), "JsObject");
	wv.setWebChromeClient(new WebChromeClient());
	wv.loadUrl("file:///android_asset/index.html");
	// wv.loadData("", "text/html", null);

    }

    private String getJsonContentFromFile() {

	// Another way of reading from file (assuming json.txt is under res/raw
	// folder)
	// InputStream inputStream = getResources().openRawResource(R.raw.json);
	InputStream inputStream = null;
	String jsonString = null;

	try {
	    inputStream = getApplicationContext().getAssets().open("json.txt");

	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	    int i;

	    i = inputStream.read();

	    while (i != -1) {
		byteArrayOutputStream.write(i);
		i = inputStream.read();
	    }

	    inputStream.close();

	    jsonString = byteArrayOutputStream.toString();

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	Log.d("Test", jsonString);

	return jsonString;
    }

    protected void onResume() {

	super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }

    public class JsObject {

	Context context;

	JsObject(Context c) {
	    context = c;
	}

	@JavascriptInterface
	public String toString() {
	    return "JsObject";
	}
    }

    @Override
    public void onClick(View view) {
	// super.onClick(view);

	Log.d("Test", "Inside onClick");

	switch (view.getId()) {
	case R.id.button1:
	    wv.loadUrl("javascript:getJsonData(\"data from native\")");
	    break;

	default:
	    // code..
	    break;

	}
    }
}

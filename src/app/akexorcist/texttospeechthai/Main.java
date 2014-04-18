package app.akexorcist.texttospeechthai;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity {
	final static String VAJA_TTS_ENGINE = "com.spt.tts.vaja";
	final static String SVOX_TTS_ENGINE = "com.svox.classic";
	
	TextToSpeech tts;
	EditText et;
	Button btn;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		checkTTSEngineInstalled(SVOX_TTS_ENGINE);
		
		et = (EditText)findViewById(R.id.editText);
		
		btn = (Button)findViewById(R.id.button);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checkTTSEngineInstalled(SVOX_TTS_ENGINE);
				if(tts != null)
					tts.speak(et.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
			}
		});
	}
	
	public void onDestroy() {
		super.onDestroy();
		if(tts != null)
			tts.shutdown();
	}

	public void checkTTSEngineInstalled(String packageName) {
		boolean isSvoxInstalled = isAppInstalled(packageName);
		if(isSvoxInstalled) {
			if(tts == null) 
				tts = new TextToSpeech(Main.this, null, packageName);
		} else if(!isSvoxInstalled){
			Toast.makeText(getApplicationContext()
					, "กรุณาติดตั้ง SVOX Text-to-Speech Engine"
					, Toast.LENGTH_LONG).show();
			Intent svoxIntent = new Intent(Intent.ACTION_VIEW);
			svoxIntent.setData(Uri.parse("market://details?id=" + packageName));
	        startActivity(svoxIntent);
		}
	}
	
	public boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
        	pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        	app_installed = true;
        } catch (PackageManager.NameNotFoundException e) { }
        return app_installed ;
	}
}

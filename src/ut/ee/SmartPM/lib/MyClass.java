package ut.ee.SmartPM.lib;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class MyClass implements LibInterface{

	private final String libName = "Noize";
	private final String libType = "String";
	List<rulesObject<Double, Double, String>> volList = new ArrayList<rulesObject<Double, Double, String>>();
	
	/* constants */
    private static final int POLL_INTERVAL = 500;
   
    private Handler mHandler = new Handler();

    /* sound data source */
    private Detect_noise mSensor;
    private TextView mAutoLabel;
	    
   	@Override
	public String useMyLib(Context context, TextView mAutoLabel, String rules) {
   		this.mAutoLabel = mAutoLabel;
		new parseXML(volList).execute(rules);
		mSensor = new Detect_noise();
        mSensor.start();
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
		return null;
   	}

	private void updateDisplay(double status) {
		
		Boolean isListed = false;
	    for (rulesObject<Double, Double, String> rulesObject : volList) {
			if ((status > rulesObject.getLow()) && (status < rulesObject.getHigh())) {
				mAutoLabel.setText(rulesObject.getName());
				isListed = true;
				return;
			}
		}
	    if(!isListed){
	    	String outVol = "NotMapped level: " + status;
	    	mAutoLabel.setText(outVol);
	    }
	        mAutoLabel.setText(String.valueOf(status));
	}
		
	
	private Runnable mPollTask = new Runnable() {
        public void run() {
        	if(mAutoLabel.isShown() == false){
				Log.d("EXIT","autolable deleted");
				mHandler.removeCallbacks(mPollTask);
				mSensor.stop();
				Thread.currentThread().interrupt();
				Log.d("EXIT","Mic released");
        	} else {
                double amp = mSensor.getAmplitude();
                updateDisplay(amp);
                mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        	}
        }
	};

	@Override
	public String getName() {
		return libName;
	}

	@Override
	public String getType() {
		return libType;
	}

}

package ut.ee.SmartPM.lib;

import java.io.IOException;

import android.media.MediaRecorder;
import android.util.Log;

public class Detect_noise {
    // This file is used to record voice

    private MediaRecorder mRecorder = null;

    public void start() {
           
    	if (mRecorder == null) {
            	
                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile("/dev/null"); 
                    
                    try {
						mRecorder.prepare();
						mRecorder.start();
					} catch (IllegalStateException e) {
						Log.d("DetectNoizeLib State: ", "" + e.getMessage());
					} catch (IOException e) {
						Log.d("DetectNoizeLib IO", "" + e.getMessage());
					}
					
                   
            }
    }
            
    public double getAmplitude() {
            if (mRecorder != null)
                    return  20.0 * Math.log10(mRecorder.getMaxAmplitude()/2.7);
            else
                    return 0;

    }
	
}

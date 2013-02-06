/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.sound;

/**
 * A Clip Player that is used in SwingWT's JApplet to play clip.
 * The clip-file is read and loaded the first time the play method is invoked
 * 
 * 
 *  
 *  @TODO: Fix  the file/url source. Releasing it's resources. Implement the stop and loop methods. Support for other audio-formats
 *  @TODO: So that this class may be used for other purpuses.  ( AudioPlayer ). 
 *  
 *  @author Niklas Gustafsson
 *  @date 2006-08-15
 *  
 */
import swingwt.applet.*;

import java.net.*;
import java.io.*;


import javax.sound.sampled.*;

public class AudioClipPlayer implements LineListener,  AudioClip
{
	/**
	 * The audio-stream's  file/url
	 */
	private URL url = null;
	private AudioInputStream	audioInputStream = null;
	private Clip		clip;
	/**
	 *  Is  true  when the clip is playing
	 */   
	private boolean clipIsPlaying = false;
	/**
	 * Is true when the audio-stream and clip is loading
	 */
	private boolean clipIsLoading = false;
	
	public AudioClipPlayer(URL url)
	
	
	{
		this.url = url;
	}
/**
 * Load the audio-stream and convert it if it is the wrong format.
 * then optain a clip from the AudioSystem.
 *
 */	
	private void loadInputStream()
	{
		clipIsLoading = true;
		File clipFile = new File(url.getFile());
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(clipFile);
		}
		catch(Exception e)
		{
		}
		if (audioInputStream != null)
		{
			AudioFormat	format = audioInputStream.getFormat();
			if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
                    (format.getEncoding() == AudioFormat.Encoding.ALAW)) 
            {
                    AudioFormat tmp = new AudioFormat(
                                              AudioFormat.Encoding.PCM_SIGNED, 
                                              format.getSampleRate(),
                                              format.getSampleSizeInBits() * 2,
                                              format.getChannels(),
                                              format.getFrameSize() * 2,
                                              format.getFrameRate(),
                                              true);
                    audioInputStream = AudioSystem.getAudioInputStream(tmp, audioInputStream);
                    format = tmp;
            }
			DataLine.Info info = new DataLine.Info(Clip.class, 
						audioInputStream.getFormat(), 
						((int) audioInputStream.getFrameLength() * format.getFrameSize()));
			try
			{
				clip = (Clip) AudioSystem.getLine(info);
				clip.addLineListener(this);
				clipIsLoading = false;
			}
			catch(Exception e){}
		}
		else
		{
			System.out.println("can't get data from file " + clipFile.getName());
		}
	}
	/**
	 * Open the clip and reset it's current frame-position
	 *
	 */
	private void openClip()
	{
			try
			{
				clip.open(audioInputStream);
				clip.setFramePosition(0);
			}
			
			catch (Exception exe)
			{
				exe.printStackTrace();
			}
	}
	/**
	 * Restart the clip from the beginning 
	 *
	 */
	private void restartClip()
	{
		clip.setFramePosition(0);
	}
/**
 * Updates some status
 */
	public void update(LineEvent event)
	{
		if (event.getType().equals(LineEvent.Type.STOP))
		{
			clipIsPlaying = false;
		}
		else if (event.getType().equals(LineEvent.Type.CLOSE))
		{

		}
	}
	/**
	 * play the clip from the beginning
	 * If the clip is already playing this method just returns
	 */
	public void play()
	{
		if (clipIsLoading)return;
		if (clip==null)
		{
			loadInputStream();
			openClip();
		}
		if (clipIsPlaying){return;}
		new Thread() {
            public void run() {
              try
              {
            	  if (!clipIsPlaying)
            	  {
            	  		clipIsPlaying = true;
            	  		restartClip();
            	  		clip.start();
            	  }
               }
               catch (Exception e) 
               {
                     e.printStackTrace();
               }
            }
          }.start();
	}
	/**
	 * Not implemented yet
	 */
    public void loop()
    {
    }
    /**
     * Send a stop issure command to the slip
     */
    public void stop()
    {
    	clip.stop();
    }
     
   

    

    
 


}

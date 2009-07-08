/*
 * © Copyright 2007-2009 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki’s API.
 * 
 * Erki’s API is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package erki.api.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;

/**
 * This class contains utility methods to play sound and music. If no audio device is present on a
 * system or the audio device is not accessible no sound is played and the first time this happens
 * an error message is printed to the log file.
 * 
 * @author Edgar Kalkowski
 */
public class SoundPlayer {
    
    static boolean warned = false;
    
    static final int BUFFER_SIZE = 50000;
    
    static final Collection<Thread> activeThreads = new LinkedList<Thread>();
    
    /**
     * Plays a sound file.
     * 
     * @param file
     *        An {@link File} object that denotes the sound file to play.
     * @param listener
     *        A {@link LineListener} that will be informed about the events that the playing of the
     *        audio file generates. This way one can e.g. observer the point when the playback stops
     *        because the file is finished.
     */
    public static synchronized void play(File file, LineListener listener) {
        file = file.getAbsoluteFile();
        
        try {
            final AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Info info = new DataLine.Info(SourceDataLine.class, stream.getFormat());
            
            if (AudioSystem.isLineSupported(info)) {
                
                try {
                    final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                    
                    if (listener != null) {
                        line.addLineListener(listener);
                    }
                    
                    Log.debug("Opening and starting line.");
                    line.open(stream.getFormat(), BUFFER_SIZE);
                    line.start();
                    SoundThread thread = new SoundThread(line, stream);
                    Log.debug("Starting playback.");
                    activeThreads.add(thread);
                    thread.start();
                } catch (LineUnavailableException e) {
                    
                    if (!warned) {
                        warned = true;
                        Log.error(e);
                        Log.error("No sound will be played.");
                    }
                }
                
            } else {
                
                if (!warned) {
                    warned = true;
                    Log.error("No sound output available! No sound will be played!");
                }
            }
            
        } catch (UnsupportedAudioFileException e) {
            
            if (!warned) {
                warned = true;
                Log.error(e);
                Log.error("No sound will be played.");
            }
            
        } catch (IOException e) {
            
            if (!warned) {
                warned = true;
                Log.error(e);
                Log.error("No sound will be played.");
            }
        }
    }
    
    /**
     * Play a sound file.
     * 
     * @param file
     *        The sound file to play.
     */
    public static synchronized void play(File file) {
        play(file, null);
    }
    
    /** Stop all playback. */
    public static synchronized void stop() {
        SoundThread[] threads = activeThreads.toArray(new SoundThread[0]);
        
        for (SoundThread thread : threads) {
            thread.kill();
        }
    }
}

class SoundThread extends Thread {
    
    private SourceDataLine line;
    private AudioInputStream stream;
    private boolean stopped;
    
    public SoundThread(SourceDataLine line, AudioInputStream stream) {
        this.line = line;
        this.stream = stream;
    }
    
    @Override
    public void run() {
        int read = 0;
        byte[] buffer = new byte[SoundPlayer.BUFFER_SIZE];
        
        try {
            
            while ((read = stream.read(buffer, 0, SoundPlayer.BUFFER_SIZE)) != -1 && !stopped) {
                line.write(buffer, 0, read);
            }
            
            if (stopped) {
                Log.debug("Sound was stopped.");
            } else {
                Log.debug("Sound file is finished.");
            }
            
            line.drain();
            line.stop();
            line.close();
            
            synchronized (SoundPlayer.class) {
                SoundPlayer.activeThreads.remove(this);
            }
            
        } catch (IOException e) {
            
            if (!SoundPlayer.warned) {
                SoundPlayer.warned = true;
                Log.error(e);
                Log.error("No further sound will be played!");
            }
        }
        
        Log.debug("Sound thread exited.");
    }
    
    public void kill() {
        stopped = true;
    }
}
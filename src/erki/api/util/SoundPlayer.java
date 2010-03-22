/*
 * © Copyright 2007–2010 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
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
import java.util.logging.Level;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;

/**
 * This class provides with a simple way to play back sound. If no audio device is present on a
 * system or the audio device is not accessible no sound is played and the first time this happens
 * an error message is printed to the {@link Log}. There is intentionally no exception thrown to
 * make this class failsafe to use. If you want to disable the spammy log output call
 * {@link Log#setLevel(java.util.logging.Level)} with {@link Level#OFF}.
 * <p>
 * Note that actual playback only starts when this thread is started by calling {@link #start()}. If
 * not stopped by use intervention this thread exits if the given sound file is played completely.
 * 
 * @author Edgar Kalkowski
 */
public class SoundPlayer extends Thread {
    
    private static final int DEFAULT_BUFFER_SIZE = 50000;
    
    private static boolean warned = false;
    
    private final File file;
    
    private final LineListener listener;
    
    private final int bufferSize;
    
    private boolean stopped = false;
    
    /**
     * Create a new SoundPlayer.
     * 
     * @param file
     *        The file to play back.
     */
    public SoundPlayer(File file) {
        this(file, null, DEFAULT_BUFFER_SIZE);
    }
    
    /**
     * Create a new SoundPlayer.
     * 
     * @param file
     *        The file to play back.
     * @param listener
     *        The listener that shall be informed if e.g. the playback is finished.
     */
    public SoundPlayer(File file, LineListener listener) {
        this(file, listener, DEFAULT_BUFFER_SIZE);
    }
    
    /**
     * Create a new SoundPlayer.
     * 
     * @param file
     *        The file to play back.
     * @param listener
     *        The listener that shall be informed if e.g. the playback is finished.
     * @param bufferSize
     *        The buffer size to use when reading the sound file (see
     *        {@link SourceDataLine#open(javax.sound.sampled.AudioFormat, int)} for details about
     *        the buffer size).
     */
    public SoundPlayer(File file, LineListener listener, int bufferSize) {
        this.file = file;
        this.listener = listener;
        this.bufferSize = bufferSize;
    }
    
    /** Stop playback as soon as possible. Identical to {@link #stopPlayback()}. */
    public void kill() {
        stopPlayback();
    }
    
    /** Stop playback as soon as possible. */
    public void stopPlayback() {
        stopped = true;
    }
    
    @Override
    public void run() {
        
        try {
            final AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Info info = new DataLine.Info(SourceDataLine.class, stream.getFormat());
            
            if (AudioSystem.isLineSupported(info)) {
                
                final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                
                if (listener != null) {
                    line.addLineListener(listener);
                }
                
                Log.debug("Opening and starting line.");
                line.open(stream.getFormat(), bufferSize);
                line.start();
                Log.debug("Starting playback.");
                
                int read = 0;
                byte[] buffer = new byte[SoundPlayer.DEFAULT_BUFFER_SIZE];
                
                while ((read = stream.read(buffer, 0, SoundPlayer.DEFAULT_BUFFER_SIZE)) != -1
                        && !stopped) {
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
                
            } else {
                
                if (!warned) {
                    warned = true;
                    Log.error("No sound output available! No sound will be played!");
                }
            }
            
        } catch (LineUnavailableException e) {
            
            if (!warned) {
                warned = true;
                Log.error(e);
                Log.error("No sound will be played.");
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
}

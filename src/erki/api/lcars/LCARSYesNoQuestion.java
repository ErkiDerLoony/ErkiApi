/*
 * © Copyright 2007–2011 by Edgar Kalkowski <eMail@edgar-kalkowski.de>
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

package erki.api.lcars;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.LinkedList;

import erki.api.util.Observer;

/**
 * Extends {@link LCARSFrame} to display a yes/no-question.
 * 
 * @author Edgar Kalkowski
 */
public class LCARSYesNoQuestion extends LCARSFrame {
    
    /** In tribute to the api. */
    private static final long serialVersionUID = -4026432170547551177L;
    
    /** The user has answered yes. */
    public static final int YES = 1;
    
    /** The user has answered no. */
    public static final int NO = 0;
    
    /** The user has closed the window without answering the question. */
    public static final int INDEFINITE = -1;
    
    /** The observers of the answer of this question. */
    private Collection<Observer<Integer>> observers = new LinkedList<Observer<Integer>>();
    
    /**
     * Create a new yes/no-question.
     * 
     * @param question
     *        The question to ask the user.
     */
    public LCARSYesNoQuestion(String question) {
        this(question, "Question");
    }
    
    /**
     * Create a new yes/no-question.
     * 
     * @param question
     *        The question to ask the user.
     * @param title
     *        The title of the window.
     */
    public LCARSYesNoQuestion(String question, String title) {
        super(title);
        
        ButtonBarButton yesButton = new ButtonBarButton("Yes", LCARSUtil.BLUE,
                LCARSUtil.BLUE_BRIGHT);
        ButtonBarButton noButton = new ButtonBarButton("No", LCARSUtil.BLUE, LCARSUtil.BLUE_BRIGHT);
        
        addLCARSButton(yesButton);
        addLCARSButton(noButton);
        
        yesButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                for (Observer<Integer> o : observers) {
                    o.inform(YES);
                }
            }
        });
        
        noButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                for (Observer<Integer> o : observers) {
                    o.inform(NO);
                }
            }
        });
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        cp.add(new LCARSLabel(question), BorderLayout.CENTER);
        pack();
        
        addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                
                for (Observer<Integer> o : observers) {
                    o.inform(INDEFINITE);
                }
            }
        });
    }
    
    /**
     * Register a new observer to the result of this question.
     * 
     * @param observer
     *        The observer to register.
     * @return {@code true} if the observer was newly inserted into the collection of observers.<br />
     *         {@code false} if the observer was not newly inserted into the collection of observers
     *         (this can mean that the observer is already in the list).
     */
    public boolean register(Observer<Integer> observer) {
        return observers.add(observer);
    }
    
    /**
     * Deregister an observer of the result of this question.
     * 
     * @param observer
     *        The observer to deregister.
     * @return {@code true} if the observer was successfully removed from the collection of
     *         observers.<br />
     *         {@code false} if the observer was not found in the collection of observers and thus
     *         could not be removed.
     */
    public boolean deregister(Observer<Integer> observer) {
        return observers.remove(observer);
    }
}

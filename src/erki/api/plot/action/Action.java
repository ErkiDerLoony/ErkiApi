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

package erki.api.plot.action;

import erki.api.plot.Plot2D;

/**
 * All classes that implement this interface can add additional functionality to the {@link Plot2D}
 * class. Actions are added to a {@code Plot2D} object by calling {@link Plot2D#add(Action)} with
 * the corresponding action object. {@code Plot2D} then calls the action's {@link #init(Plot2D)}
 * method with itself as parameter. After an action was added to a plot it can be removed via
 * {@link Plot2D#remove(Action)}. Then {@link #destroy(Plot2D)} is called where the action shall
 * remove the formerly added functionality from the delivered {@code Plot2D} object.
 * <p>
 * If someone tries to remove an action from a plot object to which it was not formerly added it is
 * up to the implementation of the {@link #destroy(Plot2D)} method if an exception is thrown or the
 * issue is silently ignored.
 * <p>
 * Be careful to use different instances of actions for different plots as the actions may store
 * some state of the plot in {@link #init(Plot2D)} which is needed for {@link #destroy(Plot2D)}.
 * This may not work if the same action instance is used for multiple plot objects.
 * 
 * @author Edgar Kalkowski
 */
public interface Action {
    
    /**
     * This method is called by {@link Plot2D#add(Action)} with the corresponding plot object as
     * parameter. This {@code Action} may initialize itself in this method and add functionality to
     * the delivered plot object.
     * 
     * @param plot
     *        The plot to which the functionality of this {@code Action} will be added.
     */
    public void init(Plot2D plot);
    
    /**
     * This method is called by {@link Plot2D#remove(Action)} with the corresponding plot object as
     * parameter. This {@code Action} shall remove its functionality from the given plot object. If
     * this action was not formerly added to the same plot object it is up to the actual
     * implementation of this method what happens. It may well throw an exception.
     * 
     * @param plot
     *        The plot from which the functionality of this {@code Action} will be removed.
     */
    public void destroy(Plot2D plot);
}

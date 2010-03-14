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

package erki.api.plot.style;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

/**
 * Identifiers for the different style constants defined in the {@link StyleProvider} hierarchy. As
 * the user does only implicitly know about the type of the style constants returned for these keys
 * it might be wise to include the type in the javadoc description.
 * 
 * @author Edgar Kalkowski
 */
public enum StyleConstants {
    
    /** This instance of {@link Color} defines the background colour of the plot. */
    PLOT_BACKGROUND_COLOR,

    /**
     * This instance of {@link Boolean} indicates whether or not gridlines shall be displayed by the
     * plot.
     */
    GRID_VISIBLE,

    /**
     * This instance of {@link Color} defines the colour of the gridlines. Note that the gridlines
     * are only shown if {@link #GRID_VISIBLE} is set to {@code true}.
     */
    GRID_COLOR,

    /**
     * This instance of {@link Stroke} defines the stroke used for the gridlines. Note that the
     * gridlines are only shown if {@link #GRID_VISIBLE} is set to {@code true}.
     */
    GRID_STROKE,

    /**
     * This instance of {@link Boolean} indicates whether or not the plot shall be drawn
     * antialiased. This setting also works for the external drawers that can be added to the plot.
     */
    ANTIALIASING_ENABLED,

    /** This {@link Color} is used for the tick labels, e.g. of the {@link StyledNumberAxis}. */
    AXES_TICK_FONT_COLOR,

    /** This {@link Font} is used for the tick labels, e.g. of the {@link StyledNumberAxis}. */
    AXES_TICK_FONT,

    /** This {@link Color} is used to draw the coordinate axis, e.g. in {@link StyledNumberAxis}. */
    AXES_COLOR
}

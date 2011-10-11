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

package erki.api.plot;

/**
 * Implemented by coordinate axes that require {@link Plot2D#autorange()} to leave an empty area at
 * the edge of the plot where only the coordinate axes are drawn.
 * <p>
 * <b>Attention</b>: All the returned values are in screen pixel coodinates!
 * 
 * @author Edgar Kalkowski
 */
public interface CoordinateAxis {
    
    /**
     * The offset required at the left edge of the plot area.
     * 
     * @return The offset in pixels.
     */
    public int getLeftOffset();
    
    /**
     * The offset required at the right edge of the plot area.
     * 
     * @return The offset in pixels.
     */
    public int getRightOffset();
    
    /**
     * The offset at the top edge of the plot area.
     * 
     * @return The offset in pixels.
     */
    public int getTopOffset();
    
    /**
     * The offset at the bottom edge of the plot area.
     * 
     * @return The offset in pixels.
     */
    public int getBottomOffset();
    
}

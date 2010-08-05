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

import org.jfree.chart.axis.NumberAxis;

import erki.api.plot.Plot2d;

/**
 * The default axis used as both domain and range axis by {@link Plot2d}.
 * 
 * @author Edgar Kalkowski
 */
public class StyledNumberAxis extends NumberAxis {
    
    private static final long serialVersionUID = -6508522350620494981L;
    
    /**
     * Create a new DefaultNumberAxis.
     * 
     * @param styleProvider
     *        The style provider that defines how the new axis shall look.
     */
    public StyledNumberAxis(StyleProvider styleProvider) {
        super();
        setAutoRangeIncludesZero(false);
        setTickMarkPaint(styleProvider.get(new StyleKey<Color>(StyleConstants.AXES_COLOR)));
        setTickLabelFont(styleProvider.get(new StyleKey<Font>(StyleConstants.AXES_TICK_FONT)));
        setTickLabelPaint(styleProvider
                .get(new StyleKey<Color>(StyleConstants.AXES_TICK_FONT_COLOR)));
        setAxisLinePaint(styleProvider.get(new StyleKey<Color>(StyleConstants.AXES_COLOR)));
    }
}

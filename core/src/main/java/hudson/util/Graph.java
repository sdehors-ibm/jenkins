/*
 * The MIT License
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.util;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.awt.*;
import java.io.IOException;
import java.util.Calendar;

/**
 * A JFreeChart-generated graph that's bound to UI.
 *
 * <p>
 * This object exposes two URLs:
 * <dl>
 * <dt>/png
 * <dd>PNG image of a graph
 *
 * <dt>/map
 * <dd>Clickable map
 * </dl>
 *
 * @author Kohsuke Kawaguchi
 * @since 1.320
 */
public class Graph {
    private final long timestamp;
    private final int defaultW;
    private final int defaultH;

    /**
     * @param timestamp Timestamp of this graph. Used for HTTP cache related headers.
     *                  If the graph doesn't have any timestamp to tie it to, pass -1.
     */
    protected Graph(long timestamp, int defaultW, int defaultH) {
        this.timestamp = timestamp;
        this.defaultW = defaultW;
        this.defaultH = defaultH;
    }

    protected Graph(Calendar timestamp, int defaultW, int defaultH) {
        this(timestamp.getTimeInMillis(), defaultW, defaultH);
    }


    @NonNull
    private static Color stringToColor(@CheckForNull String s) {
        if (s != null) {
            try {
                return Color.decode("0x" + s);
            } catch (NumberFormatException e) {
                return Color.WHITE;
            }
        } else {
            return Color.WHITE;
        }
    }

    /**
     * Renders a graph.
     */
    public void doPng(StaplerRequest req, StaplerResponse rsp) throws IOException {
        return;
    }

    /**
     * Renders a clickable map.
     */
    public void doMap(StaplerRequest req, StaplerResponse rsp) throws IOException {
        return;
    }
}

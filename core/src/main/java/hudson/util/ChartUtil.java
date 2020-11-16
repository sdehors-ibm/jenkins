/*
 * The MIT License
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., Kohsuke Kawaguchi
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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.awt.*;
import java.io.IOException;

/**
 * Chart generation utility code around JFreeChart.
 *
 * @author Kohsuke Kawaguchi
 */
public class ChartUtil {
    /**
     * Can be used as a graph label. Only displays numbers.
     */
    public static final class NumberOnlyBuildLabel implements Comparable<NumberOnlyBuildLabel> {

        private final Run<?, ?> run;

        @Deprecated
        public final AbstractBuild build;

        /**
         * @since 1.577
         */
        public NumberOnlyBuildLabel(Run<?, ?> run) {
            this.run = run;
            this.build = run instanceof AbstractBuild ? (AbstractBuild) run : null;
        }

        @Deprecated
        public NumberOnlyBuildLabel(AbstractBuild build) {
            this.run = build;
            this.build = build;
        }

        /**
         * @since 1.577
         */
        public Run<?, ?> getRun() {
            return run;
        }

        public int compareTo(NumberOnlyBuildLabel that) {
            return this.run.number - that.run.number;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof NumberOnlyBuildLabel)) return false;
            NumberOnlyBuildLabel that = (NumberOnlyBuildLabel) o;
            return run == that.run;
        }

        @Override
        public int hashCode() {
            return run.hashCode();
        }

        @Override
        public String toString() {
            return run.getDisplayName();
        }
    }

    /**
     * @deprecated Use {@code awtProblemCause!=null} instead. As of 1.267.
     */
    @Deprecated
    public static boolean awtProblem = false;

    //TODO: prevent usage of this APIs in plugins. Needs to be deprecated and replaced by a getter method
    /**
     * See issue 93. Detect an error in X11 and handle it gracefully.
     */
    @SuppressFBWarnings(value = "MS_SHOULD_BE_REFACTORED_TO_BE_FINAL",
        justification = "It's actually being widely used by plugins. "
            + "Obsolete approach, should be ideally replaced by Getter")
    public static Throwable awtProblemCause = null;

    /**
     * Generates the graph in PNG format and sends that to the response.
     *
     * @param defaultSize The size of the picture to be generated. These values can be overridden
     *                    by the query parameter 'width' and 'height' in the request.
     * @deprecated as of 1.320
     * Bind {@link Graph} to the URL space. See {@code hudson.tasks.junit.History} as an example (note that doing so involves
     * a bit of URL structure change.)
     */
    @Deprecated
    public static void generateGraph(StaplerRequest req, StaplerResponse rsp, Area defaultSize) throws IOException {
        generateGraph(req, rsp, defaultSize.width, defaultSize.height);
    }

    /**
     * Generates the graph in PNG format and sends that to the response.
     *
     * @param defaultW
     * @param defaultH The size of the picture to be generated. These values can be overridden
     *                 by the query parameter 'width' and 'height' in the request.
     * @deprecated as of 1.320
     * Bind {@link Graph} to the URL space. See {@code hudson.tasks.junit.History} as an example (note that doing so involves
     * a bit of URL structure change.)
     */
    @Deprecated
    public static void generateGraph(StaplerRequest req, StaplerResponse rsp, int defaultW, int defaultH) throws IOException {
        new Graph(-1, defaultW, defaultH).doPng(req, rsp);
    }

    /**
     * Generates the clickable map info and sends that to the response.
     *
     * @deprecated as of 1.320
     * Bind {@link Graph} to the URL space. See {@code hudson.tasks.junit.History} as an example (note that doing so involves
     * a bit of URL structure change.)
     */
    @Deprecated
    public static void generateClickableMap(StaplerRequest req, StaplerResponse rsp, Area defaultSize) throws IOException {
        generateClickableMap(req, rsp, defaultSize.width, defaultSize.height);
    }

    /**
     * Generates the clickable map info and sends that to the response.
     *
     * @deprecated as of 1.320
     * Bind {@link Graph} to the URL space. See {@code hudson.tasks.junit.History} as an example (note that doing so involves
     * a bit of URL structure change.)
     */
    @Deprecated
    public static void generateClickableMap(StaplerRequest req, StaplerResponse rsp, int defaultW, int defaultH) throws IOException {
        new Graph(-1, defaultW, defaultH).doMap(req, rsp);
    }


    public static double CHEBYSHEV_N = 3;

    static {
        try {
            new Font("SansSerif", Font.BOLD, 18).toString();
        } catch (Throwable t) {
            awtProblemCause = t;
            awtProblem = true;
        }
    }
}

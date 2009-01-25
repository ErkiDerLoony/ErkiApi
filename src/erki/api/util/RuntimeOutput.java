/*
 * (c) Copyright 2007-2008 by Edgar Kalkowski (eMail@edgar-kalkowski.de)
 * 
 * This file is part of Erki's API.
 * 
 * Erki's API is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package erki.api.util;

/**
 * Data class for the stdout and stderr output of an executed shell command.
 * 
 * @author Edgar Kalkowski
 */
public class RuntimeOutput {
    
    /** The command that was executed. */
    public final String cmd;
    
    /** The stdout output of the shell command. */
    public final String stdout;
    
    /** The stderr output of the shell command. */
    public final String stderr;
    
    /**
     * Creates a new <code>RuntimeOutput</code> instance containing the results
     * of a shell command.
     * 
     * @param stdout
     *        The stdout output of the shell command.
     * @param stderr
     *        The stderr output of the shell command.
     */
    public RuntimeOutput(String cmd, String stdout, String stderr) {
        this.cmd = cmd;
        this.stdout = stdout;
        this.stderr = stderr;
    }
    
    /** @return The output produced by the executed command. */
    public String getOutput() {
        return stdout;
    }
    
    /** @return The error output produced by the executed command. */
    public String getError() {
        return stderr;
    }
    
    /** @return The command the was executed. */
    public String getCmd() {
        return cmd;
    }
    
    @Override
    public String toString() {
        return "-> Command executed: " + cmd
                + System.getProperty("line.separator") + "-> Standard output:"
                + System.getProperty("line.separator") + stdout
                + System.getProperty("line.separator") + "-> Error output:"
                + System.getProperty("line.separator") + stderr;
    }
}

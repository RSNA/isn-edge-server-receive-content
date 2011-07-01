/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsna.isn.retrievecontent.retrieve;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author kleiding
 */

public class LogProvider {
    private static LogProvider logger;
    private static Logger log;

    public static void init(String propsFile, String logName) {
        logger = new LogProvider();
        PropertyConfigurator.configure(propsFile);
        log = Logger.getLogger(logName);
    }

    public static LogProvider getInstance() {
        return logger;
    }

    public Logger getLog() {
        return log;
    }
}


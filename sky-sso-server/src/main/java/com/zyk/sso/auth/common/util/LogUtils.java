package com.zyk.sso.auth.common.util;

import org.slf4j.Logger;

/**
 * @author sky
 * @description
 * @create 2017-10-31 下午1:15
 **/
public class LogUtils {

    /**
     *
     * @param log
     * @param info
     */
    private static void debug(Logger log, String info) {
        if (log.isDebugEnabled()) {
            log.debug(info);
        }
    }

    /**
     *
     * @param log
     * @param info
     */
    private static void info(Logger log, String info) {
        if (log.isInfoEnabled()) {
            log.info(info);
        }
    }

    /**
     *
     * @param log
     * @param error
     * @param e
     */
    private static void error(Logger log, String error, Exception e) {
        if (log.isErrorEnabled()) {
            log.error(error, e);
        }
    }

}

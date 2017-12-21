package cz.i.pirin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author jan.hadas@i.cz
 */
public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    public static Long parseLong(String number) {
        if (!StringUtils.isEmpty(number)) {
            try {
                return Long.parseLong(number);
            } catch (NumberFormatException e) {
                LOG.warn("Unparseable number: {}", number);
            }
        }
        return null;
    }
}

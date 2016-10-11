package online.decentworld.charge;

import online.decentworld.tools.EnvironmentCofing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sammax on 2016/9/26.
 */
final public class ChargeConfig {
    final public static String RSA_PUBLIC;
    final public static String RSA_PRIVATE;

    private static Logger logger= LoggerFactory.getLogger(ChargeConfig.class);
    static {
        Properties p=new Properties();
        try {
            p.load(ChargeConfig.class.getClassLoader().getResourceAsStream("admin.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("",e);
        }
        RSA_PUBLIC=p.getProperty(EnvironmentCofing.environment.name().toUpperCase()+"_RSA_PUBLIC");
        RSA_PRIVATE=p.getProperty(EnvironmentCofing.environment.name().toUpperCase()+"_RSA_PRIVATE");
    }


}

package may.i.jhq.constant;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午3:32
 * @desc The constant of project
 **/
public final class ProjectConstant {

    /**
     * Base package
     */
    public static final String BASE_PACKAGE = "may.i.jhq";

    /**
     * Domain package
     */
    public static final String DOMAIN_PACKAGE = BASE_PACKAGE + ".domain";

    /**
     * Dao package
     */
    public static final String DAO_PACKAGE = BASE_PACKAGE + ".dao";

    /**
     * Service package
     */
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";

    /**
     * Service implement package
     */
    public static final String SERVICE_IMPLEMENT_PACKAGE = SERVICE_PACKAGE + ".impl";

    /**
     * Controller package
     */
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".api";

    /**
     * Dev environment
     */
    public static final String ENVIRONMENT_DEV = "dev";

    /**
     * Pro environment
     */
    public static final String ENVIRONMENT_PRO = "pro";

    /**
     * Test environment
     */
    public static final String ENVIRONMENT_TEST = "test";
}

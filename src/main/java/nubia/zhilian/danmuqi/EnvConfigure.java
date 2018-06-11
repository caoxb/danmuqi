package nubia.zhilian.danmuqi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuwang on 2017/11/7.
 */

public class EnvConfigure {
    static final public String KEY_IS_DEBUG = "KEY_IS_DEBUG";
    static final public String KEY_DEVICE_ID = "KEY_DEVICE_ID";
    static final public String KEY_PRODUCT_ID = "KEY_PRODUCT_ID"; // 阿里云移动平台分配的产品ID
    static final public String KEY_APPKEY = "KEY_APPKEY"; // 阿里云移动平台分配的AppKey
    static final public String KEY_TRACE_ID = "KEY_TRACE_ID";
    // rn-container
    static final public String KEY_RN_CONTAINER_PLUGIN_ENV = "KEY_RN_CONTAINER_PLUGIN_ENV";

    static final Map<String, String> envArgs = new HashMap<>();

    static public boolean hasEnvArg(String key) {
        return envArgs.containsKey(key);
    }

    static public Object getEnvArg(String key) {
        return envArgs.get(key);
    }

    static public void putEnvArg(String key, String value) {
        envArgs.put(key, value);
    }

}

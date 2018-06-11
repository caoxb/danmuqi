package nubia.zhilian.danmuqi;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKConfigure;

import nubia.zhilian.danmuqi.sdk.APIGatewaySDKDelegate;

public class DemoApplication extends AApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init_api_sdk();
    }

    private void init_api_sdk() {
        SDKConfigure configure = new SDKConfigure("API-Client", "0.0.1", APIGatewaySDKDelegate.class.getName());
        Log.d("xbcao","init_api_sdk");
        new APIGatewaySDKDelegate().init(this,configure,EnvConfigure.envArgs);
    }
}

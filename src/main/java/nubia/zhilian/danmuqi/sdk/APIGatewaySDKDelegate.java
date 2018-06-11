package nubia.zhilian.danmuqi.sdk;

import android.app.Application;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.wireless.security.jaq.JAQException;
import com.alibaba.wireless.security.jaq.SecurityInit;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.aep.sdk.apiclient.adapter.APIGatewayHttpAdapterImpl;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Env;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestWrapper;
import com.aliyun.iot.aep.sdk.framework.sdk.SDKConfigure;
import com.aliyun.iot.aep.sdk.framework.sdk.SimpleSDKDelegateImp;

import java.util.Map;

import nubia.zhilian.danmuqi.EnvConfigure;

/**
 * Created by wuwang on 2017/10/30.
 */
public final class APIGatewaySDKDelegate extends SimpleSDKDelegateImp {

    public final static String ENV_KEY_API_CLIENT_DEFAULT_HOST = "KEY_API_CLIENT_DEFAULT_HOST";
    public final static String ENV_KEY_API_CLIENT_API_ENV = "KEY_API_CLIENT_API_ENV";

    static final private String TAG = "xbcao";

    /* API: ISDKDelegate */

    @Override
    public int init(Application app, SDKConfigure sdkConfigure, Map<String, String> args) {
        int ret = 0;

        // 初始化无线保镖
        try {
            ret = SecurityInit.Initialize(app);
        } catch (JAQException ex) {
            Log.e(TAG, "security-sdk-initialize-failed", ex);

            ret = null != ex ? ex.getErrorCode() : -9999;
        } catch (Exception ex) {
            Log.e(TAG, "security-sdk-initialize-failed", ex);

            ret = -1;
        }

        // 初始化 IoTAPIClient
        IoTAPIClientImpl.InitializeConfig config = new IoTAPIClientImpl.InitializeConfig();
        config.host = "api.link.aliyun.com";
        config.apiEnv = Env.RELEASE;

        IoTAPIClientImpl impl = IoTAPIClientImpl.getInstance();
        impl.init(app, config);

        // 日志处理
        impl.setPerformanceTracker(new Tracker());
        //ALog.setLevel(ALog.LEVEL_DEBUG);

        // 添加环境变量
        String appKey = APIGatewayHttpAdapterImpl.getAppKey(app, "114d");
        args.put(EnvConfigure.KEY_APPKEY, appKey);
        args.put(ENV_KEY_API_CLIENT_DEFAULT_HOST, config.host);
        args.put(ENV_KEY_API_CLIENT_API_ENV, config.apiEnv.toString());

        return ret;
    }

    private static class Tracker implements com.aliyun.iot.aep.sdk.apiclient.tracker.Tracker {
        final String TAG = "xbcao:Tracker";

        @Override
        public void onSend(IoTRequest request) {
            Log.i(TAG, "onSend:\r\n" + toString(request));
        }

        @Override
        public void onRealSend(IoTRequestWrapper ioTRequest) {
            Log.d(TAG, "onRealSend:\r\n" + toString(ioTRequest));
        }

        @Override
        public void onRawFailure(IoTRequestWrapper ioTRequest, Exception e) {
            Log.d(TAG, "onRawFailure:\r\n" + toString(ioTRequest) + "ERROR-MESSAGE:" + e.getMessage());
        }

        @Override
        public void onFailure(IoTRequest request, Exception e) {
            Log.i(TAG, "onFailure:\r\n" + toString(request) + "ERROR-MESSAGE:" + e.getMessage());
        }

        @Override
        public void onRawResponse(IoTRequestWrapper request, IoTResponse response) {
            Log.d(TAG, "onRawResponse:\r\n" + toString(request) + toString(response));
        }

        @Override
        public void onResponse(IoTRequest request, IoTResponse response) {
            Log.i(TAG, "onResponse:\r\n" + toString(request) + toString(response));
        }

        private static String toString(IoTRequest request) {
            return new StringBuilder("Request:").append("\r\n")
                    .append("url:").append(request.getScheme()).append("://").append(null == request.getHost() ? "" : request.getHost()).append(request.getPath()).append("\r\n")
                    .append("apiVersion:").append(request.getAPIVersion()).append("\r\n")
                    .append("params:").append(null == request.getParams() ? "" : JSON.toJSONString(request.getParams())).append("\r\n").toString();
        }

        private static String toString(IoTRequestWrapper wrapper) {
            IoTRequest request = wrapper.request;

            return new StringBuilder("Request:").append("\r\n")
                    .append("id:").append(wrapper.id).append("\r\n")
                    .append("apiEnv:").append(wrapper.apiEnv).append("\r\n")
                    .append("url:").append(request.getScheme()).append("://").append(wrapper.host).append(request.getPath()).append("\r\n")
                    .append("apiVersion:").append(request.getAPIVersion()).append("\r\n")
                    .append("params:").append(null == request.getParams() ? "" : JSON.toJSONString(request.getParams())).append("\r\n").toString();
        }

        private static String toString(IoTResponse response) {
            return new StringBuilder("Response:").append("\r\n")
                    .append("id:").append(response.getId()).append("\r\n")
                    .append("code:").append(response.getCode()).append("\r\n")
                    .append("message:").append(response.getMessage()).append("\r\n")
                    .append("localizedMsg:").append(response.getLocalizedMsg()).append("\r\n")
                    .append("data:").append(null == response.getData() ? "" : response.getData().toString()).append("\r\n").toString();
        }
    }
}
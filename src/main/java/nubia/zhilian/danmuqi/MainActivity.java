package nubia.zhilian.danmuqi;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientImpl;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;

import nubia.zhilian.danmuqi.bean.Temperature;
import nubia.zhilian.danmuqi.sdk.APIGatewaySDKDelegate;

public class MainActivity extends AppCompatActivity {
    private Temperature temperature;
    private TextView tv_temp;
    private String content;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String tempFormat = getResources().getString(R.string.temp);
            String sFinalTemp = String.format(tempFormat, temperature.getValue());
            tv_temp.setText(sFinalTemp);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                        GetTemperature();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        GetTemperature();
    }

    private void initData() {
        temperature = new Temperature();
        content = "Hello,弹幕器！";
    }

    private void initView() {
        tv_temp = findViewById(R.id.tv_temp);
    }

    private void GetTemperature() {
        // 构建请求
        IoTRequest request = new IoTRequestBuilder()
                .setScheme(Scheme.HTTPS) // 如果是HTTPS，可以省略本设置
                .setHost((String) EnvConfigure.getEnvArg(APIGatewaySDKDelegate.ENV_KEY_API_CLIENT_DEFAULT_HOST)) // 如果是IoT官方服务，可以省略本设置
                .setPath("/thing/device/property/query") // 参考业务API文档，设置path
                .setApiVersion("1.1.0")  // 参考业务API文档，设置apiVersion
                .addParam("productKey", "a1qmj4JuHvq") // 参考业务API文档，设置params,也可使用setParams(Map<Strign,Object> params)
                .addParam("deviceName","QQGEVZptd2vyZzAJUReX")
                .addParam("propertyIdentifier","IndoorTemperature")
                .build();

        IoTAPIClientImpl impl = IoTAPIClientImpl.getInstance();
        Log.d("xbcao","GetTemperature");
        impl.send(request, new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {

            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                Log.d("xbcao","response code = " + ioTResponse.getCode() );
                //List<Student> studentList1 = JSONArray.parseArray(JSON_ARRAY_STR, Student.class);
                Log.d("xbcao","Data:"+ioTResponse.getData().toString());
                temperature = JSONObject.parseObject(ioTResponse.getData().toString(),Temperature.class);
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    public void danmu(View view) {
        setDanMu(content);
    }

    private void setDanMu(String content) {
        String  JSON_OBJ_STR  = "{\"DanMu\":\""+content+"\"}";
        //转为JSON对象
        JSONObject danmu = JSON.parseObject(JSON_OBJ_STR);
        // 构建请求
        IoTRequest request = new IoTRequestBuilder()
                .setScheme(Scheme.HTTPS) // 如果是HTTPS，可以省略本设置
                .setHost((String) EnvConfigure.getEnvArg(APIGatewaySDKDelegate.ENV_KEY_API_CLIENT_DEFAULT_HOST)) // 如果是IoT官方服务，可以省略本设置
                .setPath("/thing/device/properties/set") // 参考业务API文档，设置path
                .setApiVersion("1.1.0")  // 参考业务API文档，设置apiVersion
                .addParam("productKey", "a1qmj4JuHvq") // 参考业务API文档，设置params,也可使用setParams(Map<Strign,Object> params)
                .addParam("deviceName","QQGEVZptd2vyZzAJUReX")
                .addParam("properties",danmu)
                .build();

        IoTAPIClientImpl impl = IoTAPIClientImpl.getInstance();

        Log.d("xbcao","setDanMu");
        impl.send(request, new IoTCallback() {
            @Override
            public void onFailure(IoTRequest ioTRequest, Exception e) {

            }

            @Override
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                Log.d("xbcao","response code = " + ioTResponse.getCode() );
                //List<Student> studentList1 = JSONArray.parseArray(JSON_ARRAY_STR, Student.class);
                Log.d("xbcao","Data:"+ioTResponse.getData().toString());
            }
        });
    }
}

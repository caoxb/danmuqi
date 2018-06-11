package nubia.zhilian.danmuqi.bean;

public class Temperature {
    //data:{"value":18.5,"gmtModified":1528164848884,"attribute":"IndoorTemperature","batchId":"6534815d11cd4f8684253534f938d362","iotId":"QQGEVZptd2vyZzAJUReX0010cce800"}
    private float value;
    private long gmtModified;
    private String attribute;
    private String batchId;
    private String iotId;

    public Temperature() {
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public void setIotId(String iotId) {
        this.iotId = iotId;
    }

    public float getValue() {
        return value;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getIotId() {
        return iotId;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "value=" + value +
                ", gmtModified=" + gmtModified +
                ", attribute='" + attribute + '\'' +
                ", batchId='" + batchId + '\'' +
                ", iotId='" + iotId + '\'' +
                '}';
    }
}

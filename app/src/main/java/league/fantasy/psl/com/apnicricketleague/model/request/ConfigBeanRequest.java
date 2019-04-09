package league.fantasy.psl.com.apnicricketleague.model.request;

public class ConfigBeanRequest {
    private String param_type;
    private String userId;

    public ConfigBeanRequest(String param_type, String userId) {
        this.param_type = param_type;
        this.userId = userId;
    }

    public String getParam_type() {
        return param_type;
    }

    public void setParam_type(String param_type) {
        this.param_type = param_type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

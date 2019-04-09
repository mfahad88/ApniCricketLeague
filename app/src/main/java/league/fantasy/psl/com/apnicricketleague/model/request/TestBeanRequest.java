package league.fantasy.psl.com.apnicricketleague.model.request;

public class TestBeanRequest {
    private String request;
    private String key;

    public TestBeanRequest(String request, String key) {
        this.request = request;
        this.key = key;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

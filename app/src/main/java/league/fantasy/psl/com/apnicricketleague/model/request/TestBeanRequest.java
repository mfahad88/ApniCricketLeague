package league.fantasy.psl.com.apnicricketleague.model.request;

public class TestBeanRequest {
    private StringBuilder request;
    private String key;

    public TestBeanRequest(StringBuilder request, String key) {
        this.request = request;
        this.key = key;
    }

    public StringBuilder getRequest() {
        return request;
    }

    public void setRequest(StringBuilder request) {
        this.request = request;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

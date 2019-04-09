package league.fantasy.psl.com.apnicricketleague.model.request;

public class AgentBeanRequest {
    private String lat;
    private String lng;
    private String dist;
    private int userId;
    private String method_Name;

    public AgentBeanRequest(String lat, String lng, String dist, int userId, String method_Name) {
        this.lat = lat;
        this.lng = lng;
        this.dist = dist;
        this.userId = userId;
        this.method_Name = method_Name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMethod_Name() {
        return method_Name;
    }

    public void setMethod_Name(String method_Name) {
        this.method_Name = method_Name;
    }
}

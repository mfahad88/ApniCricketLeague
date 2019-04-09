
package league.fantasy.psl.com.apnicricketleague.model.response.Agent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("agent_name")
    @Expose
    private String agentName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("officer")
    @Expose
    private String officer;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("field_office_name")
    @Expose
    private String fieldOfficeName;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("dist")
    @Expose
    private Object dist;
    @SerializedName("is_BVS")
    @Expose
    private String isBVS;

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFieldOfficeName() {
        return fieldOfficeName;
    }

    public void setFieldOfficeName(String fieldOfficeName) {
        this.fieldOfficeName = fieldOfficeName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getDist() {
        return dist;
    }

    public void setDist(Object dist) {
        this.dist = dist;
    }

    public String getIsBVS() {
        return isBVS;
    }

    public void setIsBVS(String isBVS) {
        this.isBVS = isBVS;
    }

}

package league.fantasy.psl.com.apnicricketleague.model.request;


public class ProductLeadRequest {


    private int userId;
    private String name;
    private String email;
    private String contact;
    private String userComment;
    private String city;
    private String channelId;
    private String prodSts;
    private String methodName;

    public ProductLeadRequest(int userId, String name, String email, String contact, String userComment, String city, String channelId, String prodSts, String methodName) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.userComment = userComment;
        this.city = city;
        this.channelId = channelId;
        this.prodSts = prodSts;
        this.methodName = methodName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getProdSts() {
        return prodSts;
    }

    public void setProdSts(String prodSts) {
        this.prodSts = prodSts;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

}
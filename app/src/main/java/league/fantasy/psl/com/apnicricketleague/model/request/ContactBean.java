package league.fantasy.psl.com.apnicricketleague.model.request;

public class ContactBean {

    private int user_id;
    private String name;
    private String email;
    private String mobile;
    private String comments;
    private String method_Name;

    public ContactBean(int user_id, String name, String email, String mobile, String comments, String method_Name) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.comments = comments;
        this.method_Name = method_Name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }



    public String getMethod_Name() {
        return method_Name;
    }

    public void setMethod_Name(String method_Name) {
        this.method_Name = method_Name;
    }
}

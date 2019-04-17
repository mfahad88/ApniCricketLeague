
package league.fantasy.psl.com.apnicricketleague.model.response.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

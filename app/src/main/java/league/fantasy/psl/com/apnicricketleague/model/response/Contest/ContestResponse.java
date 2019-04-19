
package league.fantasy.psl.com.apnicricketleague.model.response.Contest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContestResponse {

    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

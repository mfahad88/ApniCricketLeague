package league.fantasy.psl.com.apnicricketleague.model.response.ProductLead;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductLeadResponse {

@SerializedName("ResponseCode")
@Expose
private String responseCode;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("data")
@Expose
private Integer data;

public String getResponseCode() {
return responseCode;
}

public void setResponseCode(String responseCode) {
this.responseCode = responseCode;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Integer getData() {
return data;
}

public void setData(Integer data) {
this.data = data;
}

}
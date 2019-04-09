package league.fantasy.psl.com.apnicricketleague.Interface;

import league.fantasy.psl.com.apnicricketleague.model.request.AgentBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.request.ConfigBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.request.ContactBean;
import league.fantasy.psl.com.apnicricketleague.model.request.ProductLeadRequest;
import league.fantasy.psl.com.apnicricketleague.model.response.Agent.AgentBeanResponse;
import league.fantasy.psl.com.apnicricketleague.model.response.Config.ConfigBeanResponse;
import league.fantasy.psl.com.apnicricketleague.model.response.ProductLead.ProductLeadResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("agents")
    Call<AgentBeanResponse> getAgents(@Body AgentBeanRequest beanRequest);

    @POST("insertComplaint")
    Call<ContactBean> insertComplaint(@Body ContactBean beanRequest);

    @POST("config")
    Call<ConfigBeanResponse> getConfig(@Body ConfigBeanRequest beanRequest);

    @POST("insertProductLead")
    Call<ProductLeadResponse> insertProductLead(@Body ProductLeadRequest beanRequest);
}

package league.fantasy.psl.com.apnicricketleague.Interface;

import league.fantasy.psl.com.apnicricketleague.model.request.AgentBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.request.ConfigBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.request.ContactBean;
import league.fantasy.psl.com.apnicricketleague.model.request.ProductLeadRequest;
import league.fantasy.psl.com.apnicricketleague.model.request.TestBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.response.Agent.AgentBeanResponse;
import league.fantasy.psl.com.apnicricketleague.model.response.Config.ConfigBeanResponse;
import league.fantasy.psl.com.apnicricketleague.model.response.ProductLead.ProductLeadResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("agents")
    Call<AgentBeanResponse> getAgents(@Body TestBeanRequest beanRequest);

    @POST("insertComplaint")
    Call<ContactBean> insertComplaint(@Body TestBeanRequest beanRequest);

    @POST("config")
    Call<ConfigBeanResponse> getConfig(@Body TestBeanRequest beanRequest);

    @POST("insertProductLead")
    Call<ProductLeadResponse> insertProductLead(@Body TestBeanRequest beanRequest);

    @POST("testService")
    Call<String> testService(@Body TestBeanRequest beanRequest);

/*    @POST("text")
    Call<String> testService(@Body TestBeanRequest beanRequest);*/
}

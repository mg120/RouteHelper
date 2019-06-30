package com.routehelperr.networking;

import com.routehelperr.model.CallServicesDataModel;
import com.routehelperr.model.ChangePassModel;
import com.routehelperr.model.ChatMessageResponseModel;
import com.routehelperr.model.ChattersModel;
import com.routehelperr.model.ForgetPassSendMailModel;
import com.routehelperr.model.InfoDataModel;
import com.routehelperr.model.InfoModalModel;
import com.routehelperr.model.LogInResponseModel;
import com.routehelperr.model.MakeOrderModel;
import com.routehelperr.model.MapEmployModel;
import com.routehelperr.model.NotifationsModel;
import com.routehelperr.model.PackagesModel;
import com.routehelperr.model.RegisterResponseModel;
import com.routehelperr.model.SendMessageResponseModel;
import com.routehelperr.model.ServicesModel;
import com.routehelperr.model.SettingInfoModel;
import com.routehelperr.model.UpdateLocationModel;
import com.routehelperr.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    // ---------------- Setting INFO -------------------------------
    @POST("api/settinginfo")
    Call<SettingInfoModel> getSettingInfo();


    // ---------------- Setting INFO (Get Packages)  -------------------------------
    @POST("api/settinginfo")
    Call<PackagesModel> getPackagesInfo();


    // ---------------- Setting INFO (Get Services)  -------------------------------
    @POST("api/settinginfo")
    Call<ServicesModel> getServicesInfo();


    // ---------------- Setting INFO (Get InfoData)  -------------------------------
    @POST("api/settinginfo")
    Call<InfoDataModel> getInfo();


    // ---------------- Setting INFO (Get InfoData)  -------------------------------
    @POST("api/settinginfo")
    Call<InfoModalModel> getModalInfo();


    // ---------------- Setting INFO (Get Call Services)  --------------------------
    @POST("api/settinginfo")
    Call<CallServicesDataModel> getCallServicesData();


    // ---------------- LogIn------------------------------------------------------
    @FormUrlEncoded
    @POST("api/login")
    Call<LogInResponseModel> logIn(@Field("email") String email,
                                   @Field("password") String password,
                                   @Field("firebase_token") String firebase_token,
                                   @Field("lang") String lang
    );


    // ---------------- Register-------------------------------
    @FormUrlEncoded
    @POST("api/register")
    Call<RegisterResponseModel> register(@Field("username") String username,
                                         @Field("email") String email,
                                         @Field("phone") String phone,
                                         @Field("idnumber") String idnumber,
                                         @Field("password") String password,
                                         @Field("carnumber") String carnumber,
                                         @Field("carmodal") String carmodal,
                                         @Field("cartype") String cartype,
                                         @Field("user_type") int user_type,
                                         @Field("package") int packages,
                                         @Field("coupon") String coupon,
                                         @Field("lat") double lat,
                                         @Field("lng") double lng,
                                         @Field("firebase_token") String firebase_token,
                                         @Field("lang") String lang
    );


    // ---------------- Forget Password -------------------------------
    // ---------------- 1 - Send Email --------------------
    @FormUrlEncoded
    @POST("api/forgetpassword")
    Call<ForgetPassSendMailModel> forgetPassword(@Field("email") String email);

    // ---------------- 2 - Send Code --------------------
    @FormUrlEncoded
    @POST("api/activcode")
    Call<ForgetPassSendMailModel> sendCode(@Field("forgetcode") String forgetcode,
                                           @Field("email") String email,
                                           @Field("lang") String lang);


    // ---------------- 3 - New Passsword --------------------
    @FormUrlEncoded
    @POST("/api/rechangepass")
    Call<ChangePassModel> new_Pass(@Field("new_password") String new_password,
                                   @Field("email") String email,
                                   @Field("lang") String lang);


    // -------------------  Get User Data (Profile)-----------------------
    @FormUrlEncoded
    @POST("api/profile")
    Call<User> getUserData(@Field("user_id") int user_id);


    // -------------------  Employer Activate -----------------------
    @FormUrlEncoded
    @POST("api/activcode")
    Call<LogInResponseModel> employerActivate(@Field("forgetcode") String forgetcode,
                                              @Field("email") String email,
                                              @Field("lang") String lang);


    // -------------------  Update User Data (Profile)-----------------------
    @FormUrlEncoded
    @POST("api/updateprofile")
    Call<RegisterResponseModel> updateProfile(@Field("user_id") int user_id,
                                              @Field("username") String username,
                                              @Field("email") String email,
                                              @Field("phone") String phone,
                                              @Field("idnumber") String idnumber,
                                              @Field("password") String password
    );


    // -------------------  Update Car Data -----------------------
    @FormUrlEncoded
    @POST("api/updatecar")
    Call<RegisterResponseModel> updateCar(@Field("user_id") int user_id,
                                          @Field("carnumber") String carnumber,
                                          @Field("carmodal") String carmodal,
                                          @Field("cartype") String cartype
    );


    // -------------------  Get My Notification -----------------------
    @FormUrlEncoded
    @POST("api/mynotification")
    Call<NotifationsModel> getUserNotifications(@Field("user_id") int user_id);


    // -------------------  Get My Notification -----------------------
    @FormUrlEncoded
    @POST("api/getchaters")
    Call<ChattersModel> getUserChatters(@Field("user_id") int user_id);


    // -------------------  Order Service -----------------------
    @FormUrlEncoded
    @POST("api/makeorder")
    Call<MakeOrderModel> orderService(@Field("user_id") int user_id,
                                      @Field("user_lat") double user_lat,
                                      @Field("user_lng") double user_lng,
                                      @Field("emp_id") int emp_id,
                                      @Field("emp_lat") double emp_lat,
                                      @Field("emp_lng") double emp_lng,
                                      @Field("problem") String problem,
                                      @Field("policyname") String policyname,
                                      @Field("service_id") int service_id
    );


    // -------------------  Get Chat -----------------------
    @FormUrlEncoded
    @POST("api/getchat")
    Call<ChatMessageResponseModel> getChatMessages(@Field("receiver_id") int user_id,
                                                   @Field("sender_id") int service_id
    );


    // -------------------  Send chat Msg -----------------------
    @FormUrlEncoded
    @POST("api/makechat")
    Call<SendMessageResponseModel> sendMessage(@Field("sender_id") int user_id,
                                               @Field("receiver_id") int service_id,
                                               @Field("message") String message,
                                               @Field("lang") String lang
    );


    // -------------------  Get All Employer Maps -----------------------
    @GET("api/allemps")
    Call<MapEmployModel> getEmployMaps();


    // ------------------- Update Location ------------------------------
    @FormUrlEncoded
    @POST("api/updatelocation")
    Call<UpdateLocationModel> updateLocation(@Field("user_id") int user_id,
                                             @Field("lat") double lat,
                                             @Field("lng") double lng
    );
}
package com.devanshi.tambola.coinpicker.apis;

import com.devanshi.tambola.coinpicker.models.*;

import retrofit2.*;
import retrofit2.http.*;

public interface APIInterface {

    @FormUrlEncoded
    @POST(ServerConfig.LOGIN_API)
    Call<UserModel> loginApi(@Field("username") String username,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST(ServerConfig.REGISTER_API)
    Call<UserModel> registerApi(@Field("password") String password,
                                @Field("fname") String firstName,
                                @Field("lname") String lastName,
                                @Field("email") String email);

    @FormUrlEncoded
    @POST(ServerConfig.FORGOT_PASSWORD_API)
    Call<UserModel> forgotPasswordApi(@Field("username") String username);

    @FormUrlEncoded
    @POST(ServerConfig.CHANGE_PASSWORD_API)
    Call<UserModel> changePasswordApi(@Field("username") String username,
                                      @Field("old_password") String old_password,
                                      @Field("new_password") String new_password,
                                      @Field("confirm_password") String confirm_password);

    @FormUrlEncoded
    @POST(ServerConfig.DECLARED_NUMBER_BY_ADMIN)
    Call<DeclaredNumberModel> getDeclaredNumbersApi(@Field("game_id") Integer game_id,
                                                    @Field("type") String type);

    @FormUrlEncoded
    @POST(ServerConfig.DECLARED_NUMBER_BY_ADMIN)
    Call<DeclaredNumberModel> numberByAdminApi(@Field("game_id") Integer game_id,
                                               @Field("type") String type,
                                               @Field("manual_input_number") String manual_input_number);

    @FormUrlEncoded
    @POST(ServerConfig.ADD_GAME_API)
    Call<StartGameModel> addGameApi(@Field("created_by") String created_by);
}

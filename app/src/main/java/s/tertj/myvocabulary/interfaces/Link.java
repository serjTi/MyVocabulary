package s.tertj.myvocabulary.interfaces;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Link {
    @FormUrlEncoded
    @POST("user/edit")
    Call<Object> translate(@FieldMap Map<String,String> map);
}

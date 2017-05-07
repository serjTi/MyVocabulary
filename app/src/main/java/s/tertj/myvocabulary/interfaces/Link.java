package s.tertj.myvocabulary.interfaces;


import java.util.Map;

import retrofit.Call;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface Link {
    @FormUrlEncoded
    @POST("/api/v1.5/tr.json/translate")
    Call<Object> translate(@FieldMap Map <String,String> map);
}

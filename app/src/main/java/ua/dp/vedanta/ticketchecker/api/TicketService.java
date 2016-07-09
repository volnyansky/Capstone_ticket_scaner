package ua.dp.vedanta.ticketchecker.api;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Stanislav Volnyansky on 26.06.16.
 */
public interface TicketService {
    @GET("event-ticket/{barcode}.json")
    Call<TicketJson> getTicket(@Path("barcode") String barcode);
    @Multipart
    @POST("event-ticket/{barcode}/validate.json")
    Call<ValidationResult> validate(@Path("barcode") String barcode,@Part("barcode") RequestBody bcode);

}

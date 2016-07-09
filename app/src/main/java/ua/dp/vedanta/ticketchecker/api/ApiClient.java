package ua.dp.vedanta.ticketchecker.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Stanislav Volnyansky on 26.06.16.
 */
public class ApiClient {

    private static TicketService service;
    private ApiClient(){
    }

    public static TicketService getService() {
        if (service==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://vedanta.dp.ua/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(TicketService.class);
        }
        return service;
    }



}

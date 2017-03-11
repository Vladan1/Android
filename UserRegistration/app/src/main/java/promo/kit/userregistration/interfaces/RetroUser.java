package promo.kit.userregistration.interfaces;



import java.util.List;

import promo.kit.userregistration.model.Result;
import retrofit2.http.GET;
import rx.Observable;

public interface RetroUser {
    @GET("api")
    Observable<List<Result>> getUsers();
}
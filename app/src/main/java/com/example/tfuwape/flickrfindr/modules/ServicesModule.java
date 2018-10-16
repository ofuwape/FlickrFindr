package com.example.tfuwape.flickrfindr.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.tfuwape.flickrfindr.BuildConfig;
import com.example.tfuwape.flickrfindr.core.APIService;
import com.example.tfuwape.flickrfindr.core.MyApplication;
import com.example.tfuwape.flickrfindr.util.MyUtil;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module returning mockable version of the API services
 */
@Module
public final class ServicesModule extends MockableModule {

    private Context mContext;
    private boolean provideMocks;

    /**
     * Creates new instance of ServicesModule.
     *
     * @param provideMocks determine if API service should be mocked.
     * @param context      context calling method
     */
    public ServicesModule(boolean provideMocks, Context context) {
        super(provideMocks);
        this.mContext = context;
        this.provideMocks = provideMocks;
    }

    /**
     * Provides new instance of APIService.
     *
     * @return APIService
     * @see APIService
     */
    @Provides
    @Singleton
    public APIService provideAPI() {
        final String apiPath = MyUtil.getAPIPath(mContext);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {

                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Accept", "applicaton/json")
                                .header("User-Agent", "Flickr Findr")
                                .method(original.method(), original.body());

                        if (BuildConfig.DEBUG) {
                            Request request = requestBuilder.build();
                            Log.i("apiUrl-->", request.url().toString());
                        }

                        return chain.proceed(requestBuilder.build());
                    }
                }).build();

        String baseUrl = apiPath;

        // Used for configuring mocked url endpoint
        if (BuildConfig.DEBUG && mockMode) {
            MyApplication app = MyUtil.getApplication(mContext);
            if (app != null && !app.getMockBaseUrl().isEmpty()) {
                baseUrl = app.getMockBaseUrl();
            }
        }

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build();

        return retrofit.create(APIService.class);
    }
}

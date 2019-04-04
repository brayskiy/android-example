package com.brayskiy.example.dagger

import android.app.Application
import android.util.Log
import com.brayskiy.example.rest.BaseUrl
import com.brayskiy.example.rest.MobileService
import com.google.gson.*
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by brayskiy on 10/27/17.
 */

@Module
class ApiModule(private val application: Application)
{

    @Provides
    @Singleton
    fun getRetrofit(client: OkHttpClient, gsonFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl.THE_MOVIE_DB_HOST_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .addConverterFactory(gsonFactory)
            .build()
    }

    @Provides
    @Singleton
    fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()

            // TODO For additional parameters

            val updatedRequest = builder.build()
            chain.proceed(updatedRequest)
        }
    }

    @Provides
    @Singleton
    fun getClient(interceptor: Interceptor): OkHttpClient {
        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                message -> Log.d("", message)
        })
        logger.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .cache(Cache(File(application.cacheDir, "content_cache"), 16 * 1024 * 1024))
            .addInterceptor(logger)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun getGson(): Gson {
        return GsonBuilder()
            // TODO set temporarily ignore date parsing failure
            //.setDateFormat("yyyy-MM-dd")
            .registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

                @Throws(JsonParseException::class)
                override fun deserialize(
                    json: JsonElement, typeOfT: Type,
                    context: JsonDeserializationContext
                ): Date? {
                    return try {
                        df.timeZone = TimeZone.getTimeZone("UTC")
                        df.parse(json.asString)
                    } catch (e: ParseException) {
                        null
                    }
                }
            })
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    @Singleton
    fun getGsonFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun getMobileService(retrofit: Retrofit): MobileService {
        return retrofit.create<MobileService>(MobileService::class.java)
    }
}

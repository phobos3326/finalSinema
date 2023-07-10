package com.example.skillsinema.repository


import com.example.skillsinema.adapter.Film
import com.example.skillsinema.data.BestFilmDTO
import com.example.skillsinema.data.DataDTO
import com.example.skillsinema.entity.*
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class Repository @Inject constructor(

) {


    @Provides
    suspend fun getPremiere(year: Int, month: String): Model {
        //delay(2000)
        return retrofitInstance().getFilms(year, month)
    }

    @Provides
    suspend fun getFilmDetails(value: Int): ModelFilmDetails {

        return retrofitInstance2().filmDetails(value)
    }

    @Provides
    suspend fun getTopFilm(): List<Film> {
        return retrofitTopFilms.topFilms().films
    }


    @Provides
    suspend fun getFilters(): List<ModelFilter.Genre> {
        return retrofitInstanceFilters().filters().genres
    }

    @Provides
    suspend fun getFilteredFilm(page: Int, countries: Int, genre: Int): List<Film> {
        return retrofit.filteredFilms(page, countries, genre, 3, 10).items
    }

    /*@Provides
    suspend fun getStaff(id: Int): List<ModelStaffItem> {
        return retrofitStaff.staff(301).staffItem
    }*/

      /* val q=retrofitStaff.staff(301).body()

        val gson = Gson()
        try {
            retrofitStaff.staff(301).body()
        } catch (e: JsonSyntaxException) {
            if (e.equals(
                    "Expected BEGIN_OBJECT but was BEGIN_ARRAY"
                )
            ) {
                val jsonArray = JsonParser()
                    .parse(qq)
                    .getAsJsonArray();
                for (i in 0..jsonArray.size()) {
                  val q= gson.fromJson(jsonArray.get(i), ModelStaffItem::class.java)
                    // do something with the user object
                }
            }
        }

        val myData: List<ModelStaffItem> = moshi.adapter(Types.newParameterizedType(List::class.java, ModelStaffItem::class.java)).fromJson(q)
        return q!!*/

      /*  kotlin.runCatching {

        }.fold(
            onSuccess = {
              return  listOf(it!!)
            },
            onFailure =
        )*/






    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"
    private val BASE_URL2 = "https://kinopoiskapiunofficial.tech/api/v1/"


    private val logInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()




    //val myData: List<ModelStaffItem> = moshi.  adapter(Types.newParameterizedType(List::class.java, ModelStaffItem::class.java)).fromJson(jsonString)



   /* class VideosJsonConverter {
        @Wrapped
        @FromJson
        fun fromJson(json: VideosResponse): List<Video> {
            return json.results
        }

        @ToJson
        fun toJson(@Wrapped value: List<Video?>?): VideosResponse {
            throw UnsupportedOperationException()
        }
    }*/


    private fun retrofit(): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClientBuilder.build())
            .build()
    }


    val retrofitTopFilms = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .baseUrl("https://kinopoiskapiunofficial.tech/api/v2.2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterfaceTopFilms::class.java)

    val retrofit = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .baseUrl("https://kinopoiskapiunofficial.tech/api/v2.2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterfaceFilteredFilms::class.java)


  /*  val retrofitStaff = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {


              //  qq = it.level
                it.level = HttpLoggingInterceptor.Level.BODY

            }).build()
        )
        .baseUrl(BASE_URL2)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterfaceStaff::class.java)*/


 /*   val listType = Types.newParameterizedType(List::class.java, ApiInterfaceStaff::class.java)
    val adapter: JsonAdapter<List<qq>> = moshi.adapter(listType)
    val pushPortMessageList = adapter.fromJson(pushPortMessage)*/


    private fun retrofitInstance(): ApiInterface = retrofit()!!.create(ApiInterface::class.java)
    private fun retrofitInstance2(): ApiInterface2 = retrofit()!!.create(ApiInterface2::class.java)
    private fun retrofitInstanceTopFilms(): ApiInterfaceTopFilms =
        retrofit()!!.create(ApiInterfaceTopFilms::class.java)

    private fun retrofitInstanceFilters(): ApiInterfaceFilters =
        retrofit()!!.create(ApiInterfaceFilters::class.java)

    private fun retrofitInstanceFilteredFilms(): ApiInterfaceFilteredFilms =
        retrofit()!!.create(ApiInterfaceFilteredFilms::class.java)

    interface ApiInterface {
        @Headers("X-API-KEY: $api_key")

        @GET("films/premieres?")
        suspend fun getFilms(
            @Query("year") year: Int,
            @Query("month") month: String

        ): DataDTO


    }


    interface ApiInterface2 {
        @Headers("X-API-KEY: $api_key")
        @GET("films/{id}")
        suspend fun filmDetails(
            @Path(value = "id") id: Int
            // @Query("id") id:Int
        ): ModelFilmDetails

    }

    interface ApiInterfaceTopFilms {
        @Headers("X-API-KEY: $api_key")
        @GET("films/top?type=TOP_250_BEST_FILMS&page=1")
        suspend fun topFilms(
            // @Query("page") page: Int
        ): BestFilmDTO

    }


    interface ApiInterfaceFilters {
        @Headers("X-API-KEY: $api_key")
        @GET("films/filters")
        suspend fun filters(

        ): ModelFilter

    }

    interface ApiInterfaceFilteredFilms {
        @Headers("X-API-KEY: $api_key")
        @GET("films")
        suspend fun filteredFilms(
            @Query("page") page: Int,
            @Query("countries") countries: Int,
            @Query("genres") month: Int,
            @Query("ratingFrom") ratingFrom: Int,
            @Query("ratingTo") ratingTo: Int,

            ): ModelFilteredFilms1

    }

   /* interface ApiInterfaceStaff {
        @Headers("X-API-KEY: $api_key")
        @GET("staff?")
        suspend fun staff(
            @Query("filmId") filmId: Int
        ): ModelStaff
    }*/

    private companion object {
        private const val api_key = "63101d70-3149-4782-99f8-dd1ba5fc4ab1"
    }




/*    @JsonClass(generateAdapter = true)
    data class CustomObjJson(
        var at1: String? = null,
        var at2: String? = null,
        var at3: String? = null,
    )

    class CustomObjAdapter {
        @ToJson
        fun toJson(obj: ModelStaffItem): String {
            return obj.nameRu + obj.description + obj.posterUrl
        }

        @FromJson
        fun fromJson(customObjJson: CustomObjJson): ModelStaffItem {
            var at1 = customObjJson.at1
            // TODO: find a way to convert customObjJson.at2 of type String to Date here
            var at2 = customObjJson.at2
            var at3 = customObjJson.at3
            return ModelStaffItem(description = at1!!, nameRu = at2!!, posterUrl = at3!! )
        }
    }*/



}
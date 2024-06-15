package org.d3if0123.posyanducare2.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if0123.posyanducare2.model.Kegiatan
import org.d3if0123.posyanducare2.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://api.ihsanmiqbal.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface ApiService {
    @GET("json.php")
    suspend fun getHewan(
        @Query("auth") userId: String
    ): List<Kegiatan>

    @Multipart
    @POST("json.php")
    suspend fun postHewan(
        @Part("auth") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus

    @DELETE("json.php")
    suspend fun deleteHewan(
        @Query("auth") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object Api {
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun getHewanUrl(imageId: String): String {
        return "${BASE_URL}$imageId"
    }
}

enum class ApiStatus {
    LOADING, SUCCESS, FAILED
}
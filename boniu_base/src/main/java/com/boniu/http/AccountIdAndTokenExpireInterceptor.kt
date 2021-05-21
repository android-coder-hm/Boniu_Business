package com.boniu.http


import com.boniu.clearLoginStatus
import com.boniu.getAccountId
import com.boniu.getLoginAccountId
import com.boniu.saveLoginAccountId
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import org.json.JSONObject
import java.io.IOException

data class TokenExpireException(val msg: String = "token过期") : Exception(msg)


const val http_business_account_id_expire = "9990"
const val http_business_token_expire = "9991"

class AccountIdAndTokenExpireInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val builder = response.newBuilder()
        val clone = builder.build()
        val body = clone.body
        body?.apply {
            val bodyJsonObject = JSONObject(this.string())
            if (bodyJsonObject.has("errorCode")) {
                val errorCode = bodyJsonObject.getString("errorCode") ?: ""
                if (errorCode.isNotEmpty()) {
                    if (errorCode.contains(http_business_token_expire)) {
                        clearLoginStatus()
                        throw TokenExpireException("登录过期,请重新登录")
                    }
                    if (errorCode.contains(http_business_account_id_expire)) {
                        //重新获取accountId
                        saveLoginAccountId(getAccountId())
                        val newRequestJsonObject = JSONObject(bodyToString(request))
                        if (newRequestJsonObject.has("accountId")) {
                            newRequestJsonObject.put("accountId", getLoginAccountId())
                        }
                        return chain.proceed(
                            Request.Builder()
                                .url(request.url)
                                .post(
                                    newRequestJsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
                                )
                                .build()
                        )
                    }
                }
            }

            return response.newBuilder()
                .body(bodyJsonObject.toString().toResponseBody(body.contentType())).build()

        }
        return response
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "something error when show requestBody."
        }
    }
}
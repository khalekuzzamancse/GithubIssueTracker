package core.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


/**
Because of Generic ,we are using inline function,to keep the inline function short as possible
using the class.
Because of the inline reified we are not able to make this completely private.
later try to refactor is as as private.
Right now we are making it internal,
we are trying to hide it from IDE suggestion by it:
 * * @PublishedApi internal is the intended way of exposing non-public API for use in public inline functions.
 * * Try out for better solution if any...
 */


class GetRequests {
    val tag: String = this.javaClass.simpleName

val client = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true // This will ignore unknown keys in the response
        })
    }

}

      suspend inline fun <reified T> request(url: String): Result<T> {
        return requestResponseDecorator(url)
    }

    suspend inline fun <reified T> requestResponseDecorator(url: String): Result<T> {
        return try {
            val responseRaw = client.get(url)
            Log.d(tag,responseRaw.bodyAsText())
            val response: T = responseRaw.body()
            Log.d(tag,responseRaw.bodyAsText())
            return Result.success(response)
        } catch (ex: Exception) {
            Log.d(tag,"$ex")
            Result.failure(
                Throwable(
                    message = "Failed to fetch",
                    cause = Throwable(ex.stackTraceToString())
                )
            )
        } finally {
            closeConnection()
        }
    }


    @PublishedApi
    internal fun closeConnection() {
        try {
            client.close()
        } catch (_: Exception) {
        }
    }


}
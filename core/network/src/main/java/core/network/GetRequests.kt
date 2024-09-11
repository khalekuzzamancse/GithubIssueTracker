package core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
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
@Serializable
data class ErrorMessage(
    val message: String,
    val cause: String,
    val source: String,
)

@Serializable
data class ResponseDecorator<T>(
    val response: T,
    val message: ErrorMessage?,
    val success: Boolean
)


class GetRequests {
    val tag: String = this.javaClass.simpleName
//    val client = HttpClient {
//        install(ContentNegotiation) {
//            json()
//        }
//    }
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
            println("$tag:Raw:${responseRaw.bodyAsText()}")
            val response: T = responseRaw.body()
            println("$tag:Response:${response}")
            return Result.success(response)
        } catch (ex: Exception) {
            println("$tag:Failed:${ex}")
            Result.failure(Throwable(ex))
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
package core.network.factory

import android.util.Log
import core.network.component.ApiServiceClient
import core.network.component.ExceptionToError
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**- Outer module should not create direct instance of this but can use it by getting
 * instance via `factory method`
 */
class ApiServiceClientImpl internal constructor(): ApiServiceClient {
   private val tag: String = this.javaClass.simpleName
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // This will ignore unknown keys in the response
            })
        }
    }


    override suspend fun retrieveJsonData(url: String): Result<String> {
        return try {
            val httpResponse = client.get(url)
            val json = httpResponse.bodyAsText()
            Result.success(json)
        } catch (ex: Exception) {
            Log.d(tag, "$url -> $ex")
            Result.failure(ExceptionToError().convert(ex))
        } finally {
            closeConnection()
        }
    }

    private fun closeConnection() {
        try {
            client.close()
        } catch (_: Exception) {
        }
    }
}


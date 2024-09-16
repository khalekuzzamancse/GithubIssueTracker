package core.network.component

import kotlinx.serialization.KSerializer

/**
 * -  Parses the given JSON string and converts it into an instance of the specified type T
 */
interface JsonParser {
    /**
     * Parses the given JSON string and converts it into an instance of the specified type [T].
     * @param json The raw JSON string to be parsed.
     * @param serializer The [KSerializer] to handle the deserialization of the type [T],
     * Unfortunately, you cannot directly obtain a `KSerializer<T>` for a `generic` type T at runtime due to type erasure in Kotlin
     * that is why need to pass the serializer
     * @return A [Result] containing the parsed object of type [T] if successful, or an error if parsing fails.
     * - Possible Usage
     * ```kotlin
     * @kotlinx.serialization.Serializable
     * data class User(val id: Int, val name: String)
     *
     * val json:String="{msg:Hello}"
     * val parser: JsonParser = JsonParserImpl()
     * val result: Result<User> = parser.parse(json,User.serializer())
     * ```
     */
    fun <T> parse(json: String, serializer: KSerializer<T>): Result<T>
}
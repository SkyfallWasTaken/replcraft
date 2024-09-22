package com.replmc.replcraft.rpc

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.full.*
import kotlin.reflect.KFunction

const val PROTO_VERSION = 1
val ALLOWLIST = listOf("add")

@Serializable
class RpcRequest(
    val id: String,
    val method: String,
    val params: List<@Contextual Any>?,
    val protoVersion: Int = 1
) {
    val isEvent: Boolean = params == null

    init {
        require(protoVersion == PROTO_VERSION) { "Invalid protocol version: expected $PROTO_VERSION, got $protoVersion" }
        require(method.isNotBlank()) { "Method cannot be blank" }
        require(id.isNotBlank()) { "ID cannot be blank" }
        require(ALLOWLIST.contains(method)) { "Method `$method` is not allowed" }
    }

    fun callOnServer(): Any? {
        val kClass = this::class
        val method: KFunction<*>? = kClass.functions.find { it.name == this.method }
        return method?.call(this)
    }

    fun add(): Int {
        return 1
    }

    companion object {
        fun fromJson(json: String): RpcRequest {
            return Json.decodeFromString(json)
        }
    }
}
package com.replmc.replcraft.rpc

import io.javalin.Javalin

class RpcServer {
    val app: Javalin = Javalin.create()

    init {
        app.get("/", { ctx -> ctx.result("Hello from Replcraft!") })
        app.ws("/gateway") { ws ->
            ws.onConnect { ctx -> println("Connected") }
            ws.onMessage { ctx ->
                val req = RpcRequest.fromJson(ctx.message())
                val response = req.callOnServer()
                if (response != null) {
                    ctx.send(response.toString())
                }
            }
            ws.onClose { ctx -> println("Closed") }
            ws.onError { ctx -> println("Errored") }
        }

        app.start(4680)
    }

    fun shutdown() {
        this.app.stop()
    }
}
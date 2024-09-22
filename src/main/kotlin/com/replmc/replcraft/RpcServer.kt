package com.replmc.replcraft

import io.javalin.Javalin

class RpcServer {
    val app: Javalin = Javalin.create()

    init {
        app.get("/", { ctx -> ctx.result("Hello from Replcraft!") })
        app.ws("/gateway") { ws ->
            ws.onConnect { ctx -> println("Connected") }
            ws.onMessage { ctx ->
                // val user = ctx.message<User>() // convert from json string to object
                ctx.send(ctx.message()) //  convert to json string and send back
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
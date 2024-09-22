package com.replmc.replcraft

import net.fabricmc.api.ModInitializer
import net.minecraft.server.command.CommandManager
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import org.slf4j.LoggerFactory
import kotlinx.coroutines.*
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents


object Replcraft : ModInitializer {
    private val logger = LoggerFactory.getLogger("Replcraft")

	override fun onInitialize() = runBlocking {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
        logger.info("Registering commands")
		CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            dispatcher.register(CommandManager.literal("tokens").executes(TokensCommand::execute))
        }

        launch {
            logger.info("Starting RPC server...")
            val rpcServer = RpcServer();
            ServerLifecycleEvents.SERVER_STOPPING.register({
                rpcServer.shutdown()
            })
            logger.info("Started RPC server")
        }

        logger.info("Initialized!")
    }
}
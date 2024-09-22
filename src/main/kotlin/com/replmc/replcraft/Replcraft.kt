package com.replmc.replcraft

import net.fabricmc.api.ModInitializer
import net.minecraft.server.command.CommandManager
import net.minecraft.text.Text
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import org.slf4j.LoggerFactory
import com.replmc.replcraft.TokensCommand

object Replcraft : ModInitializer {
    private val logger = LoggerFactory.getLogger("Replcraft")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->
            dispatcher.register(CommandManager.literal("tokens").executes(TokensCommand::execute))
        }

        logger.info("Initialized!")
    }
}
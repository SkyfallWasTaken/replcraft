package com.replmc.replcraft

import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

class TokensCommand {
    companion object {
        @JvmStatic
        fun execute(context: CommandContext<ServerCommandSource>): Int {
            context.source.sendFeedback({ Text.literal("Your token is XYZ") }, false)
            return 1
        }
    }
}

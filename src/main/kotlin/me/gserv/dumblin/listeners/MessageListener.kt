package me.gserv.dumblin.listeners

import me.gserv.dumblin.config
import me.gserv.dumblin.events.Listener
import kotlinx.coroutines.future.await
import kotlinx.coroutines.time.delay
import mu.KotlinLogging
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.time.Duration


private val logger = KotlinLogging.logger("listener/message")

class MessageListener {
    @Listener(MessageReceivedEvent::class)
    suspend fun socialDistancing(event: MessageReceivedEvent) {
        if (event.author.idLong == event.jda.selfUser.idLong) {
            return  // Don't action our own messages!
        }

        if (!event.isFromGuild) {
            return  // Only actions messages from a server
        }

        val num = (0..5).shuffled().first()

        if (num != 0) {
            event.channel.sendMessage("⠀").submit().await()
        }
    }
}

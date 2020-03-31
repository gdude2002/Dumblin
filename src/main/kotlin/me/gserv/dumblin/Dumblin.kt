package me.gserv.dumblin

import com.charleskorn.kaml.Yaml
import com.jagrosh.jdautilities.command.CommandClientBuilder
import me.gserv.dumblin.config.Config
import me.gserv.dumblin.events.EventDispatcher
import me.gserv.dumblin.listeners.MessageListener
import mu.KotlinLogging
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import java.io.File
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger("main")

var bot: JDA? = null
var config: Config = Config()


fun main() {
    val configFile = File("config.yml")

    val loadedConfig: Config?

    if (System.getenv("NO_CONFIG") != null) {
        loadedConfig = Config()
    } else if (!configFile.exists()) {
        loadedConfig = Config()

        try {
            configFile.createNewFile()
            configFile.writeText(Yaml.default.stringify(Config.serializer(), loadedConfig))
            logger.info { "Created config file 'config.yml' - please fill it out and restart the bot." }

            exitProcess(0)
        } catch (e: Throwable) {
            logger.error(e) { "Failed to create config file." }

            exitProcess(1)
        }
    } else {
        try {
            loadedConfig = Yaml.default.parse(Config.serializer(), configFile.readText())
        } catch (e: Throwable) {
            logger.error(e) { "Failed to load config file." }

            exitProcess(1)
        }
    }

    config = loadedConfig  // So it's available in other parts of the application

    val client = CommandClientBuilder().setPrefix("!")
        .setOwnerId(config.ownerId.toString())
        .build()

    EventDispatcher.register(MessageListener())

    bot = JDABuilder(config.token)
        .addEventListeners(client, EventDispatcher)
        .build()

    bot?.awaitReady()
}

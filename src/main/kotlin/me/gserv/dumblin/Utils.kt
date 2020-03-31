package me.gserv.dumblin
import java.net.URL

fun String.asResource(): URL? = object {}.javaClass.getResource(this)

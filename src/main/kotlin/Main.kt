package com.robbiebowman
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: test <command> [args...]")
        return
    }

    val command = args[0]
    val restArgs = args.slice(1 until args.size)

    println("Executing command: $command ${restArgs.joinToString(" ")}")

    val fullCommand = listOf(command) + restArgs
    val processBuilder = ProcessBuilder(fullCommand)
    processBuilder.redirectErrorStream(true)

    try {
        val process = processBuilder.start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))

        var line: String?
        while (reader.readLine().also { line = it } != null) {
            println(line)
        }

        val exitCode = process.waitFor()
        println("Command exited with code: $exitCode")
    } catch (e: Exception) {
        println("Error executing command: ${e.message}")
    }
}
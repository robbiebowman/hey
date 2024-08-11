package com.robbiebowman

import java.io.BufferedReader
import java.io.InputStreamReader

object HeyCLI {
    @JvmStatic
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
            .redirectErrorStream(true)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)

        // Set environment variable to force color output
        val env = processBuilder.environment()
        env["FORCE_COLOR"] = "true"
        env["CLICOLOR_FORCE"] = "1"

        try {
            val process = processBuilder.start()
            val exitCode = process.waitFor()
            println("Command exited with code: $exitCode")
        } catch (e: Exception) {
            println("Error executing command: ${e.message}")
        }
    }
}
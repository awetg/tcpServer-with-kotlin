/**
 *  @author Awet Ghebreslassie , ID= 1706242
 *
 *  This file contains the main method of this application.
 *  The server is run from the main thread (inside main function) and spawns a new thread for every accepted client.
 *  The server runs on ipAddress 0.0.0.0/0.0.0.0 this approach is taken,
 *  so that so that the server can listen for all ipAddress of local machine on specified port
 *  All functionality of this server can observed from the list of supported command in 'CommandList.kt', it includes
 *  SetUser, LogIn, Logout, latest message from all chats or specific chats, history, top chatter, notification through Firebase
 *  As a configuration the server adds a public chat to the list of chats on start up,
 *  and all clients are subscribed to this chat on first register( when user name set)
 */

import chat.ChatList
import command.CommandInterpreter
import java.net.ServerSocket

fun main(args: Array<String>)   {
    serverConfig()

    try {
        val server = ServerSocket(2323)
        println("Server running on port: ${server.localPort} Address: ${server.inetAddress}")

        while (true) {
            val client = server.accept()
            Thread(CommandInterpreter(client.getInputStream(), client.getOutputStream())).start()
            println("Client connected: ${client.inetAddress.hostAddress}")
        }
    } catch (e: Exception) {
        println("ERROR:  ${e.message}")
    }
}

fun serverConfig() {
    ChatList.addChat(ChatList.PublicChat)

}
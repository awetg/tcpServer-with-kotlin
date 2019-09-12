/**
 * @author Awet Ghebreslassie , ID= 1706242
 *
 * This file contains SendPushNotification class.
 * This class is used to deliver notification using Firebase Cloud Messaging to android client application.
 * Server key of FCM are supposed to be kept private.
 * This approach was chosen for easy of implementation push notification. If there is enough time while implementing android client application,
 * this might be removed and replaced with Android Service that listens to server all the time starting from app install.
 */

import chat.ChatMessage
import org.json.simple.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.io.OutputStreamWriter

class SendPushNotification(private val token:String,private val chatMessage: ChatMessage){
    private val _url = "https://fcm.googleapis.com/fcm/send"
    private val _serverKey = "AAAAZ7V2zhI:APA91bF4CXAotT1_x9I_yJJxUMLalnzBdoRfCYwwPyXPeAQ8dLxRm4LBsKCeYInX3fuzlhi4rkaj1-7fwlqGdCcebXhPYPcGWlmZwbd9M4FiwSvDi366i_2Q9-4mnTuBy4Z6bEP1Q1pr"

    operator fun invoke(){
        val connection = URL(_url).openConnection() as HttpURLConnection
        connection.useCaches = false
        connection.doOutput = true
        connection.requestMethod = "POST"
        connection.doInput = true

        connection.setRequestProperty("Authorization", "key=$_serverKey")
        connection.setRequestProperty("Content-Type", "application/json")

//        val notification = JSONObject()
//        notification.put("title",chatMessage.user)
//        notification.put("body",chatMessage.message)
//        notification.put("sound","default")

        val data = JSONObject()
        data.put("CHAT_ID",chatMessage.chatId)
        data.put("SENDER",chatMessage.sender)
        data.put("MESSAGE",chatMessage.message)
        data.put("TIME",chatMessage.dateTime.toInstant().toEpochMilli())
        data.put("CHAT_NAME",chatMessage.chatName)
        data.put("READ",chatMessage.read.toString())

        val json = JSONObject()
        json.put("to",token)
        json.put("data",data)
        json.put("priority","high")

        try {
            val wr = OutputStreamWriter(connection.outputStream)
            wr.write(json.toString())
            wr.flush()

            val br = BufferedReader(InputStreamReader(connection.inputStream))
            var output = br.readLine()
            while (output != null) {
                println(output)
                output = br.readLine()
            }

        } catch (e: Exception) {
            println("push notification failed: ${e.message}")
        }
    }
}
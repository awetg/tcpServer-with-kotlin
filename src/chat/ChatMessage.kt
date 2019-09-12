/**
 * @author Awet Ghebreslassie , ID= 1706242
 *
 * This file contains 'ChatMessage' class. ChatMessage represents message as entity with all necessary property
 */

package chat

import java.time.ZoneId
import java.time.ZonedDateTime

data class ChatMessage(val message: String,
                       val sender: String, val chatId:Long,
                       val chatName:String,
                       val dateTime:ZonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Helsinki")),
                       val read:Boolean = false) {
    override fun toString(): String {
        return "$message from $sender at ${dateTime.toLocalDateTime()}"
    }

    fun toStringForApp(): String {
        return "CHAT_ID:$chatId CHAT_NAME:$chatName FROM:$sender TIME:${dateTime.toInstant().toEpochMilli()} MESSAGE:$message"
    }
}
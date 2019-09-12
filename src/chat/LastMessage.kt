package chat

class LastMessage(val chatName:String, val chatMessage: ChatMessage) {
    override fun toString(): String {
        return "CHAT_NAME:$chatName " + chatMessage.toString()
    }

    fun toStringForApp(): String {
        return "CHAT_NAME:$chatName " + chatMessage.toStringForApp()
    }
}
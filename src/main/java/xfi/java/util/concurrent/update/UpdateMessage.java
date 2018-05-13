package xfi.java.util.concurrent.update;

class UpdateMessage {
    private final String messageId;
    private final String body;

    UpdateMessage(String messageId, String body) {
        this.messageId = messageId;
        this.body = body;
    }

    String getMessageId() {
        return messageId;
    }

    String getBody() {
        return body;
    }
}

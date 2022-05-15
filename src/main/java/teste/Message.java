package teste;

import java.io.File;

public class Message {
    private String content;
    private File attachment;

    public Message(String content) {
        this.content = content;
    }

    public Message(String content, String path) {
        this.content = content;
        this.attachment = new File(path);
    }

    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(String filePath) {
        this.attachment = new File(filePath);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


package dtos;

public class EmailDTO {
    private String message;
    private String subject;

    public EmailDTO(String message, String subject) {
        this.message = message;
        this.subject = subject;
    }

    public EmailDTO() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

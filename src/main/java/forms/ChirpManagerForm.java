package forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.ElementCollection;
import java.util.Collection;

public class ChirpManagerForm {
    private String				subject;
    private Collection<String> attachments;
    private String				text;

    public ChirpManagerForm(){super();}

    @NotBlank
    public String getText() {
        return this.text;
    }
    public void setText(final String text) {
        this.text = text;
    }

    @NotBlank
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    @ElementCollection
    public Collection<String> getAttachments() {
        return this.attachments;
    }
    public void setAttachments(final Collection<String> attachments) {
        this.attachments = attachments;
    }
}

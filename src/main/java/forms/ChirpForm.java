
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;

import org.hibernate.validator.constraints.NotBlank;

import domain.Chorbi;

public class ChirpForm {

	private String				subject;
	private Collection<String>	attachments;
	private String				text;
	private Chorbi				receiver;


	public Chorbi getReceiver() {
		return this.receiver;
	}

	public void setReceiver(final Chorbi receiver) {
		this.receiver = receiver;
	}

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

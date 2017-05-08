
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

public class EventForm {

	private String	title;
	private String	description;
	private String	picture;
	private Date	moment;
	private int		seatsOffered;


	public EventForm() {
		super();
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@Min(1)
	public int getSeatsOffered() {
		return this.seatsOffered;
	}
	public void setSeatsOffered(final int seatsOffered) {
		this.seatsOffered = seatsOffered;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}
}

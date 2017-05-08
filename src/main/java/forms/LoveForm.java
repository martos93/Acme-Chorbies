
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class LoveForm {

	//Atributtes
	private String	comment;
	private int		stars;
	private String	loved;


	//Constructor
	public LoveForm() {
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Range(min = 0, max = 3)
	public int getStars() {
		return this.stars;
	}

	public void setStars(final int stars) {
		this.stars = stars;
	}

	@NotBlank
	public String getLoved() {
		return this.loved;
	}

	public void setLoved(final String loved) {
		this.loved = loved;
	}

}

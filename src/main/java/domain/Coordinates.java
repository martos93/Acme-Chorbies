
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Coordinates {

	// Attributes------------------------------------------------------------------

	private double	latitude;
	private double	longitude;
	private String	country;
	private String	state;
	private String	province;
	private String	city;


	@Digits(integer = 2, fraction = 9)
	public double getLatitude() {
		return this.latitude;
	}
	public void setLatitude(final double latitude) {
		this.latitude = latitude;
	}

	@Digits(integer = 2, fraction = 9)
	public double getLongitude() {
		return this.longitude;
	}
	public void setLongitude(final double longitude) {
		this.longitude = longitude;
	}

	@NotBlank
	public String getCountry() {
		return this.country;
	}
	public void setCountry(final String country) {
		this.country = country;
	}

	public String getState() {
		return this.state;
	}
	public void setState(final String state) {
		this.state = state;
	}

	public String getProvince() {
		return this.province;
	}
	public void setProvince(final String province) {
		this.province = province;
	}

	@NotBlank
	public String getCity() {
		return this.city;
	}
	public void setCity(final String city) {
		this.city = city;
	}

}

package forms;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;


public class ManagerForm {
	
	private String	name;
	private String	surname;
	private String	email;
	private String	phoneNumber;
	
	private String	company;
	private String	holderName;
	private String	brandName;
	private String	number;
	private int		expirationMonth;
	private int		expirationYear;
	private int		cvvCode;
	private String		VAT;
	
	private boolean	acceptTerms;
	private String	username;
	private String	password;
	private String	confirmPassword;
	
	private String	newpassword;
	private String	repeatnewpassword;
	
	private int		managerId;
	
	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "\\+\\d{1,3}?[ -]?\\d{6,14}|\\d{6,14}$")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@NotBlank
	public String getCompany() {
		return this.company;
	}
	public void setCompany(final String company) {
		this.company = company;
	}
	
	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}
	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@Pattern(regexp = "^VISA$|^MASTERCARD$|^DISCOVER$|^DINNERS$|^AMEX$")
	public String getBrandName() {
		return this.brandName;
	}
	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@CreditCardNumber
	@NotBlank
	public String getNumber() {
		return this.number;
	}
	public void setNumber(final String number) {
		this.number = number;
	}

	@Range(min = 1, max = 12)
	public int getExpirationMonth() {
		return this.expirationMonth;
	}
	public void setExpirationMonth(final int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@Min(2017)
	public int getExpirationYear() {
		return this.expirationYear;
	}
	public void setExpirationYear(final int expirationYear) {
		this.expirationYear = expirationYear;
	}

	@Range(min = 100, max = 999)
	public int getCvvCode() {
		return this.cvvCode;
	}
	public void setCvvCode(final int cvvCode) {
		this.cvvCode = cvvCode;
	}
	
	@NotBlank
	public String getVAT() {
		return this.VAT;
	}
	public void setVAT(final String vAT) {
		this.VAT = vAT;
	}

	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean isAcceptTerms() {
		return this.acceptTerms;
	}
	public void setAcceptTerms(final boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

	public String getNewpassword() {
		return this.newpassword;
	}
	public void setNewpassword(final String newpassword) {
		this.newpassword = newpassword;
	}

	public String getRepeatnewpassword() {
		return this.repeatnewpassword;
	}
	public void setRepeatnewpassword(final String repeatnewpassword) {
		this.repeatnewpassword = repeatnewpassword;
	}
	
	public int getManagerId() {
		return this.managerId;
	}
	public void setManagerId(final int managerId) {
		this.managerId = managerId;
	}

}

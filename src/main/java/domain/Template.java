
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Template extends DomainEntity {

	private String		kindRelationship;
	private Integer		aproxAge;
	private String		genre;
	private String		keyword;
	private Coordinates	location;


	@Pattern(regexp = "^ACTIVITIES$|^FRIENDSHIP$|^LOVE$")
	public String getKindRelationship() {
		return this.kindRelationship;
	}
	public void setKindRelationship(final String kindRelationship) {
		this.kindRelationship = kindRelationship;
	}

	@Range(min = 18, max = 99)
	public Integer getAproxAge() {
		return this.aproxAge;
	}
	public void setAproxAge(final Integer aproxAge) {
		this.aproxAge = aproxAge;
	}

	@Pattern(regexp = "^MAN$|^WOMAN$")
	public String getGenre() {
		return this.genre;
	}
	public void setGenre(final String genre) {
		this.genre = genre;
	}

	@NotBlank
	public String getKeyword() {
		return this.keyword;
	}
	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public Coordinates getLocation() {
		return this.location;
	}
	public void setLocation(final Coordinates location) {
		this.location = location;
	}


	//Relationships----------------------------------------------------------------

	private Collection<Chorbi>	results;
	private Chorbi				searcher;


	@NotNull
	@Valid
	@ManyToMany()
	public Collection<Chorbi> getResults() {
		return this.results;
	}

	public void setResults(final Collection<Chorbi> results) {
		this.results = results;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Chorbi getSearcher() {
		return this.searcher;
	}

	public void setSearcher(final Chorbi searcher) {
		this.searcher = searcher;
	}

}

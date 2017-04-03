
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity {

	private Collection<String> banners;


	@ElementCollection
	public Collection<String> getBanners() {
		return this.banners;
	}

	public void setBanners(final Collection<String> banners) {
		this.banners = banners;
	}

}

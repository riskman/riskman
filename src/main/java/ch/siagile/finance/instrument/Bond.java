package ch.siagile.finance.instrument;

import ch.siagile.finance.*;
import ch.siagile.finance.instrument.rating.*;

public class Bond {

	private final String name;
	private final Rating rating;
	private final Area area;

	public static Bond from(String name, Rating rating, Area anArea) {
		return new Bond(name, rating, anArea);
	}

	public static Bond from(String name, String area) {
		return new Bond(name, Rating.NotRated(), Area.from(area));
	}

	public Bond(String name, Rating rating, Area area) {
		this.name = name;
		this.rating = rating;
		this.area = area;
	}

	public Rating rating() {
		return rating;
	}

	@Override
	public boolean equals(Object obj) {
		if (!Bond.class.isInstance(obj))
			return false;
		Bond bond = (Bond) obj;
		return bond.name.equals(name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public boolean isLocated(String someAreas) {
		for (String anArea : someAreas.split(",")) {
			if (Area.from(anArea).equals(area))
				return true;
		}
		return false;
	}

}
package ch.siagile.finance.instrument;

import ch.siagile.finance.instrument.rating.Rating;

public class Bond {

	private final String name;
	private final Rating rating;

	public static Bond from(String name, Rating rating) {
		return new Bond(name, rating);
	}
	
	public static Bond from(String name) {
		return new Bond(name, Rating.NR());
	}

	public Bond(String name, Rating rating) {
		this.name = name;
		this.rating = rating;
	}

	public Rating rating() {
		return rating;
	}

	@Override
	public boolean equals(Object obj) {
		if(!Bond.class.isInstance(obj)) return false;
		Bond bond = (Bond)obj;
		return bond.name.equals(name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
package ch.siagile.finance.check;

import ch.siagile.finance.base.*;

public class MaxCheck extends SingleCheck {

	public MaxCheck(double value) {
		super(value);
	}

	public boolean check(Ratio value) {
		return value.isLowerOrEqualsTo(value());
	}

}

package ch.siagile.finance;

import static ch.siagile.finance.fixtures.Fixtures.*;
import static ch.siagile.finance.money.Money.*;

import static ch.siagile.finance.matcher.IsRatingInMatcher.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import ch.siagile.finance.instrument.*;
import ch.siagile.finance.instrument.rating.*;
import ch.siagile.finance.location.*;
import ch.siagile.finance.money.*;
import ch.siagile.finance.position.*;

public class PositionTest {
	@Test
	public void shouldCreateAccountPosition() {
		AccountPosition account = new AccountPosition("pippo", CHF(500));
		
		assertEquals(CHF(500), account.balance());
	}
	
	@Test
	public void shouldCreateEquityPosition() {
		Equity equity = Equity.from("IBM");
		EquityPosition position = new EquityPosition(equity, 5, CHF(IBM_PRICE));
		
		assertEquals(CHF(5*IBM_PRICE), position.balance());
	}
	
	@Test
	public void shouldCreatePortfolioWith2Positions() {
		AccountPosition 		account 	= account("pippo", CHF(500));
		EquityPosition 	equity 		= equity("IBM", 5, CHF(IBM_PRICE));

		Positions 		positions 	= new Positions(account, equity);
		
		Money totalAmount = account.balance().plus(equity.balance());
		assertEquals(totalAmount, positions.value());
	}
	
	@Test
	public void shouldBondPositionHaveOwnBalance() {
		BondPosition bondPosition = bond(interAmericaDevBankBond(), CHF(5000), "102 %");
		
		assertEquals(CHF(5100), bondPosition.balance());
	}

	private Bond interAmericaDevBankBond() {
		return Bond.from("Inter American Dev. Bank",MoodyRatings.find("Aaa"), Area.from("UE"));
	}
	
	@Test
	public void shouldTreatBondPositionLikePosition() {
		BondPosition bondPosition = bond(interAmericaDevBankBond(), CHF(5000), "102 %");
		
		AccountPosition accountPosition = account("pippo", CHF(500));
		Positions positions = new Positions(bondPosition, accountPosition);
		
		assertEquals(CHF(5600), positions.value());
	}
	
	@Test
	public void shouldBondPositionHaveRating() {
		BondPosition bondPosition = bond(interAmericaDevBankBond(), CHF(5000), "102 %");
		
		assertEquals(bondPosition.rating(), MoodyRatings.find("Aaa"));
	}

	@Test
	public void shouldMatchBondPositingInRatings() {
		BondPosition bondPosition = bond(interAmericaDevBankBond(), CHF(5000), "102 %");
		
		MoodyRating C = MoodyRatings.find("C");
		MoodyRating AAA = MoodyRatings.find("Aaa");
		
		assertThat(bondPosition, is(ratingIn(C, AAA)));
	}
}

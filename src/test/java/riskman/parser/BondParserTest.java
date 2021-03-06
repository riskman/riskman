package riskman.parser;

import static riskman.money.Money.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import riskman.position.*;

public class BondParserTest {

	private BondPositionParser importBond;

	private String bond1 = "pippo1;Oacc;EUR;0;3495227;20111024 - 4.875% Procter & Gamble 24-10-11 Pro-rata;;;540.924682617188;540.924682617188;;D;Obbligazioni e simili          ;798.231870875702;540.924682617188;784.503067199707;1043;2;;;;";
	private String bond2 = "pippo2;O      ;EUR;200000;1626851;20100625 - 3.25% Rabobank Nederland 25-06-10;101.568125;100.99;201980;201980;-0.00569199244349528;D ;Obbligazioni e simili          ;298057.896895;201980;292931.594;733;2;;;;";

	@Before
	public void setUp() {
		importBond = new BondPositionParser();
	}

	@Test 
	public void shouldImportAccount$Balance() {
		assertThat(parse(bond1).balance(), is(equalTo(EUR(0))));
		assertThat(parse(bond2).balance(), is(equalTo(EUR(201980))));
	}

	@Test 
	public void shouldImportAccount$Type() {
		assertThat(parse(bond1), is(instanceOf(BondPosition.class)));
	}

	@Test 
	public void shouldImportAccount$Owner() {
		assertTrue(parse(bond1).isOwnedBy("pippo1"));
		assertTrue(parse(bond2).isOwnedBy("pippo2"));
	}

	@Test 
	public void shouldImportAccount$Name() {
		assertTrue(parse(bond1).isCalled("20111024 - 4.875% Procter & Gamble 24-10-11 Pro-rata"));
		assertTrue(parse(bond2).isCalled("20100625 - 3.25% Rabobank Nederland 25-06-10"));
	}

	private BondPosition parse(String bond) {
		return (BondPosition)importBond.parse(bond);
	}

}

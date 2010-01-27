package ch.siagile.finance.repository;

import java.util.*;

import ch.siagile.finance.*;
import ch.siagile.finance.instrument.*;
import ch.siagile.finance.location.*;

public class EquityAreaRepository {

	private static List<String> lines = new TextRepository().load("src/test/resources/aree-equity.csv");
	private static AreaRepository areaRepository = new AreaRepository();

	public Area locationOf(Equity equity) {
		for (String line : lines) 
			if(setIdentity(equity, equityId(line)))
				return areaById(areaId(line));
		return Area.from("USA");
	}

	private boolean setIdentity(Equity equity, String equityId) {
		return Identities.from(equity).isIdentifiedBy(equityId);
	}

	private Area areaById(String id) {
		return areaRepository.loadById(id);
	}

	private String areaId(String line) {
		return line.split(",")[2];
	}

	private String equityId(String line) {
		return line.split(",")[1];
	}

}

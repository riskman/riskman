package ch.siagile.finance.matcher;

import static java.text.MessageFormat.*;
import static org.hamcrest.Matchers.*;

import java.security.*;
import java.util.*;

import org.hamcrest.*;

import ch.siagile.finance.constraint.*;
import ch.siagile.finance.matcher.builder.*;
import ch.siagile.finance.position.*;

public class FilterBuilder {

	private List<MatcherBuilder> builders = new ArrayList<MatcherBuilder>() {
		private static final long serialVersionUID = 1L;
		{
			add(new RatingBuilder<Position>());
			add(new CurrencyBuilder());
			add(new TypeBuilder("bond"));
			add(new TypeBuilder("equity"));
			add(new AreaBuilder());
			add(new OwnerBuilder());
			add(new FreeTextBuilder());
		}
	};

	public static Filter from(String definition) {
		final FilterBuilder builder = new FilterBuilder();
		return new Filter(builder.build(definition));
	}

	public Filter build(String definition) {
		return new Filter(anyOfFilters(selections(definition)));
	}

	private String[] selections(String definition) {
		return cleanUp(definition).split("\\+");
	}

	private String cleanUp(String definition) {
		String result = definition.replaceAll("[ ]*([,:+])[ ]*", "$1");
		for (MatcherBuilder b : builders)
			result = b.cleanUp(result);
		return result;
	}

	private Matcher<Position> anyOfFilters(String[] definitions) {
		Filters filters = new Filters();
		for (String definition : definitions)
			filters.add(allOfFilters(definition));
		return anyOf(filters.matchers);
	}

	private Matcher<Position> allOfFilters(String definitions) {
		Filters filters = new Filters();
		for (String definition : definitions.split(":"))
			filters.add(toFilter(definition));
		return allOf(filters.matchers);
	}

	private Matcher<Position> toFilter(String definition) {
		for (MatcherBuilder b : builders) {
			if (b.canBuild(definition))
				return b.build(definition);
		}
		throw new InvalidParameterException(format("wrong definition: {0}",
				definition));
	}
}

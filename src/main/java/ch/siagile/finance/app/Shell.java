package ch.siagile.finance.app;

import static java.text.MessageFormat.*;

import java.util.*;

import ch.siagile.finance.command.*;
import ch.siagile.finance.position.*;

public class Shell {

	private static final List<Command> commands = new LinkedList<Command>() {
		{
			add(new EquityCommand(""));
			add(new AreaCommand(""));
			add(new CurrencyCommand(""));
			add(new RatingCommand(""));
		}
	};

	public String execute(Positions positions, String definition) {
		if (definition.equals(""))
			return "OK";

		return commandFor(definition).execute(positions);
	}

	private Command commandFor(String definition) {

		for (Command command : commands) {
			if (command.canExecute(definition)) {
				return command.createFrom(definition);
			}
		}
		throw new RuntimeException(format("unknown command for {0}", definition));
	}

}
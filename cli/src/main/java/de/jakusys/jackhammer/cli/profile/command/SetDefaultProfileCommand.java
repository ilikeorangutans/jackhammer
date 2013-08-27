package de.jakusys.jackhammer.cli.profile.command;

import de.jakusys.jackhammer.cli.profile.ProfileFacade;
import io.airlift.command.Arguments;
import io.airlift.command.Command;

import javax.inject.Inject;

/**
 * @author jakobk
 */
@Command(name = "default", description = "Sets the default profile")
public class SetDefaultProfileCommand implements Runnable {

	@Inject
	private ProfileFacade facade;

	@Arguments(title = "profile", required = true)
	private String name;

	@Override
	public void run() {
		facade.setDefault(name);

	}
}

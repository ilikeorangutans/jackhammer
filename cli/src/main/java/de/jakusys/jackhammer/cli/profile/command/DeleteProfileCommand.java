package de.jakusys.jackhammer.cli.profile.command;

import de.jakusys.jackhammer.cli.profile.ProfileFacade;
import io.airlift.command.Arguments;
import io.airlift.command.Command;

import javax.inject.Inject;

/**
 * @author Jakob Külzer
 */
@Command(name = "remove", description = "Removes a profile")
public class DeleteProfileCommand implements Runnable {

	@Arguments(title = "profile", description = "Name of profile")
	private String name;

	@Inject
	private ProfileFacade profileFacade;

	@Override
	public void run() {
		profileFacade.remove(name);
	}
}

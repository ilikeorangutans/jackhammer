package de.jakusys.jackhammer.cli.profile.command;

import com.google.inject.Inject;
import de.jakusys.jackhammer.cli.profile.ImmutableProfile;
import de.jakusys.jackhammer.cli.profile.Profile;
import de.jakusys.jackhammer.cli.profile.ProfileFacade;
import io.airlift.command.Arguments;
import io.airlift.command.Command;
import io.airlift.command.Option;

import java.net.URL;

/**
 * @author Jakob Külzer
 */
@Command(name = "add", description = "Add a new profile or modify existing")
public class AddProfileCommand implements Runnable {

	@Inject
	private ProfileFacade profileFacade;

	@Option(name = {"--username", "-u"}, description = "Username to connect", required = true)
	private String username = "admin";

	@Option(name = {"--password", "-p"}, description = "Password to connect", required = true)
	private String password = "admin";

	@Option(name = {"-h", "--host"}, description = "Server url", required = true)
	private URL url;

	@Arguments(title = "profile", required = true, description = "Name of the profile")
	private String name;

	@Override
	public void run() {
		final Profile profile = new ImmutableProfile(name, url, username, password);
		profileFacade.persist(profile);
	}
}

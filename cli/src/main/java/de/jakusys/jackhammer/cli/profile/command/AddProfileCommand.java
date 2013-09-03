/**
 * Copyright (C) 2013 Jakob Külzer (jakob.kuelzer@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

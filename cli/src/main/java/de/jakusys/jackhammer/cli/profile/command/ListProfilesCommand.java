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

import de.jakusys.jackhammer.cli.profile.Profile;
import de.jakusys.jackhammer.cli.profile.ProfileFacade;
import io.airlift.command.Command;

import javax.inject.Inject;

/**
 * @author Jakob Külzer
 */
@Command(name = "list", description = "List existing profileFacade")
public class ListProfilesCommand implements Runnable {

	@Inject
	private ProfileFacade profileFacade;

	@Override
	public void run() {

		System.out.println("Available Profiles: ");

		for (Profile profile : profileFacade.getAll()) {
			profileToString(profile);
		}

		System.out.println();
		System.out.println("Default Profile: ");

		profileToString(profileFacade.getDefault());
	}

	private void profileToString(Profile profile) {
		System.out.println(String.format("    %s %s %s %s", profile.getName(), profile.getUsername(), (profile.getPassword() == null || profile.getPassword().isEmpty() ? "<blank>" : "<password set>"), profile.getHost()));
	}
}

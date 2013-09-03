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

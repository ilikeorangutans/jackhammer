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
package de.jakusys.jackhammer.cli.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import de.jakusys.jackhammer.cli.profile.Profile;
import de.jakusys.jackhammer.cli.profile.ProfileFacade;
import org.apache.jackrabbit.commons.JcrUtils;

import javax.inject.Singleton;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

/**
 * Guice module for dependencies. Probably overkill but I like having proper DI.
 *
 * @author Jakob Külzer
 */
public class JackhammerModule extends AbstractModule {

	private final String[] args;

	public JackhammerModule(String[] args) {
		this.args = args;
	}

	@Override
	protected void configure() {
		bind(ProfileFacade.class).in(Singleton.class);
		bind(Profile.class).toProvider(ProfileFacade.class);
	}

	@Provides
	public String[] provideArgs() {
		return args;
	}

	@Provides
	public Session provideSession(Profile profile) {
		try {
			System.out.println("Connecting to " + profile.getHost().toString() + " with username " + profile.getUsername() + "...");
			return JcrUtils.getRepository(profile.getHost().toString()).login(new SimpleCredentials(profile.getUsername(),
					profile.getPassword().toCharArray()));
		} catch (RepositoryException e) {
			throw new RuntimeException("Unable to obtain session", e);
		}
	}
}

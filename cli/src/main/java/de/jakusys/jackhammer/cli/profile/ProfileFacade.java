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
package de.jakusys.jackhammer.cli.profile;

import com.google.inject.Provider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @author Jakob Külzer
 */
public class ProfileFacade implements Provider<Profile> {

	public static final String URL = "url";

	public static final String USERNAME = "username";

	public static final String PASSWORD = "password";

	public static final String NAME = "name";

	public static final String DEFAULT = "default";

	public static final String DEFAULT_URL = "http://localhost:4402/crx/server";

	public static final String DEFAULT_USERNAME = "admin";

	public static final String DEFAULT_PASSWORD = "admin";

	private final Preferences preferences;

	public ProfileFacade() {
		preferences = Preferences.userNodeForPackage(ProfileFacade.class);
	}

	/**
	 * Provides the currently active profile.
	 *
	 * @return
	 */
	@Override
	public Profile get() {
		return getDefault();
	}

	/**
	 * Returns the default connection profile. Checks if the user has set a default profile.
	 *
	 * @return
	 */
	public Profile getDefault() {
		try {

			final String defaultProfile = preferences.get(DEFAULT, "");
			Profile profile = get(defaultProfile);
			if (profile != null)
				return profile;

			return new ImmutableProfile("<system default>", new URL(DEFAULT_URL), DEFAULT_USERNAME, DEFAULT_PASSWORD);
		} catch (MalformedURLException e) {
			throw new RuntimeException("Could not load default profile", e);
		}
	}

	/**
	 * Returns the currently active profile or null if no such profile.
	 *
	 * @return
	 */
	public Profile get(String name) {
		if (name == null || name.isEmpty())
			return null;

		final String sanitizedName = sanitizeProfileName(name);

		try {
			if (!preferences.nodeExists(sanitizedName))
				return null;

			return createProfileFromPreferences(preferences.node(sanitizedName));

		} catch (BackingStoreException e) {
			throw new RuntimeException("Unable to retrieve profile", e);
		}

	}

	/**
	 * Creates instances of Profile from preferences nodes.
	 *
	 * @param node
	 * @return
	 */
	private Profile createProfileFromPreferences(Preferences node) {
		try {
			return new ImmutableProfile(node.get(NAME, ""), new URL(node.get(URL, "")), node.get(USERNAME, ""), node.get(PASSWORD, ""));
		} catch (MalformedURLException e) {
			throw new RuntimeException("Unable to create profile", e);
		}
	}

	public List<Profile> getAll() {
		try {
			List<Profile> profiles = new ArrayList<Profile>();
			for (String name : preferences.childrenNames()) {
				final Profile profile = createProfileFromPreferences(preferences.node(name));
				profiles.add(profile);
			}
			return profiles;
		} catch (BackingStoreException e) {
			throw new RuntimeException("Unable to read profiles", e);
		}
	}

	public void persist(Profile profile) {

		System.out.println("Saving profile " + profile.getName() + "...");

		String name = sanitizeProfileName(profile.getName());

		System.out.println("name = " + name);

		Preferences node = preferences.node(name);

		node.put(NAME, profile.getName());
		node.put(URL, profile.getHost().toString());
		node.put(USERNAME, profile.getUsername());
		node.put(PASSWORD, profile.getPassword());

		try {
			node.sync();
		} catch (BackingStoreException e) {
			throw new RuntimeException("Unable to save profile " + name, e);
		}
	}

	public void remove(String name) {
		try {
			preferences.node(sanitizeProfileName(name)).removeNode();
			preferences.sync();
		} catch (BackingStoreException e) {
			throw new RuntimeException("Could not remove profile \"" + name + "\"", e);
		}
	}

	private String sanitizeProfileName(String name) {
		return name.replaceAll("[^a-zA-Z0-9]+", "");
	}

	public void setDefault(String name) {
		final Profile profile = get(sanitizeProfileName(name));

		if (profile == null) {
			System.out.println("No such profile \"" + name + "\"");
			return;
		}

		preferences.put(DEFAULT, sanitizeProfileName(name));
		try {
			preferences.sync();
		} catch (BackingStoreException e) {
			throw new RuntimeException("Unable to set default profile", e);
		}
	}
}

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

import com.google.common.base.Objects;

import java.net.URL;

/**
 * @author Jakob Külzer
 */
public class ImmutableProfile implements Profile {

	private final URL host;

	private final String username;

	private final String password;

	private final String name;

	public ImmutableProfile(String name, URL host, String username, String password) {
		this.name = name;
		this.host = host;
		this.username = username;
		this.password = password;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public URL getHost() {
		return host;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(ImmutableProfile.class).add("name", name).add("host", host).add("username", username).add("password", (password.length() > 0 ? "yes" : "empty")).toString();
	}
}

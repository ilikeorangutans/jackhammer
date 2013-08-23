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
package de.jakusys.jackhammer.cli.command;

import io.airlift.command.Option;
import io.airlift.command.OptionType;
import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.net.URL;

/**
 * @author jakobk
 */
public abstract class RemoteCommand implements Runnable {

	@Option(name = {"--username", "-u"}, description = "Username to connect, defaults to \"admin\".", type = OptionType.GLOBAL)
	private String username = "admin";

	@Option(name = {"--password", "-p"}, description = "Password to connect, defaults to \"admin\".", type = OptionType.GLOBAL)
	private String password = "admin";

	@Option(name = {"-h", "--host"}, description = "Server url", required = true)
	private URL url;

	protected Session getSession() throws RepositoryException {
		return JcrUtils.getRepository(getUrl().toExternalForm()).login(new SimpleCredentials(getUsername(), getPassword().toCharArray()));
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public URL getUrl() {
		return url;
	}
}

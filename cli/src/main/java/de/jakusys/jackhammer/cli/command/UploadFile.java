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

import io.airlift.command.Arguments;
import io.airlift.command.Command;
import io.airlift.command.Option;
import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.File;

/**
 * @author jakobk
 */
@Command(name = "file", description = "Upload a single file to the JCR")
public class UploadFile extends RemoteCommand {

	@Arguments(title = "file", description = "File to upload", required = true)
	private File file;

	@Option(name = "-p", description = "Path in the JCR to write to", required = true)
	private String path;

	@Override
	public void run() {

		try {
			System.out.println("file = " + file + " -> path = " + path);

			Session session = getSession();

			Node node = session.getRootNode().getNode(path);

			System.out.println("node = " + node);



		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}

	}
}

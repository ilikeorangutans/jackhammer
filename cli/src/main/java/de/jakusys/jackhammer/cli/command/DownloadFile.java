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
import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.*;

/**
 * @author Jakob Külzer
 */
@Command(name = "file", description = "Download a single file")
public class DownloadFile extends RemoteCommand {

	@Arguments(title = "Path")
	private String path;

	@Override
	public void run() {

		try {
			Session session = getSession();

			Node rootNode = session.getRootNode();

			NodeIterator nodeIterator = rootNode.getNodes();
			while (nodeIterator.hasNext()) {
				Node next = nodeIterator.nextNode();
				System.out.println("next = " + next.getPath());

			}

			Node node = rootNode.getNode(path);
			InputStream inputStream = JcrUtils.readFile(node);

			String filename = node.getName();

			File file = new File(filename);

			file.createNewFile();

			OutputStream outputStream = new FileOutputStream(file);
			IOUtils.copy(inputStream, outputStream);

			outputStream.close();

		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}

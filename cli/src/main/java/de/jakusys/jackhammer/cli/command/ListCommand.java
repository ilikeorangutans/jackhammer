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

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * @author Jakob Külzer
 */
@Command(name = "list", description = "Lists nodes")
public class ListCommand extends RemoteCommand implements Runnable {

	@Arguments(title = "path", required = true)
	private String path;

	@Override
	public void run() {

		try {
			final Session session = getSession();

			final String relativePath = path.startsWith("/") ? path.substring(1) : path;

			Node node = session.getRootNode();
			if (!"".equals(relativePath))
				node = session.getRootNode().getNode(relativePath);

			System.out.println(node.getPath() + " [" + node.getPrimaryNodeType().getName() + "]");

			for (NodeIterator ni = node.getNodes(); ni.hasNext(); ) {
				Node child = ni.nextNode();

				System.out.println(String.format("  %-20s [%s]", child.getName(), child.getPrimaryNodeType().getName()));
			}

		} catch (RepositoryException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}
}

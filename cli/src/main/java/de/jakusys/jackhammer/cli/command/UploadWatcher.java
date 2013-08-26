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
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.File;

/**
 * @author jakobk
 */
@Command(name = "watch", description = "Watch a root for changes and automatically upload them")
public class UploadWatcher extends RemoteCommand {

	@Option(name = "-p", description = "Root path in the JCR for uploads, defaults to \"/\"")
	private String rootPath = "";

	@Arguments(title = "root", description = "Directory to watch for filesystem changes")
	private File directory;

	@Override
	public void run() {

		Session session;

		Node node;
		try {
			session = getSession();

			if ("".equals(rootPath)) {
				node = session.getRootNode();
			} else {

				String tmp = rootPath;
				if (rootPath.startsWith("/"))
					tmp = rootPath.substring(1);

				node = session.getRootNode().getNode(tmp);
			}
		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}

		FileAlterationObserver observer = new FileAlterationObserver(directory);
		FileAlterationMonitor monitor = new FileAlterationMonitor(1000);
		FileAlterationListener listener = new UploadingFileAlternationListener(directory, session, node);

		observer.addListener(listener);

		monitor.addObserver(observer);
		try {
			monitor.start();

		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

}

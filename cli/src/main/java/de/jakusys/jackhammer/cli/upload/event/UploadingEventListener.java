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
package de.jakusys.jackhammer.cli.upload.event;

import com.google.common.eventbus.Subscribe;
import de.jakusys.jackhammer.cli.upload.handler.FileHandler;
import de.jakusys.jackhammer.cli.upload.handler.factory.FileHandlerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * @author Jakob Külzer
 */
public class UploadingEventListener {

	private final FileHandlerFactory fileHandlerFactory;

	private final Session session;

	private final Node rootNode;

	public UploadingEventListener(FileHandlerFactory fileHandlerFactory, Session session, Node rootNode) {
		this.fileHandlerFactory = fileHandlerFactory;
		this.session = session;
		this.rootNode = rootNode;
	}

	@Subscribe
	public void onUploadEvent(UploadEvent event) {

		try {

			FileHandler fileHandler = fileHandlerFactory.create(event.getOperation(), event.getFile());
			fileHandler.handle(rootNode);

			session.save();

		} catch (RepositoryException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}

}

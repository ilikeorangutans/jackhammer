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

import de.jakusys.jackhammer.cli.util.PathRelativizer;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.jackrabbit.commons.JcrUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UploadingFileAlternationListener implements FileAlterationListener {

	private final File root;

	private final Session session;

	private final Node node;

	public UploadingFileAlternationListener(File directory, Session session, Node node) {
		this.root = directory;
		this.session = session;
		this.node = node;
	}

	@Override
	public void onStart(FileAlterationObserver observer) {
		if (!session.isLive())
			throw new RuntimeException("Session is not live!");
	}

	@Override
	public void onDirectoryCreate(File directory) {

		String path = PathRelativizer.relativize(root, directory);
		System.out.print("+ " + path + " -> ");
		try {
			Node folder = JcrUtils.getOrAddFolder(node, path);
			session.save();
			System.out.println(folder.getPath());
		} catch (RepositoryException e) {
			throw new RuntimeException("Could not upload directory", e);
		}
	}

	@Override
	public void onDirectoryChange(File directory) {
	}

	@Override
	public void onDirectoryDelete(File directory) {
		String path = PathRelativizer.relativize(root, directory);
		System.out.println("- " + path);
	}

	@Override
	public void onFileCreate(File file) {
		String path = PathRelativizer.relativize(root, file);
		System.out.println("+ " + path);

		// Make sure directory exists:
		onDirectoryChange(file.getParentFile());

		uploadFile(file);

	}

	private void uploadFile(File file) {

		MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();

		try {
			String relPath = PathRelativizer.relativize(root, file.getParentFile());
			Node parent = getNode(relPath);
			String contentType = mimetypesFileTypeMap.getContentType(file);
			JcrUtils.putFile(parent, file.getName(), contentType, new FileInputStream(file));

			session.save();
		} catch (RepositoryException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (FileNotFoundException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	private Node getNode(String relPath) throws RepositoryException {
		return "".equals(relPath) ? node : node.getNode(relPath);
	}

	@Override
	public void onFileChange(File file) {
		String path = PathRelativizer.relativize(root, file);
		System.out.println("^ " + path);

		uploadFile(file);

	}

	@Override
	public void onFileDelete(File file) {
		String path = PathRelativizer.relativize(root, file);
		System.out.println("- " + path);
	}

	@Override
	public void onStop(FileAlterationObserver observer) {

	}
}

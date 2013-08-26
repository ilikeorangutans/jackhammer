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

	}

	@Override
	public void onDirectoryCreate(File directory) {

		String path = PathRelativizer.relativize(root, directory);
		System.out.println("+ " + path);
		try {
			JcrUtils.getOrAddFolder(node, path);

			session.save();
		} catch (RepositoryException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
			System.out.println("parent.getPath() = " + parent.getPath());
			String contentType = mimetypesFileTypeMap.getContentType(file);
			System.out.println("contentType = " + contentType);
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

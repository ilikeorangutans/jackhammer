package de.jakusys.jackhammer.cli.command;

import de.jakusys.jackhammer.cli.util.PathRelativizer;
import io.airlift.command.Arguments;
import io.airlift.command.Command;
import io.airlift.command.Option;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

		FileAlterationListener listener = new UploadingFileAlterationListener(directory, session, node);

		observer.addListener(listener);

		monitor.addObserver(observer);
		try {
			monitor.start();

		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	private static class UploadingFileAlterationListener implements FileAlterationListener {

		private final File root;

		private final Session session;

		private final Node node;

		public UploadingFileAlterationListener(File directory, Session session, Node node) {
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

			Node parent = null;
			try {
				parent = node.getNode(PathRelativizer.relativize(root, file.getParentFile()));
				System.out.println("parent.getPath() = " + parent.getPath());
				JcrUtils.putFile(parent, file.getName(), "text/plain", new FileInputStream(file));
			} catch (RepositoryException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			} catch (FileNotFoundException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}

		}

		@Override
		public void onFileChange(File file) {
			String path = PathRelativizer.relativize(root, file);
			System.out.println("^ " + path);
			
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
}

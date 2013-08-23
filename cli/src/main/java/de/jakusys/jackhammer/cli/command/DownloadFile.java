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
 * @author jakobk
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

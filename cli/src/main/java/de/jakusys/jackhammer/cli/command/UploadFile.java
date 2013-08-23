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

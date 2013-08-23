package de.jakusys.jackhammer.cli.command;

import io.airlift.command.Command;
import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

/**
 * @author Jakob Külzer
 */
@Command(name = "connect", description = "Tests connection to the server with the given parameters.")
public class Connect extends RemoteCommand {

	@Override
	public void run() {

		try {
			Session session = getSession();

			System.out.println("Connection successful. Session: " + session);
		} catch (RepositoryException e) {
			System.out.println("Connection failed: " + e.getMessage());
		}

	}

}

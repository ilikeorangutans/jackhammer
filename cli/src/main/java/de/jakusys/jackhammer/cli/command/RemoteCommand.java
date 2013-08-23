package de.jakusys.jackhammer.cli.command;

import io.airlift.command.Option;
import io.airlift.command.OptionType;
import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.net.URL;

/**
 * @author jakobk
 */
public abstract class RemoteCommand implements Runnable {

	@Option(name = {"--username", "-u"}, description = "Username to connect, defaults to \"admin\".", type = OptionType.GLOBAL)
	private String username = "admin";

	@Option(name = {"--password", "-p"}, description = "Password to connect, defaults to \"admin\".", type = OptionType.GLOBAL)
	private String password = "admin";

	@Option(name = {"-h", "--host"}, description = "Server url", required = true)
	private URL url;

	protected Session getSession() throws RepositoryException {
		return JcrUtils.getRepository(getUrl().toExternalForm()).login(new SimpleCredentials(getUsername(), getPassword().toCharArray()));
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public URL getUrl() {
		return url;
	}
}

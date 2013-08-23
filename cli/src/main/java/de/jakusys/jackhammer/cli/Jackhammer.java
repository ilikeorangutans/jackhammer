package de.jakusys.jackhammer.cli;

import de.jakusys.jackhammer.cli.command.Connect;
import de.jakusys.jackhammer.cli.command.DownloadFile;
import de.jakusys.jackhammer.cli.command.UploadFile;
import de.jakusys.jackhammer.cli.command.UploadWatcher;
import io.airlift.command.Cli;
import io.airlift.command.Help;

/**
 * @author Jakob Külzer
 */
public class Jackhammer {

	public static void main(String[] args) {

		Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("jackhammer")
				.withDescription("The JCR power tool!")
				.withCommands(Help.class, Connect.class)
				.withDefaultCommand(Help.class);

		builder
				.withGroup("upload")
				.withDescription("Uploads things to the server")
				.withDefaultCommand(UploadFile.class)
				.withCommands(UploadFile.class, UploadWatcher.class);

		builder
				.withGroup("download")
				.withDescription("Download things from the server")
				.withDefaultCommand(Help.class)
				.withCommand(DownloadFile.class);

		Cli<Runnable> cli = builder.build();
		cli.parse(args).run();

	}

}

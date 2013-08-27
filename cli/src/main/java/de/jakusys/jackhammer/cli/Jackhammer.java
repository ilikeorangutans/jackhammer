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
package de.jakusys.jackhammer.cli;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.jakusys.jackhammer.cli.command.*;
import de.jakusys.jackhammer.cli.module.JackhammerModule;
import de.jakusys.jackhammer.cli.profile.command.AddProfileCommand;
import de.jakusys.jackhammer.cli.profile.command.DeleteProfileCommand;
import de.jakusys.jackhammer.cli.profile.command.ListProfilesCommand;
import de.jakusys.jackhammer.cli.profile.command.SetDefaultProfileCommand;
import io.airlift.command.Cli;
import io.airlift.command.Help;

/**
 * @author Jakob Külzer
 */
public class Jackhammer {

	public static void main(String[] args) {

		final Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("jackhammer")
				.withDescription("The JCR power tool!")
				.withDefaultCommand(Help.class)
				.withCommands(
						Help.class,
						Connect.class,
						ListCommand.class
				);

		builder
				.withGroup("upload")
				.withDescription("Uploads things to the server")
				.withDefaultCommand(UploadFile.class)
				.withCommands(
						UploadFile.class,
						UploadWatcher.class);

		builder
				.withGroup("download")
				.withDescription("Download things from the server")
				.withDefaultCommand(Help.class)
				.withCommand(DownloadFile.class);

		builder
				.withGroup("profile")
				.withDescription("Create, edit, list or remove profiles")
				.withDefaultCommand(ListProfilesCommand.class)
				.withCommands(
						ListProfilesCommand.class,
						AddProfileCommand.class,
						DeleteProfileCommand.class,
						SetDefaultProfileCommand.class);

		final Cli<Runnable> cli = builder.build();
		final Runnable runnable = cli.parse(args);

		// Don't inject into Help. It uses @Inject internally, but doesn't provide the constructor that Guice expects.
		// Ugly.
		if (!(runnable instanceof Help)) {
			final Injector injector = Guice.createInjector(new JackhammerModule(args));
			injector.injectMembers(runnable);
		}
		runnable.run();

	}

}

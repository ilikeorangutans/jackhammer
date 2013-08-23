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

import de.jakusys.jackhammer.cli.command.*;
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

		builder
				.withGroup("browse")
				.withDescription("Browse the repository")
				.withDefaultCommand(ListCommand.class)
				.withCommands(ListCommand.class);

		Cli<Runnable> cli = builder.build();
		cli.parse(args).run();

	}

}

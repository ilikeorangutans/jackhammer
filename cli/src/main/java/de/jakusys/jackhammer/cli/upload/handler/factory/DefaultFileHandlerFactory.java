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
package de.jakusys.jackhammer.cli.upload.handler.factory;

import de.jakusys.jackhammer.cli.path.Path;
import de.jakusys.jackhammer.cli.upload.handler.FileHandler;
import de.jakusys.jackhammer.cli.upload.handler.Operation;
import de.jakusys.jackhammer.cli.upload.handler.impl.CreateAndUpdateFileFileHandler;
import de.jakusys.jackhammer.cli.upload.handler.impl.CreateDirectoryFileHandler;
import de.jakusys.jackhammer.cli.upload.handler.impl.DeleteFileHandler;
import de.jakusys.jackhammer.cli.util.PathRelativizer;

import java.io.File;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Jakob Külzer
 */
public class DefaultFileHandlerFactory implements FileHandlerFactory {

	private final PathRelativizer pathRelativizer;

	public DefaultFileHandlerFactory(PathRelativizer pathRelativizer) {
		this.pathRelativizer = pathRelativizer;
	}

	@Override
	public FileHandler create(Operation operation, File file) {

		final boolean isDirectory = file.isDirectory();
		final Path path = pathRelativizer.relativize(file);

		switch (operation) {

			case Delete:
				return new DeleteFileHandler(path);

			case Create:
			case Update:
				checkState(file.canRead(), "File " + file.getAbsolutePath() + " is not readable.");

				if (isDirectory) {
					return new CreateDirectoryFileHandler(path, file);
				} else {
					return new CreateAndUpdateFileFileHandler(path, file);
				}
			default:
		}

		return NoOpFileHandler.INSTANCE;

	}

}

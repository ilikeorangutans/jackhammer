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
package de.jakusys.jackhammer.cli.upload.event;

import de.jakusys.jackhammer.cli.path.Path;
import de.jakusys.jackhammer.cli.upload.handler.Operation;

import java.io.File;

/**
 * @author Jakob Külzer
 */
public class UploadEvent {

	private final File file;

	private final Operation operation;

	private final Path path;

	public UploadEvent(File file, Operation operation, Path path) {
		this.file = file;
		this.operation = operation;
		this.path = path;
	}

	/**
	 * Returns the file where the actual data can be found.
	 *
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Returns the requested operation.
	 *
	 * @return
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * Returns the determined path in the JCR.
	 *
	 * @return
	 */
	public Path getPath() {
		return path;
	}

}

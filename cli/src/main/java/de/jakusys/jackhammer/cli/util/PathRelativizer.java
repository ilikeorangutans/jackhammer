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
package de.jakusys.jackhammer.cli.util;

import de.jakusys.jackhammer.cli.path.Path;

import java.io.File;

/**
 * @author Jakob Külzer
 */
public class PathRelativizer {

	/**
	 * Returns the path of <tt>file</tt> relative to <tt>base</tt> as a string.
	 *
	 * @param base
	 * @param file
	 * @return
	 */
	public static String relativize(File base, File file) {
		return base.toURI().relativize(file.toURI()).getPath();
	}

	private final File baseDir;

	public PathRelativizer(File baseDir) {
		this.baseDir = baseDir;
	}

	public Path relativize(File file) {
		return new Path(baseDir.toURI().relativize(file.toURI()).getPath());
	}

}

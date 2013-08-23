package de.jakusys.jackhammer.cli.util;

import java.io.File;

/**
 * @author jakobk
 */
public class PathRelativizer {

	public static String relativize(File base, File file) {

		return base.toURI().relativize(file.toURI()).getPath();

	}

}

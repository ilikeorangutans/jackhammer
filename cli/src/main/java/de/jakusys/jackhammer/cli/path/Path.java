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
package de.jakusys.jackhammer.cli.path;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class to handle path manipulation. This class represents a relative path in a JCR. Instances of this class are immutable. All methods that modify the path, actually return new
 * instances.
 * <p/>
 *
 * @author Jakob Külzer
 */
public class Path {

	private final String path;

	private List<String> segments;

	public Path(String path) {
		checkNotNull(path, "Given path is null.");

		String pathToUse = path;
		// Make sure we don't have any double slashes or backwards slashes:
		pathToUse = pathToUse.replaceAll("[/\\\\]+", "/");

		// Strip leading slashes:
		if (pathToUse.startsWith("/"))
			pathToUse = pathToUse.substring(1);

		if (pathToUse.endsWith("/"))
			pathToUse = pathToUse.substring(0, pathToUse.length() - 1);

		this.path = pathToUse;
	}

	/**
	 * Appends the given path to this path.
	 *
	 * @param append
	 * @return
	 */
	public Path append(Path append) {
		return new Path(path + "/" + append);
	}

	/**
	 * Returns depth of the path. The root path is level 0.
	 *
	 * @return
	 */
	public int getDepth() {
		return getSegments().size();
	}

	/**
	 * Returns the last segment of the path.
	 *
	 * @return
	 */
	public String getName() {
		if (isEmptyPath())
			return "";

		return getSegments().get(getSegments().size() - 1);
	}

	/**
	 * Returns the parent path of this path. If there is no such path, null is returned.
	 *
	 * @return
	 */
	public Path getParent() {
		if (isEmptyPath())
			return null;

		return subpath(0, getDepth() - 1);

	}

	public List<String> getSegments() {
		if (isEmptyPath())
			return Collections.EMPTY_LIST;

		if (segments != null) {
			return segments;
		}

		segments = Lists.newArrayList(Splitter.on('/').omitEmptyStrings().split(path));
		return segments;
	}

	public boolean isEmptyPath() {
		return path.isEmpty();
	}

	/**
	 * Returns a sub path of the current path.
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public Path subpath(int from, int to) {
		if (isEmptyPath())
			throw new IllegalStateException("Cannot handle subpath of root path.");

		return new Path(Joiner.on('/').join(getSegments().subList(from, to)));
	}

	@Override
	public String toString() {
		return path;
	}
}

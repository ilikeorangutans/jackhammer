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

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author jakobk
 */
public class PathTest {

	@Test
	public void testCreation() {
		assertThat(new Path("/").isEmptyPath(), is(true));
		assertThat(new Path("//////////").isEmptyPath(), is(true));
		assertThat(new Path("/foo").toString(), is("foo"));
		assertThat(new Path("/foo////bar/").toString(), is("foo/bar"));
		assertThat(new Path("foo\\\\bar\\").toString(), is("foo/bar"));
	}

	@Test
	public void testGetDepth() {
		assertThat(new Path("/").getDepth(), is(0));
		assertThat(new Path("/foo").getDepth(), is(1));
		assertThat(new Path("/foo/bar").getDepth(), is(2));
	}

	@Test
	public void testGetParent() {
		assertThat(new Path("").getParent(), nullValue());
		assertThat(new Path("bar").getParent().isEmptyPath(), is(true));
		assertThat(new Path("foo/bar").getParent().toString(), is("foo"));
	}

	@Test
	public void testAppend() {
		assertThat(new Path("").append(new Path("")).toString(), is(""));
		assertThat(new Path("foo").append(new Path("bar")).toString(), is("foo/bar"));
		assertThat(new Path("foo/bar").append(new Path("")).toString(), is("foo/bar"));
	}

	@Test
	public void testGetName() {
		assertThat(new Path("foo").getName(), is("foo"));
		assertThat(new Path("foo/bar").getName(), is("bar"));
	}
}

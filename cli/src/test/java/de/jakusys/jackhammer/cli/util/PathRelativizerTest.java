package de.jakusys.jackhammer.cli.util;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author jakobk
 */
public class PathRelativizerTest {

	@Test
	public void testFoo() {
		Assert.assertThat(PathRelativizer.relativize(new File("foo"), new File("foo/bar")), Matchers.is("bar"));
	}
}

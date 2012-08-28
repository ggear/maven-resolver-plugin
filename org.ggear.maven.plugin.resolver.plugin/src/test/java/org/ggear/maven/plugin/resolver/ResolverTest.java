package org.ggear.maven.plugin.resolver;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

/**
 * 
 * @author graham.gear
 * 
 */
public class ResolverTest extends AbstractMojoTestCase {

	public void testModelMetaData2Path() throws FileNotFoundException {
		
		assertEquals("", ResolverMojo.model2Path(""));
		assertEquals("", ResolverMojo.model2Path("."));
		assertEquals("org", ResolverMojo.model2Path("org"));
		assertEquals("org" + File.separator + "ggear",
				ResolverMojo.model2Path("org.ggear"));
		assertEquals("org" + File.separator + "ggear",
				ResolverMojo.model2Path(".org.ggear"));
		assertEquals("org" + File.separator + "ggear",
				ResolverMojo.model2Path(".org.ggear."));
	}

	public void testPath2ModelMetaData() {

		assertEquals("", ResolverMojo.path2Model(""));
		assertEquals("", ResolverMojo.path2Model(File.separator));
		assertEquals("org", ResolverMojo.path2Model("org"));
		assertEquals("org.ggear",
				ResolverMojo.path2Model("org" + File.separator + "ggear"));
		assertEquals(
				"org.ggear.4_5",
				ResolverMojo.path2Model("org" + File.separator + "ggear"
						+ File.separator + "4.5"));
		assertEquals(
				"org.ggear.4_5",
				ResolverMojo.path2Model("" + File.separator + "org"
						+ File.separator + "ggear" + File.separator + "4.5"));
		assertEquals(
				"org.ggear.4_5",
				ResolverMojo.path2Model("org" + File.separator + "ggear"
						+ File.separator + "4.5" + File.separator + ""));

	}

}

package org.ggear.maven.plugin.resolver;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.twdata.maven.mojoexecutor.MojoExecutor;

/**
 * 
 * Resolve modules from a directory tree, installing them to a local repository
 * 
 * @author graham.gear
 * 
 * @goal install
 * @phase install
 * @requiresProject false
 * 
 */
public class ResolverInstallMojo extends ResolverMojo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		super.execute();

		for (ModuleReference module : modules.values()) {

			List<MojoExecutor.Element> moduleMetaData = new ArrayList<MojoExecutor.Element>();
			if (module.getSource() != null)
				moduleMetaData.add(MojoExecutor.element(MojoExecutor
						.name("file"), module.getSource().getAbsolutePath()));
			else
				moduleMetaData.add(MojoExecutor.element(MojoExecutor
						.name("file"), module.getDestinationPom()
						.getAbsolutePath()));
			moduleMetaData.add(MojoExecutor.element(MojoExecutor
					.name("pomFile"), module.getDestinationPom()
					.getAbsolutePath()));

			MojoExecutor
					.executeMojo(
							MojoExecutor.plugin(
									MojoExecutor
											.groupId("org.apache.maven.plugins"),
									MojoExecutor
											.artifactId("maven-install-plugin"),
									MojoExecutor.version("2.3.1")),
							MojoExecutor.goal("install-file"),
							MojoExecutor.configuration(moduleMetaData
									.toArray(new MojoExecutor.Element[moduleMetaData
											.size()])), MojoExecutor
									.executionEnvironment(project, session,
											buildPluginManager));
		}
	}

}

package org.ggear.maven.plugin.resolver;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.twdata.maven.mojoexecutor.MojoExecutor;

/**
 * 
 * Resolve modules from a directory tree, deploying them to a remote repository
 * 
 * @author graham.gear
 * 
 * @goal deploy
 * @phase deploy
 * @requiresProject false
 * 
 */
public class ResolverdDeployMojo extends ResolverMojo {

	/**
	 * Target repository ID
	 * 
	 * @parameter expression="${maven.resolver.repositoryId}"
	 * @required
	 */
	private String repositoryId;

	/**
	 * Target repository URL
	 * 
	 * @parameter expression="${maven.resolver.repositoryUrl}"
	 * @required
	 */
	private String repositoryUrl;

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
			moduleMetaData.add(MojoExecutor.element(
					MojoExecutor.name("repositoryId"), repositoryId));
			moduleMetaData.add(MojoExecutor.element(MojoExecutor.name("url"),
					repositoryUrl));
			moduleMetaData.add(MojoExecutor.element(MojoExecutor
					.name("pomFile"), module.getDestinationPom()
					.getAbsolutePath()));

			MojoExecutor
					.executeMojo(
							MojoExecutor.plugin(MojoExecutor
									.groupId("org.apache.maven.plugins"),
									MojoExecutor
											.artifactId("maven-deploy-plugin"),
									MojoExecutor.version("2.6")),
							MojoExecutor.goal("deploy-file"),
							MojoExecutor.configuration(moduleMetaData
									.toArray(new MojoExecutor.Element[moduleMetaData
											.size()])), MojoExecutor
									.executionEnvironment(project, session,
											buildPluginManager));
		}
	}
}

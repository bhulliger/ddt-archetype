ddt-archetype
=============

Maven Archetype for generating a 

Abstract
========

(at)-doclet allows you to document your project inside your code with Javadoc and generate a userfriendly maven site
afterwards.

How it work's:

1. Define annotations for different types you want do document, and write appropriate Javadoc.

2. Define apt-templates for the different annotations.

3. Configure your Maven Project to use the doclet.

4. Generate your output.

Installation and usage
======================

1. Checkout the project.

2. Install maven (if not yet done).

3. run mvn install, to install the doclet in your local maven repository. (update about a central repository will be added soon).

4. generate a new project using the following command:
   
   mvn archetype:generate -DarchetypeGroupId=ch.puzzle.maven.archetypes -DarchetypeArtifactId=ddt-archetype -DarchetypeVersion=1.0 -DgroupId=<groupId> -DartifactId=<artifactId> -Dversion=<version>
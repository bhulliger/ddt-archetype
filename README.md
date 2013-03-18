ddt-archetype
=============

1. Checkout the project.

2. Install maven (if not yet done).

3. run mvn install, to install the doclet in your local maven repository. (update about a central repository will be added soon).

4. generate a new project using the following command:
   
   mvn archetype:generate -DarchetypeGroupId=ch.puzzle.maven.archetypes -DarchetypeArtifactId=ddt-archetype -DarchetypeVersion=1.0 -DgroupId=<groupId> -DartifactId=<artifactId> -Dversion=<version>
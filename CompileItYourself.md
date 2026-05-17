# Compiling Instructions

You need to install WeaponMaster as a dependency in Maven.
You can do this by downloading the WeaponMaster `.jar` file and running:

```
mvn install:install-file -Dfile=weaponmaster-X.Y.Z.jar -DgroupId=io.github.greatericontop -DartifactId=weaponmaster -Dversion=X.Y.Z -Dpackaging=jar
```

Replace `X.Y.Z` with the version number of WeaponMaster that the `pom.xml` file in GreatUHC requires.

Then it's just a standard Maven build.
Run `mvn package` in the root directory of the project.
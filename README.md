# Hlæja Core Plugin

One Gradle plugin to rule them all, a simple thing to bind the tasks and in automation's grasp, control them.

## Extension

### Extension Git

The GitExtension enhances versioning by dynamically appending the Git hash before "snapshot" in the version string. For example, `0.0.0-SNAPSHOT` becomes `0.0.0.0a2b3c4d-SNAPSHOT`, ensuring each build reflects its commit origin, prevents overwriting existing versions. This feature aids developers during development by providing clear version identification.

### Extension Info

The InfoExtension provides information for name and version, vendor name, and UTC timestamp.

### Extension Config

The ConfigExtension provides a find or findOrDefault for getting a property or environment.

## Releasing plugin

Run `release.sh` script from `master` branch.

## Publishing plugin

### Publish plugin locally

```shell
./gradlew clean build publishToMavenLocal
```

### Publish plugin to repository

```shell
./gradlew clean build publish
```

### Global gradle properties

To authenticate with Gradle to access repositories that require authentication, you can set your user and token in the `gradle.properties` file.

Here's how you can do it:

1. Open or create the `gradle.properties` file in your Gradle user home directory:
    - On Unix-like systems (Linux, macOS), this directory is typically `~/.gradle/`.
    - On Windows, this directory is typically `C:\Users\<YourUsername>\.gradle\`.
2. Add the following lines to the `gradle.properties` file:
    ```properties
    repository.user=your_user
    repository.token=your_token_value
    ```
   or use environment variables `REPOSITORY_USER` and `REPOSITORY_TOKEN`

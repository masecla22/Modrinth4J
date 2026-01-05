package masecla.modrinth4j.environment;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.testcontainers.containers.ComposeContainer;

import lombok.SneakyThrows;

public class EnvironmentCreator {
    @SneakyThrows
    public static void main(String[] args) {
        // new GenericContainer<>(
        // new DockerfileBuilder().
        // )

        // Start by cloning the monorepo at https://github.com/modrinth/code
        // Use JGit
        String commit = "cace1a54cdfc3630036d8f897663cead3234aed5";
        File tmpDir = new File(".testing-artifacts");
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }

        // Clone
        File repoDir = new File(tmpDir, "code");
        System.out.println("Cloning Modrinth codebase...");

        if (!repoDir.exists()) {
            Git.cloneRepository()
                    .setURI("https://github.com/modrinth/code")
                    .setDirectory(repoDir)
                    .call();
        }

        // Checkout the specific commit
        Git git = Git.open(repoDir);

        // Fetch and make sure the commit exists
        // Checkout to main first
        git.checkout().setName("main").call();
        git.fetch().call();
        git.pull().call();
        git.checkout().setName(commit).call();

        System.out.println("Checked out commit " + commit);
        System.out.println("Directory: " + tmpDir.getAbsolutePath());

        System.out.println("Creating Docker image...");

        File file = new File(repoDir, "docker-compose.yml");

        // Remove the container_name fields from the docker-compose.yml
        // using Regex
        String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
        content = content.replaceAll("(?m)^\\s*container_name:.*$", "");
        java.nio.file.Files.write(file.toPath(), content.getBytes());
        

        // Build the Docker image using the Dockerfile in the repo
        try (
            ComposeContainer environment = new ComposeContainer(
                file
            ).withOptions("--profiles with-labrinth")
        ) {
            environment.start();
            
            System.out.println("Environment started!");

            Thread.sleep(1000000);
        }
    }
}

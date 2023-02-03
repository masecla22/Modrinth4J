package masecla.modrinth4j.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import masecla.modrinth4j.client.agent.UserAgent;

/**
 * Tests the {@link UserAgent} class.
 */
public class UserAgentTests {
    /**
     * This method tests the nulled agent.
     */
    @Test
    public void testNulledAgent() {
        UserAgent agent = UserAgent.builder().build();
        assertEquals("Modrinth4J / No User Agent", agent.toString());
    }

    /**
     * This method tests the agent with only the project name.
     */
    @Test
    public void testOnlyProjectName() {
        UserAgent agent = UserAgent.builder()
                .projectName("Modrinth4J").build();
        assertEquals("Modrinth4J", agent.toString());
    }

    /**
     * This method tests the agent with only the author name.
     */
    @Test
    public void testOnlyAuthorName() {
        UserAgent agent = UserAgent.builder()
                .authorUsername("masecla22").build();
        assertEquals("masecla22", agent.toString());
    }

    /**
     * This method tests the agent with only the version.
     */
    @Test
    public void testOnlyVersion() {
        UserAgent agent = UserAgent.builder()
                .projectVersion("1.0.0").build();
        assertEquals("Modrinth4J / No User Agent", agent.toString());
    }

    /**
     * This method tests the agent with only the contact.
     */
    @Test
    public void testOnlyContact() {
        UserAgent agent = UserAgent.builder()
                .contact("masecla22#4309").build();
        assertEquals("(masecla22#4309)", agent.toString());
    }

    /**
     * This method tests the agent with only the project name and author name.
     */
    @Test
    public void testWithoutVersion() {
        UserAgent agent = UserAgent.builder()
                .projectName("Modrinth4J")
                .authorUsername("masecla22")
                .contact("masecla22#4309").build();
        assertEquals("masecla22/Modrinth4J (masecla22#4309)", agent.toString());
    }

    /**
     * This method tests the agent with only the project name and version.
     */
    @Test
    public void testWithoutContact() {
        UserAgent agent = UserAgent.builder()
                .projectName("Modrinth4J")
                .authorUsername("masecla22")
                .projectVersion("1.0.0").build();
        assertEquals("masecla22/Modrinth4J/1.0.0", agent.toString());
    }

    /**
     * This method tests the agent with everything.
     */
    @Test
    public void testWithEverything() {
        UserAgent agent = UserAgent.builder()
                .projectName("Modrinth4J")
                .authorUsername("masecla22")
                .projectVersion("1.0.0")
                .contact("masecla22#4309").build();
        assertEquals("masecla22/Modrinth4J/1.0.0 (masecla22#4309)", agent.toString());
    }

    /**
     * This method tests the agent with only the project name and version.
     */
    @Test
    public void testWithOnlyProjectNameAndVersion() {
        UserAgent agent = UserAgent.builder()
                .projectName("Modrinth4J")
                .projectVersion("1.0.0").build();
        assertEquals("Modrinth4J/1.0.0", agent.toString());
    }

    /**
     * This method tests the agent with only the author name and version.
     */
    @Test
    public void testWithOnlyAuthorNameAndVersion() {
        UserAgent agent = UserAgent.builder()
                .authorUsername("masecla22")
                .projectVersion("1.0.0").build();

        assertEquals("masecla22/1.0.0", agent.toString());
    }
}

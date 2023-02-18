import com.gosecuri.Main;
import com.gosecuri.agent.Agent;
import com.gosecuri.htmlgeneration.AgentPageGenerator;
import com.gosecuri.security.HtpasswdGenerator;
import com.gosecuri.utils.PathUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Test
    public void testOutputPath() {
        // Test that output path is set to generated/ if no argument is passed
        String[] args = new String[]{};
        Main.main(args);
        assertEquals("com/gosecuri/html/generated/", PathUtils.GENERATED_FOLDER_PATH);

        // Test that output path is set to the passed argument if one is given
        args = new String[]{"myOutputPath/"};
        Main.main(args);
        assertEquals("myOutputPath/", PathUtils.GENERATED_FOLDER_PATH);
    }

    @Test
    public void testAgentListCreation() {
        // Test that agent list is created from text files
        Agent agent1 = new Agent("001|James Bond|007".split("\\|"));
        Agent agent2 = new Agent("002|Jason Bourne|DeltaOne".split("\\|"));
        List<Agent> expectedAgents = Arrays.asList(agent1, agent2);
        List<Agent> actualAgents = Main.createAgentList();
        assertEquals(expectedAgents, actualAgents);
    }

    @Test
    public void testHtmlGeneration() {
        // Test that HTML files are generated
        Agent testAgent = new Agent("001|James Bond|007".split("\\|"));
        String outputPath = "test/com/gosecuri/html/generated/";
        AgentPageGenerator apg = new AgentPageGenerator(outputPath, testAgent);
        try {
            apg.generateHTML();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File expectedFile = new File(outputPath + "agent001.html");
        assertTrue(expectedFile.exists());
    }

    @Test
    public void testHtpasswdGeneration() {
        // Test that htpasswd file is generated
        Agent testAgent = new Agent("001|James Bond|007".split("\\|"));
        String outputPath = "test/com/gosecuri/html/generated/";
        List<Agent> testAgents = Arrays.asList(testAgent);
        HtpasswdGenerator htpasswdGenerator = new HtpasswdGenerator(outputPath, testAgents);
        try {
            htpasswdGenerator.generateHtpasswd();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File expectedFile = new File(outputPath + "agents.htpasswd");
        assertTrue(expectedFile.exists());
    }
}

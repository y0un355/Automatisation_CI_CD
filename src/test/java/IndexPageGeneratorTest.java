package com.gosecuri.htmlgeneration;

import com.gosecuri.agent.Agent;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.gosecuri.utils.PathUtils.GENERATED_FOLDER_PATH;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IndexPageGeneratorTest {

    @Test
    void generateHTML_ShouldGenerateIndexHTMLFile() throws IOException, NoSuchAlgorithmException {
        List<Agent> agents = new ArrayList<>();
        List<String> agentData = new ArrayList<>();
        agentData.add("007");
        agentData.add("James");
        agentData.add("Bond");
        agentData.add("jamesbond@gmail.com");
        agentData.add("United Kingdom");
        agents.add(new Agent(agentData));

        String outputPath = GENERATED_FOLDER_PATH;

        IndexPageGenerator idp = new IndexPageGenerator(outputPath, agents);
        idp.generateHTML();

        File indexHtml = new File(outputPath + "index.html");

        assertTrue(indexHtml.exists());
    }
}

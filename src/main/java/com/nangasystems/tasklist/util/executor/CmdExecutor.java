package com.nangasystems.tasklist.util.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CmdExecutor implements Executor {

    private static final Logger LOG = LoggerFactory.getLogger(CmdExecutor.class);

    @Override
    public List<String> execute(String command) {
        List<String> results = new ArrayList<>();

        BufferedReader input = null;
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            Process p = builder.start();

            input = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = input.readLine()) != null) {
                results.add(line);
            }
        } catch (Exception err) {
            LOG.error("Unable to run following command: {}. Error message: {}", command, err.getMessage());
        } finally {
            try {
                if(input != null)
                    input.close();
            } catch (IOException e) {
                LOG.error("Unable to close input stream. Error message: {}", e.getMessage());
            }
        }

        return results;
    }
}

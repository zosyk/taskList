package com.nangasystems.tasklist.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdExecutor {

    public static void main(String[] args) throws IOException {

        try {
            String line;
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "chcp.com 65001 && tasklist"); // 65001 for UTF-8
            Process p = builder.start();

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            while ((line = input.readLine()) != null) {
                System.out.println(line); //<-- Parse data here.
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

    }
}

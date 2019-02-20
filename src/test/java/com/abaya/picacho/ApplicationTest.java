package com.abaya.picacho;

import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ApplicationTest {
    @Test
    public void testCheckFile() {
        File root = new File("/Users/paul/git/PPava/projects/jixindai/jixindai-wap/src");

        Map<String, String> container = new HashMap<String, String>();
        File[] children = root.listFiles();
        for (File child : children) {
            construct(child, container, "");
        }

        for (File child : children) {
            search(child, container, "");
        }
    }

    private void construct(File file, Map<String, String> container, String path) {
        String current = path;

        if (current.length() != 0)
            current += "/";

        current += file.getName();


        if ("publicStyle".equals(file.getName())) {
            current = path; // 去掉publicStyle
        }

        if (container.containsKey(current.toLowerCase())) {
            System.out.println("WARNING: FIND ERROR!");
        }

        if (current.contains("/")) {
            container.put("/" + file.getName().toLowerCase(), "/" + file.getName());
            container.put(current.toLowerCase(), current);
        }

        if (file.isFile()) return ;

        for (File child : file.listFiles()) {
            construct(child, container, current);
        }
    }

    private void search(File file, Map<String, String> container, String path) {
        String fileName = file.getName();
        String current = path + "/" + fileName;

        if (file.isFile()) {
            searchInFile(file, container, current);
            return ;
        }

        searchInDirectory(file, container, current);
    }

    private void searchInDirectory(File file, Map<String, String> container, String path) {
        for (File child : file.listFiles()) {
            search(child, container, path);
        }
    }

    private void searchInFile(File file, Map<String, String> container, String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                String key = findInContainer(line, container);
                if (key == null) continue;

                // System.out.println("FILE[" + path + "], LINE[" + line.trim() + "], PATH[" + container.get(key) + "]" );


                if (upperLowerCaseIssue(line, container.get(key))) {
                    System.out.println("FILE[" + path + "], LINE[" + line.trim() + "], PATH[" + container.get(key) + "]" );
                    // System.out.println("(" + path + ", " + line.trim() + "\r\nPATH    " + container.get(key) );
                    // System.out.println("----------");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String findInContainer(String line, Map<String, String> container) {
        for (Map.Entry<String, String> entry : container.entrySet()) {
            if (line.toLowerCase().contains(entry.getKey().toLowerCase())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private boolean upperLowerCaseIssue(String line, String value) {
        if (line.contains(value)) return false;
        if (line.contains("https://")) return false;
        if (line.contains("http://")) return false;

        int index = line.toLowerCase().indexOf(value.toLowerCase()) + value.length();
        if (index == line.length()) return true;

        char c = line.charAt(index);
        if (Character.isLowerCase(c) || Character.isUpperCase(c)) return false;

        if (c == '>' && line.charAt(index-value.length()-1) == '<') {
            return false;
        }
        return true;
    }
}

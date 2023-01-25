package com.dt180g.project.support;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Support class containing procedures for I/O operations.
 */
public final class IOHelper {
    private static final String BASE_PATH = System.getProperty("user.dir") + "/_RepoResources/";
    private static final PrintStream OUT = System.out;  // our standard output stream
    private static final Scanner IN = new Scanner(System.in);  // our standard input stream
    private static final String WELCOME_HEADER = loadHeader("header_welcome.txt", "DT180G DUNGEON");
    private static final String GAME_OVER_HEADER = loadHeader("header_game_over.txt", "GAME OVER");
    private static final String GAME_COMPLETED_HEADER = loadHeader("header_game_completed.txt", "GAME COMPLETED");

    private IOHelper() { }

    /**
     * Helper method used internally to load ANSI text headers from resource files.
     * @param fileName name of file containing ANSI text.
     * @param defaultText header value to use as default if ANSI text cannot be loaded.
     * @return header value.
     */
    private static String loadHeader(final String fileName, final String defaultText) {
        try (Scanner scanner = new Scanner(new File(BASE_PATH + fileName))) {
            return scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            return defaultText;
        }
    }

    /**
     * Helper method used to retrieve data from XML files.
     * @param fileName name of XML file containing target data.
     * @return list of mapped values from file.
     */
    public static List<Map<String, String>> readFromFile(final String fileName) {
        List<Map<String, String>> output = new ArrayList<>();

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            //Document doc = db.parse(new File(BASE_PATH + fileName));
            Document doc = db.parse(new File(BASE_PATH + fileName));

            doc.getDocumentElement().normalize();  // optional, but recommended

            Element root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            if (nodeList == null) {  // we don't have any data
                return output;
            }

            //List<Map<String, String>> itemDetails = new ArrayList<>();  // holder for w
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);  // our base item

                if (node.getNodeType() != Node.ELEMENT_NODE) {  // ignore pure text nodes
                    continue;
                }

                NodeList detailsNodes = node.getChildNodes();

                if (detailsNodes == null) {
                    continue;
                }

                Map<String, String> itemDetails = new HashMap<>();
                for (int j = 0; j < detailsNodes.getLength(); j++) {
                    Node elem = detailsNodes.item(j);
                    if (elem.getNodeType() != Node.ELEMENT_NODE) {  // ignore pure text nodes
                        continue;
                    }

                    // we have a real node
                    String name = elem.getNodeName();
                    String content = elem.getTextContent();
                    itemDetails.put(name, content);
                    //System.out.println(name + ": " + content);
                }

                output.add(itemDetails);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    /**
     * Support method to handle and validate numeric inputs.
     * @param min lower boundary of numeric limit.
     * @param max higher boundary of numeric limit.
     * @return the value provided by user.
     */
    public static int getInput(final int min, final int max) {
        int input = -1;
        while (true) {
            try {
                OUT.print("> ");
                input = Integer.parseInt(IN.next());
                if (input > max || input < min) {
                    throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException | NumberFormatException ex) {
                String msg = String.format("Sorry, only integer values between %d and %d are allowed!", min, max);
                OUT.println(msg);
            }
        }

        return input;
    }

    /**
     * Support method used for outputting menu options on screen.
     * @param it the iterator to be used.
     */
    public static void printOptionItems(final ListIterator<?> it) {
        while (it.hasNext()) {  // we utilize the overloaded 'toString()' for printout
            OUT.println(it.nextIndex() + 1 + ". " + it.next());
        }
    }

    /**
     * Support method used to format rows and columns in table fashion for output.
     * @param data the table data to be processed.
     * @return the table data formatted in single string value.
     */
    public static String formatAsTable(final List<List<String>> data) {
        int[] maxLengths = new int[data.get(0).size()];
        for (List<String> row : data)  {
            for (int i = 0; i < row.size(); i++) {
                maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
            }
        }

        StringBuilder formatBuilder = new StringBuilder();
        for (int maxLength : maxLengths) {
            formatBuilder.append("%-").append(maxLength + 2).append("s");
        }

        String format = formatBuilder.toString();

        StringBuilder result = new StringBuilder();
        for (List<String> row : data) {
            result.append(String.format(format, row.toArray(new String[0]))).append("\n");
        }

        return result.toString();
    }

    /**
     * Prints welcome header, used by client during application start.
     */
    public static void printWelcomeHeader() {
        OUT.print(Constants.ANSI_BLUE + WELCOME_HEADER + Constants.ANSI_RESET);
    }

    /**
     * Prints game over header, used by client to illustrate failed game.
     */
    public static void printGameOverHeader() {
        OUT.print(Constants.ANSI_RED + GAME_OVER_HEADER + Constants.ANSI_RESET);
    }

    /**
     * Prints completion header, used by client to illustrate successful game.
     */
    public static void printGameCompletedHeader() {
        OUT.print(Constants.ANSI_GREEN + GAME_COMPLETED_HEADER + Constants.ANSI_RESET);
    }
}

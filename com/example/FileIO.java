import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * My program reads from an input file, adds input and writes output.
 *
 * @author  Kent Gatera
 * @version 1.0
 * @since   2024-March-24
 */


public class FileIO {

    /**
    * Pleases the style checker.
    *
    * @exception IllegalStateException Utility class
    * @see IllegalStateException
    */

    private FileIO() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * This is where the main code will be.
     *
     * @param args
     */

    public static void main(String[] args) {
        // Initializing i/o file paths and final answer array.
        String filePath = "input.txt";
        String outputFilePath = "output.txt";
        List<String> parsedOutputStringList = new ArrayList<>();
        try {
            // Initializing line-by-line sum and line-by-line output.
            int oneLineSum = 0;
            String oneLineStrOut = "0";
            // reading input
            File inputFile = new File(filePath);
            Scanner fileReader = new Scanner(inputFile);
            // While the scanner has a next line.
            while (fileReader.hasNextLine()) {
                // This string allows us to gather strings in our input.
                String placeHolderErrorString = "";
                // For each line reset the line sum.
                // Scanner reads said next line.
                String inputLine = fileReader.nextLine();
                // Split up all elements on the line into array.
                String[] tempArrayParsedStrings = inputLine.split(" ");
                // Resetting the line by line sum
                oneLineSum = 0;
                // Resetting the sum to a value palletable for both cases.
                oneLineStrOut = "0";
                // For each element on the "LINE" try to convert to int.
                // KENT to be clear this "FOR LOOP" is all for "ONE" single LINE of input!
                for (int arrayCounter = 0; arrayCounter < tempArrayParsedStrings.length; arrayCounter++) {
                    // Try to convert to int
                    try {
                        // If element I got is not empty (There must be something values on the line).
                        if (!tempArrayParsedStrings[arrayCounter].isEmpty()) {
                            // If indexed element I'm getting is numerical but previous line is string.
                            if (!isNumeric(oneLineStrOut) && isNumeric(tempArrayParsedStrings[arrayCounter])) {
                                // Assign/add the word and say it is not a number.
                                String wordErrorString = "Error:" + placeHolderErrorString + " is not a number.";
                                // Update the output text file.
                                oneLineStrOut = wordErrorString;
                            }
                            // If both the line and indexed element is non-numerical.
                            else if (!isNumeric(oneLineStrOut) && !isNumeric(tempArrayParsedStrings[arrayCounter])) {
                                // Add the word that on the index to the rest.
                                String newWord = tempArrayParsedStrings[arrayCounter];
                                placeHolderErrorString += " " + newWord;
                                // Assign/add the word and say it is not a number.
                                String wordErrorString = "Error:" + placeHolderErrorString + " is not a number.";
                                // Update the output text file.
                                oneLineStrOut = wordErrorString;
                            }
                            // When the line addition logic runs smooth the whole line.
                            else if (isNumeric(oneLineStrOut) && isNumeric(tempArrayParsedStrings[arrayCounter])) {
                                // Convert indexed element to integer and add it to one line sum.
                                int parsedIntVal = Integer.parseInt(tempArrayParsedStrings[arrayCounter]);
                                /* If my upcoming input is numeric and the result i have is numeric.
                                That means it's been numbers so far...*/
                                oneLineSum += parsedIntVal;
                                // One line output (If it passes the catch will never happen).
                                String LineOutputString = String.valueOf(oneLineSum);
                                oneLineStrOut = LineOutputString;
                            }
                            // If the indexed element is non-numeric but the line output is so far.
                            else if (isNumeric(oneLineStrOut) && !isNumeric(tempArrayParsedStrings[arrayCounter])) {
                                // Discard the line ouput so far as an error has been detected.
                                String newWord = tempArrayParsedStrings[arrayCounter];
                                placeHolderErrorString += " " + newWord;
                                // Assign/add the word and say it is not a number.
                                String wordErrorString = "Error:" + placeHolderErrorString + " is not a number.";
                                // Update the output text file.
                                oneLineStrOut = wordErrorString;
                            } else {
                                /* Else throw an error on purpose 
                                 (We already know it is a blank space which can't be converted to an integer). 
                                */
                                Integer.parseInt(tempArrayParsedStrings[arrayCounter]);
                            }
                            // When the line is empty.
                        } else {
                            // Assign the word and say it is not a number.
                            String LineOutputString = "Error: Line is empty.";
                            // Update the output text file.
                            oneLineStrOut = LineOutputString;
                            // Stops reading input from the line since it contains words.
                            break;
                        }
                    } catch (Exception e) {
                        /* When the indexed element is not a number,
                        so it needs to be appended to output string.*/
                        String newWord = tempArrayParsedStrings[arrayCounter];
                        placeHolderErrorString += " " + newWord;
                        // Assign/add the word and say it is not a number.
                        String wordErrorString = "Error:" + placeHolderErrorString + " is not a number.";
                        // Update the output text file.
                        oneLineStrOut = wordErrorString;
                    }
                }
                // Adding line output answer/data to a list.
                parsedOutputStringList.add(oneLineStrOut);
                // Converting the line output list to array.
                System.out.println(parsedOutputStringList);
            }
            // After the full file is read close file reader Scanner. 
            fileReader.close();
            // Converting the list to an array.
            String[] finalOutputArray = parsedOutputStringList.toArray(new String[0]);
            // Write the output to a file
            try {
                // Initializing file writer object and output file directory.
                FileWriter fileWriter = new FileWriter(outputFilePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for (String output : finalOutputArray) {
                    bufferedWriter.write(output);
                    // Write a new line after each output
                    bufferedWriter.newLine();
                }
                // Close the writer when done
                bufferedWriter.close();
                System.out.println("Output written to " + outputFilePath);
            } catch (IOException e) {
                // Print error message if file cannot be written
                System.out.println("Error: File cannot be written.");
            }
        }
        // File not found.
        catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
    }

    /**
     * This is where the main code will be.
     *
     * @param str The string we need to type check.
     * @return returns the numeric status of argument.
     */

    private static boolean isNumeric(String str) {
        try {
            // Try to parse the string to an integer.
            Integer.parseInt(str);
            // If successful, it's a number.
            return true;
        } // If parsing fails, it's not a number.
        catch (NumberFormatException e) {
            return false; 
        }
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InvertedIndex {

    /*
     * This is a helper function designated to return a String removed of common words
     */
    public static String removeCommonWords(String words) {
        Set<String> stopWords = new HashSet<>(Arrays.asList(
            "the", "a", "an",    // Articles 
            "to", "of", "in", "on", "at", "by",    // Prepositions
            "and", "but", "or",   // Conjuctions 
            "i", "he", "she", "it", "we", "they", "me", "him", "her", "us", "them"   // Pronouns
        ));

        StringBuilder cleanedString = new StringBuilder();
        for (String word : words.trim().split("\\s+")) {  // split by spaces, tabs, indents
            word = word.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();   // remove punctuation and make everything lowercase
            if (!stopWords.contains(word) && !word.isEmpty()) {
                cleanedString.append(word).append(" ");   // append only if it is not a common word
            }
        }
        return cleanedString.toString().trim();
    }

    public static void main(String[] args) throws IOException {

        // File handling
        String filePath = "text.txt"; 
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        // Putting into the HashMap
        Map<String, List<Integer>> invertedIndex = new HashMap<>();
        int position = 1;

        String line;
        while ((line = reader.readLine()) != null) {
            String cleanedLine = removeCommonWords(line);
            String[] words = cleanedLine.trim().split("\\s+");

            for (String word : words) {
                if (!word.isEmpty()) {
                    invertedIndex.computeIfAbsent(word, k -> new ArrayList<>()).add(position++);
                }
            }
        }

        reader.close();

        // Printing it out in a JSON-style text 
        System.out.println("{");
        List<String> sortedKeys = new ArrayList<>(invertedIndex.keySet());
        Collections.sort(sortedKeys);
        for (int i = 0; i < sortedKeys.size(); i++) {
            String key = sortedKeys.get(i);
            System.out.print("  \"" + key + "\": " + invertedIndex.get(key));
            if (i < sortedKeys.size() - 1) System.out.println(",");
            else System.out.println();
        }
        System.out.println("}");
    }
}

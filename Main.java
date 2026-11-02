import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

/*
String parsing project
Rules:
- no regex
- separate collections for words and symbols
- unique words
- words sorted alphabetically
*/

public static void main(String[] args) throws IOException {
    //Sample text: http://textfiles.com/100/ethics.txt
    ArrayList<String> wordsList = new ArrayList<>();
    ArrayList<String> punctuationList = new ArrayList<>();


    try {
        FileReader reader = new FileReader("/Users/catalin/work/stuff/java/Tokens/src/sample.txt");

        //Read file content
        String fileContent = reader.readAllAsString();

        //split file content into uncleaned tokens
        String[] tokens = fileContent.split("\\s"); //matches any whitespace: new line, tab, space

        //Split tokens in two groups, words and punctuation
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].strip().toLowerCase();

            if (tokens[i].equals("-") || tokens[i].contains("&")) {
                punctuationList.add(tokens[i]);
                continue;
            }

            if (tokens[i].isEmpty()) {
                continue;
            }

            wordsList.add(tokens[i]);
        }

        //Clean the words from punctuation and save more punctuation
        //TreeSet is slower than HashSet, but it also sorts the words
        String punctuation="";
        Set<String> cleanedWordsList = new TreeSet<String>(createCleanedListOfWords(wordsList));

        System.out.println(cleanedWordsList);
        System.out.println(cleanedWordsList.size());

        reader.close();
    } catch (FileNotFoundException e) {
        System.out.println("Provide a sample file");
        throw new RuntimeException(e);
    }
}

private static ArrayList<String> createCleanedListOfWords(ArrayList<String> wordsList) {
    ArrayList<String> cleanedListOfWords = new ArrayList<>();

    for (int i = 0; i < wordsList.size(); i++) {
        String word = wordsList.get(i);
        int lastIndex = wordsList.size() - 1;

        //Remove ending punctuation characters and also add them to punctuation list
        if (word.endsWith(")") || word.endsWith("]") || word.endsWith("\"") || word.endsWith(".") || word.endsWith(",")){
            word = word.substring(0, word.length() - 1);
        }

        //Remove beginning characters and also add them to the punctuation list
        if(word.startsWith("*") || word.startsWith("(") || word.startsWith("\"")) {
            word = word.substring(1);
        }

        cleanedListOfWords.add(word);
    }

    return new ArrayList<>(cleanedListOfWords);
}
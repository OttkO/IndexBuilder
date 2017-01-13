package indexing;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by OttkO on 04-Jan-17.
 */
public class Util {
    public static String[] extractKeywords(String input) { // extracts keywords
        {
            String[] keywords = new String[200];
            if (input.contains(","))
            {
                keywords = input.toLowerCase().split(",");
            }
            else
            {
                keywords = input.toLowerCase().split(" ");
            }
            return keywords;
        }
    }
    public static String readLineInFile(String fileName, int x) // reads a line from a file
    {
        String line32 = "n/a";
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
             line32 = lines.skip(x).findFirst().get(); // for faster reading, it goes directly to the line by skipping the previous

        } catch (IOException e) {
            e.printStackTrace();
        }
        return line32;
    }

    public static boolean errorOnEmptyFolder = true;

    public static List<String> getFilesInDirectory(File folder) { // get the files in a directory
        assert folder.isDirectory();
        File[] listOfFiles = folder.listFiles();

        ArrayList<String> fileNames = new ArrayList<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileNames.add(listOfFiles[i].getAbsolutePath());
            }

        }
            if(fileNames.isEmpty() && errorOnEmptyFolder){
            throw new Error("No files found in " + folder.getAbsolutePath());
        }
        return fileNames;
    }
    public static ArrayList<Article> getArticlesListFromKeywordsinFiles(ArrayList<KeywordStructure> input, int startingPoint)
    {
        ArrayList<Article> output = new ArrayList<Article>(); // initialize output
        int endPoint = (startingPoint+ 1) * 100; // calculate end point 0,100,200...
        startingPoint = startingPoint * 100; // calculate start point
        if (input.size() < 100) // take care if the results are less than 100
        {
            endPoint = input.size();
        }
        //put data in article object and add to output
        for (int i = startingPoint; i < endPoint; i++) {

            KeywordStructure key = input.get(i);
            String inputLine = Util.readLineInFile(key.fileName, key.lineNumber);
            String[] splitLine = inputLine.split(";");
            if (splitLine.length <= 7)
            {
                continue;
            }
            Article tmpArticle = new Article();
            tmpArticle.id = splitLine[0];
            tmpArticle.author_ids = getStringArray(new JSONArray(splitLine[1]));
            tmpArticle.description =  splitLine[2];
            tmpArticle.html = splitLine[3];
            tmpArticle.published_date = splitLine[4];
            tmpArticle.title = splitLine[5];
            tmpArticle.link = splitLine[6];
            tmpArticle.domain = splitLine[7];
            output.add(tmpArticle);
        }

        /*
        for (KeywordStructure key:input
                ) {
            String inputLine = Util.readLineInFile(key.fileName, key.lineNumber);
            String[] splitLine = inputLine.split(";");
            Article tmpArticle = new Article();
            tmpArticle.id = splitLine[0];
            tmpArticle.author_ids = getStringArray(new JSONArray(splitLine[1]));
            tmpArticle.description =  splitLine[2];
            tmpArticle.html = splitLine[3];
            tmpArticle.published_date = splitLine[4];
            tmpArticle.title = splitLine[5];
            tmpArticle.link = splitLine[6];
            tmpArticle.domain = splitLine[7];
            output.add(tmpArticle);
        }*/
        return output;

    }
    public static ArrayList<Tweet> getTweetListFromKeywordsinFiles(ArrayList<KeywordStructure> input, int startingPoint)
    {
        /*
        ArrayList<Tweet> output = new ArrayList<Tweet>();  // initialize output
        int endPoint = (startingPoint+ 1) * 100; // calculate end point 0,100,200...
        startingPoint = startingPoint * 100; // calculate start point
        if (input.size() < 100) //take care if the results are less than 100
        {
            endPoint = input.size();
        }
        for (int i = startingPoint; i < endPoint; i++) {
            KeywordStructure key = input.get(i);
            String inputLine = Util.readLineInFile(key.fileName, key.lineNumber);
            String[] splitLine = inputLine.split(";");
            //the full_normalized_ text is column 14, so if it is not there, we continue with next in loop.
            if (splitLine.length <= 14)
            {
                continue;
            }
            //put data in tweet object and add to output
            Tweet tmpTwt = new Tweet();
            tmpTwt.tweetID = splitLine[0];
            tmpTwt.userID = splitLine[3];
            tmpTwt.timestamp = splitLine[10];
            tmpTwt.fullText = splitLine[14];
            if (!tmpTwt.timestamp.contains("0")) { //small extra validation
                output.add(tmpTwt);
            }
        }
        return output; */
        ArrayList<Tweet> output = new ArrayList<Tweet>();  // initialize output
        int endPoint = (startingPoint+ 1) * 100; // calculate end point 0,100,200...
        startingPoint = startingPoint * 100; // calculate start point
        if (input.size() < 100) //take care if the results are less than 100
        {
            endPoint = input.size();
        }
        for (int i = startingPoint; i < endPoint; i++) {
            KeywordStructure key = input.get(i);
            String inputLine = Util.readLineInFile(key.fileName, key.lineNumber);
            String[] splitLine = inputLine.split(";");
            //the full_normalized_ text is column 14, so if it is not there, we continue with next in loop.
            if (splitLine.length < 27)
            {
                continue;
            }
            Tweet tmpTwt = new Tweet();
            tmpTwt.tweetID = splitLine[0];
            tmpTwt.userID = splitLine[3];
            tmpTwt.timestamp = splitLine[10];
            tmpTwt.fullText = splitLine[12];
            output.add(tmpTwt);

        }
        return output;

    }
    //same as above but returns a hashmap
    public static TweetsMap getTweets(ArrayList<KeywordStructure> input, int startingPoint)
    {
        TweetsMap output = new TweetsMap();
        int endPoint = (startingPoint+ 1) * 100;
        startingPoint = startingPoint * 100;
        if (input.size() < 100)
        {
            endPoint = input.size();
        }
        for (int i = startingPoint; i < endPoint; i++) {
            KeywordStructure key = input.get(i);
            String inputLine = Util.readLineInFile(key.fileName, key.lineNumber);
            String[] splitLine = inputLine.split(";");
            output.put(splitLine[0], inputLine);
        }

        /*
        for (KeywordStructure key:input
                ) {
            String inputLine = Util.readLineInFile(key.fileName, key.lineNumber);
            String[] splitLine = inputLine.split(";");
            output.put(splitLine[0],inputLine);
        }*/
        return output;

    }
    ///Gets start position. Indexer does not work when two words are the same. Do not use.
    public static int getPosition(String input,String keyword)
    {
        return input.indexOf(keyword);
    }
    ///Returns a string array from a json array
    static String[] getStringArray(JSONArray arr){
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < arr.length(); i++){
            list.add((String) arr.get(i));
        }
        return list.toArray(new String[list.size()]);
    }
}

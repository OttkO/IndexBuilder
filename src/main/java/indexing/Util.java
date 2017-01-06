package indexing;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;

import javax.json.JsonReader;
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
    public static int getPosition(String input,String keyword)
    {
        return input.indexOf(keyword);
    }
    public static String readLineInFile(String fileName, int x)
    {
        String line32 = "n/a";
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
             line32 = lines.skip(x).findFirst().get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return line32;
    }
    public static String[] extractKeywords(String input) {
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
    public static List<String> GetFilesInDirectory(String directory) {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();

        ArrayList<String> fileNames = new ArrayList<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileNames.add(listOfFiles[i].getAbsolutePath());
            }

        }
        return fileNames;
    }
    public static ArrayList<Article> getArticlesListFromKeywordsinFiles(ArrayList<KeywordStructure> input)
    {
        ArrayList<Article> output = new ArrayList<Article>();
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
        }
        return output;

    }

    static String[] getStringArray(JSONArray arr){
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < arr.length(); i++){
            list.add((String) arr.get(i));
        }
        return list.toArray(new String[list.size()]);
    }
}

package indexing;

import com.mysql.jdbc.Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by OttkO on 04-Jan-17.
 */
public class DbHandler {
    static MysqlConnect mysqlConnect = new MysqlConnect();

    public DbHandler() {

    }


    public static int findFilenameId(String fileName) throws SQLException {
        int id = -1;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "SELECT fileID FROM FILENAME WHERE FILENAME = ?";


        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(insertTableSQL);

            preparedStatement.setString(1, fileName);


            // execute insert SQL stetement
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                id = rs.getInt("fileId");


            }

        } catch (SQLException e) {

            // System.out.println(e.getMessage());
            // System.out.printf("Failed on" + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();

        }
        return id;

    }
    public static KeywordStructure findKeywordInfoArticleIndexes(String filename, String keyword, int position, int linenumber) throws SQLException {
        KeywordStructure result = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL =
                "select i.line_number, f.filename,i.position,i.keyword " +
                        "from index_articles_keywords as i, filename as f " +
                        "where i.fileId=f.id " +
                        "and f.filename=? " +
                        "and i.line_number=? " +
                        "and i.keyword=? " +
                        "and i.position=?";
        //System.out.println(insertTableSQL);

        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(insertTableSQL);

            preparedStatement.setString(1, filename);
            preparedStatement.setInt(2, linenumber);
            preparedStatement.setString(3, keyword);
            preparedStatement.setInt(4, position);

            // System.out.println("preparedStatement = " + preparedStatement);


            // execute insert SQL stetement
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = new KeywordStructure();
                result.position = rs.getInt("position");
                result.lineNumber = rs.getInt("line_number");
                result.fileName = rs.getString("filename");
                result.keyword = rs.getString("keyword");
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            System.out.printf("Failed on" + filename);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }


        }
        return result;

    }
    public static KeywordStructure findKeywordInfoTweetIndexes(String filename, String keyword, int position, int linenumber) throws SQLException {
        KeywordStructure result = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL =
                "select i.line_number, f.filename,i.position,i.keyword " +
                        "from index_tweets_keywords as i, filename as f " +
                        "where i.fileId=f.id " +
                        "and f.filename=? " +
                        "and i.line_number=? " +
                        "and i.keyword=? " +
                        "and i.position=?";
        //System.out.println(insertTableSQL);

        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(insertTableSQL);

            preparedStatement.setString(1, filename);
            preparedStatement.setInt(2, linenumber);
            preparedStatement.setString(3, keyword);
            preparedStatement.setInt(4, position);

            // System.out.println("preparedStatement = " + preparedStatement);


            // execute insert SQL stetement
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = new KeywordStructure();
                result.position = rs.getInt("position");
                result.lineNumber = rs.getInt("line_number");
                result.fileName = rs.getString("filename");
                result.keyword = rs.getString("keyword");
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            System.out.printf("Failed on" + filename);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }


        }
        return result;

    }
    public static ArrayList<KeywordStructure> getKeywordPositionsArticleIndexes(String keyword) throws SQLException {
        ArrayList<KeywordStructure> result = new ArrayList<KeywordStructure>();
        int id = -1;
        PreparedStatement preparedStatement = null;
        String insertTableSQL = "select indexing.index_articles_keywords.position,indexing.index_articles_keywords.line_number,indexing.filename.filename,indexing.index_articles_keywords.keyword\n" +
                "from indexing.index_articles_keywords, indexing.filename\n" +
                "where indexing.index_articles_keywords.fileId = indexing.filename.id and indexing.index_articles_keywords.keyword = ?";


        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(insertTableSQL);

            preparedStatement.setString(1, keyword);


            // execute insert SQL stetement
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                int position = rs.getInt("position");
                int line_number = rs.getInt("line_number");
                String fileName = rs.getString("filename");
                result.add(new KeywordStructure(keyword, line_number, position, fileName));


            }

        } catch (SQLException e) {

            // System.out.println(e.getMessage());
            // System.out.printf("Failed on" + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();

        }
        return result;

    }
    public static ArrayList<KeywordStructure> getKeywordPositionsTweetIndexes(String keyword) throws SQLException {
        ArrayList<KeywordStructure> result = new ArrayList<KeywordStructure>();
        int id = -1;
        PreparedStatement preparedStatement = null;
        String insertTableSQL = "select indexing.index_tweets_keywords.position,indexing.index_tweets_keywords.line_number,indexing.filename.filename,indexing.index_tweets_keywords.keyword\n" +
                "from indexing.index_tweets_keywords, indexing.filename\n" +
                "where indexing.index_tweets_keywords.fileId = indexing.filename.id and indexing.index_tweets_keywords.keyword = ?";


        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(insertTableSQL);

            preparedStatement.setString(1, keyword);


            // execute insert SQL stetement
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {

                int position = rs.getInt("position");
                int line_number = rs.getInt("line_number");
                String fileName = rs.getString("filename");
                result.add(new KeywordStructure(keyword, line_number, position, fileName));


            }

        } catch (SQLException e) {

            // System.out.println(e.getMessage());
            // System.out.printf("Failed on" + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();

        }
        return result;

    }

    public static int insertFileName(String fileName) throws SQLException {
        int fileId = -1;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO FILENAME"
                + "(filename) VALUES"
                + "(?)";

        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, fileName);

            // execute insert SQL stetement
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                fileId=rs.getInt(1);
            }

        } catch (SQLException e) {

            //System.out.println(e.getMessage());
            //System.out.println("Failed at " + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();
        }
        return fileId;

    }
    public static int insertRecordIntoArticleTable(int fileId, int lineNumber, int position, String keyword) throws SQLException {
        int id = -1;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO index_articles_keywords"
                + "(fileId, line_number, position, keyword) VALUES"
                + "(?,?,?,?)";

        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, fileId);
            preparedStatement.setInt(2, lineNumber);
            preparedStatement.setInt(3, position);
            preparedStatement.setString(4, keyword);

            // execute insert SQL stetement
            id = preparedStatement.executeUpdate();

            //System.out.println("Record is inserted into DBUSER table!");

        } catch (SQLException e) {

            // System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();

        }
        return id;

    }
    public static int insertRecordIntoTweetTable(int fileId, int lineNumber, int position, String keyword) throws SQLException {
        int id = -1;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO index_tweets_keywords"
                + "(fileId, line_number, position, keyword) VALUES"
                + "(?,?,?,?)";

        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, fileId);
            preparedStatement.setInt(2, lineNumber);
            preparedStatement.setInt(3, position);
            preparedStatement.setString(4, keyword);

            // execute insert SQL stetement
            id = preparedStatement.executeUpdate();

            //System.out.println("Record is inserted into DBUSER table!");

        } catch (SQLException e) {

            // System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();

        }
        return id;

    }

    public static void createArticleIndexesTable() throws SQLException {
        java.sql.Statement preparedStatement = null;

        String createTable =
                "  CREATE TABLE index_articles_keywords (" +
                "  fileId int(11) NOT NULL, " +
                "  line_number int(11) NOT NULL, " +
                "  position int(11) NOT NULL," +
                "  keyword varchar(500) DEFAULT NULL, " +
                "  PRIMARY KEY (fileId,line_number,position) " +
                ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";
        try {
            preparedStatement = mysqlConnect.connect().createStatement();

            // execute insert SQL stetement
            preparedStatement.executeUpdate(createTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
            // System.out.println(e.getMessage());
            // System.out.printf("Failed at " + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();
        }


    }
    public static void createTweetIndexesTable() throws SQLException {
        java.sql.Statement preparedStatement = null;

        String createTable =
                "  CREATE TABLE index_tweets_keywords (" +
                        "  fileId int(11) NOT NULL, " +
                        "  line_number int(11) NOT NULL, " +
                        "  position int(11) NOT NULL," +
                        "  keyword varchar(500) DEFAULT NULL, " +
                        "  PRIMARY KEY (fileId,line_number,position) " +
                        ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";
        try {
            preparedStatement = mysqlConnect.connect().createStatement();

            // execute insert SQL stetement
            preparedStatement.executeUpdate(createTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
            // System.out.println(e.getMessage());
            // System.out.printf("Failed at " + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();
        }


    }
    public static void createFileNameTable() throws SQLException {
        java.sql.Statement preparedStatement = null;

        String createTable =
                "CREATE TABLE `filename` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `filename` varchar(500) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`)\n" +
                        ") ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;";
        try {
            preparedStatement = mysqlConnect.connect().createStatement();

            // execute insert SQL stetement
            preparedStatement.executeUpdate(createTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
            // System.out.println(e.getMessage());
            // System.out.printf("Failed at " + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();
        }
    }


    public static void setupDatabase() throws SQLException {
        dropArticleIndexesTable();
        dropFileNameTable();
        dropTweetIndexesTable();
        createArticleIndexesTable();
        createTweetIndexesTable();
        createFileNameTable();
    }
    public static void dropFileNameTable() throws SQLException {
        java.sql.Statement preparedStatement = null;

        String dropTable =
               "DROP TABLE IF EXISTS `filename`";
        try {
            preparedStatement = mysqlConnect.connect().createStatement();

            // execute insert SQL stetement
            preparedStatement.executeUpdate(dropTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
            // System.out.println(e.getMessage());
            // System.out.printf("Failed at " + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();
        }
    }
    public static void dropArticleIndexesTable() throws SQLException {
        java.sql.Statement preparedStatement = null;

        String dropTable =
                "DROP TABLE IF EXISTS `index_articles_keywords`";
        try {
            preparedStatement = mysqlConnect.connect().createStatement();

            // execute insert SQL stetement
            preparedStatement.executeUpdate(dropTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
            // System.out.println(e.getMessage());
            // System.out.printf("Failed at " + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();
        }
    }
    public static void dropTweetIndexesTable() throws SQLException {
        java.sql.Statement preparedStatement = null;

        String dropTable =
                "DROP TABLE IF EXISTS `index_tweets_keywords`";
        try {
            preparedStatement = mysqlConnect.connect().createStatement();

            // execute insert SQL stetement
            preparedStatement.executeUpdate(dropTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
            // System.out.println(e.getMessage());
            // System.out.printf("Failed at " + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();
        }
    }

    public static void truncateArticleIndexTable() throws SQLException {
        PreparedStatement preparedStatement = null;

        String truncate2 = "truncate indexing.index_articles_keywords";
        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(truncate2);
            preparedStatement.executeQuery();

        } catch (Exception e) {

            throw new RuntimeException(e);
            // System.out.println(e.getMessage());
            // System.out.printf("Failed at " + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            mysqlConnect.disconnect();
        }


    }
    public static void truncateTweetIndexTable() throws SQLException {
        PreparedStatement preparedStatement = null;

        String truncate2 = "truncate indexing.index_articles_keywords";
        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(truncate2);
            preparedStatement.executeQuery();

        } catch (Exception e) {

            throw new RuntimeException(e);
            // System.out.println(e.getMessage());
            // System.out.printf("Failed at " + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            mysqlConnect.disconnect();
        }


    }
    public static void truncateFilenameTable() throws SQLException {
        PreparedStatement preparedStatement = null;

        String truncate1 = "truncate indexing.index_tweets_keywords";
        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(truncate1);

            // execute insert SQL stetement
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
            // System.out.println(e.getMessage());
            // System.out.printf("Failed at " + fileName);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            mysqlConnect.disconnect();
        }


    }
    public static void truncateAll() throws SQLException {
        truncateArticleIndexTable();
        truncateTweetIndexTable();
        truncateFilenameTable();
    }
    }

package indexing;

import com.mysql.jdbc.Statement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by OttkO on 04-Jan-17.
 */
//Class responsible for database calls
public class DbHandler {

    public static int findFilenameId(String filepath) {
        return SQL.find("filename", "filepath", filepath);
    }

    //Select keywords from articles table based on the parameters
    public static KeywordStructure findKeywordInfoArticleIndexes(String filename, String keyword, int position, int linenumber) {
        final String table = "index_articles_keywords";
        return SQL.getKeywordStructure(table, keyword, filename, linenumber, position);
    }
    //Select keywords from tweets table based on the parameters
    public static KeywordStructure findKeywordInfoTweetIndexes(String filename, String keyword, int position, int linenumber) throws SQLException {
        final String table = "index_tweets_keywords";
        return SQL.getKeywordStructure(table, keyword, filename, linenumber, position);
    }
    //Select keyword position indexes based on a keyword from articles
    public static ArrayList<KeywordStructure> getKeywordPositionsArticleIndexes(String keyword) throws SQLException {
        final String table = "index_articles_keywords";
        return SQL.getKeywordStructures(table, keyword);
    }
    //Select keyword position indexes based on a keyword from tweets
    public static ArrayList<KeywordStructure> getKeywordPositionsTweetIndexes(String keyword) {
        final String table = "index_tweets_keywords";
        return SQL.getKeywordStructures(table, keyword);
    }
    //Insert a filename into the filename table and retrieve the id
    public static int insertFileName(String fileName) {
        return SQL.insertFilename(fileName);
    }

    //Insert record into article table
    public static int insertRecordIntoArticleTable(int fileId, int lineNumber, int position, String keyword) throws SQLException {
        final String table = "index_articles_keywords";
        return SQL.insertRecord(table, fileId, lineNumber, position, keyword);
    }
    //Insert record into tweet table
    public static int insertRecordIntoTweetTable(int fileId, int lineNumber, int position, String keyword) throws SQLException {
        final String table = "index_tweets_keywords";
        return SQL.insertRecord(table, fileId, lineNumber, position, keyword);
    }

    public static List<Integer> insertRecordsIntoTweetTable(int fileId, ArrayList<KeywordStructure> keywordStructures) throws SQLException {
        final String table = "index_tweets_keywords";
        return SQL.insertManyRecords(table, fileId, keywordStructures);
    }

    public static List<Integer> insertRecordsIntoTweetIdTable(int fileId, ArrayList<KeywordStructure> keywordStructures) throws SQLException {
        final String table = "index_tweet_ids";
        return SQL.insertManyRecords(table, fileId, keywordStructures);
    }

    public static void createArticleIndexesTable() throws SQLException {
        final String createTable =
                "  CREATE TABLE IF NOT EXISTS `" + Config.DATABASE_NAME + "`.`index_articles_keywords` (" +
                        "  fileId int(11) NOT NULL, " +
                        "  line_number int(11) NOT NULL, " +
                        "  position int(11) NOT NULL," +
                        "  keyword varchar(500) DEFAULT NULL, " +
                        "  PRIMARY KEY (fileId,line_number,position) " +
                        ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";
        SQL.single(createTable);
    }
    //Create tweet index table
    public static void createTweetIndexesTable() throws SQLException {
        final String createTable =
                "  CREATE TABLE IF NOT EXISTS `" + Config.DATABASE_NAME + "`.`index_tweets_keywords` (" +
                        "  fileId int(11) NOT NULL, " +
                        "  line_number int(11) NOT NULL, " +
                        "  position int(11) NOT NULL," +
                        "  keyword varchar(500) DEFAULT NULL, " +
                        "  PRIMARY KEY (fileId,line_number,position) " +
                        ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";
        SQL.single(createTable);
        final String createTableId =
                "  CREATE TABLE IF NOT EXISTS `" + Config.DATABASE_NAME + "`.`index_tweet_ids` (" +
                        "  fileId int(11) NOT NULL, " +
                        "  line_number int(11) NOT NULL, " +
                        "  position int(11) NOT NULL," +
                        "  keyword varchar(500) DEFAULT NULL, " +
                        "  PRIMARY KEY (fileId,line_number,position) " +
                        ") ENGINE=MyISAM DEFAULT CHARSET=latin1;";
        SQL.single(createTableId);
    }
    //Create filename table
    public static void createFileNameTable() throws SQLException {
        final String createTable =
                "CREATE TABLE IF NOT EXISTS `" + Config.DATABASE_NAME + "`.`filename` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `filename` varchar(500) NOT NULL,\n" +
                        "  PRIMARY KEY (`id`)\n" +
                        ") ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;";
        SQL.single(createTable);
    }

    ///Setup the database - drop and create tables from scratch.
    public static void setupDatabase() throws SQLException {
        disconnect(); // Close all presisting connections
        dropArticleIndexesTable();
        dropFileNameTable();
        dropTweetIndexesTable();
        createArticleIndexesTable();
        createTweetIndexesTable();
        createFileNameTable();
    }

    //Drop filename table
    public static void dropFileNameTable() throws SQLException {
        final String tableName = "filename";
        dropTable(tableName);
    }

    //Drops a table
    private static void dropTable(String tableName) {
        final String QUERY = "DROP TABLE IF EXISTS `" + Config.DATABASE_NAME + "`.`" + tableName + "`";
        SQL.single(QUERY);
    }

    //Truncates a table
    private static void truncateTable(String tableName) {
        final String QUERY = "TRUNCATE `" + Config.DATABASE_NAME + "`.`" + tableName + "`";
        SQL.single(QUERY);
    }

    //Drop article index table
    public static void dropArticleIndexesTable() throws SQLException {
        dropTable("index_articles_keywords");
    }

    //Drop tweet index table
    public static void dropTweetIndexesTable() throws SQLException {
        dropTable("index_tweets_keywords");
    }

    //Truncate Article index table
    public static void truncateArticleIndexTable() throws SQLException {
        truncateTable("index_articles_keywords");
    }

    //Truncate Tweet table index
    public static void truncateTweetIndexTable() throws SQLException {
        // Was: truncateTable("index_articles_keywords");
        truncateTable("index_tweets_keywords");
    }

    //Truncate filename table
    public static void truncateFilenameTable() throws SQLException {
        // Was: truncateTable("index_tweets_keywords");
        truncateTable("filename");
    }

    //Truncate all tables
    public static void truncateAll() throws SQLException {
        truncateArticleIndexTable();
        truncateTweetIndexTable();
        truncateFilenameTable();
    }

    public static ArrayList<KeywordStructure> getKeywordPositionsTweetIds(String keyword) {
        final String table = "index_tweet_ids";
        return SQL.getKeywordStructures(table, keyword);
    }

    /**
     * All SQL execution shizzle.
     * Keep this private to keep it organized
     */
    private static class SQL {

        private static int single(String SQL) {
            int result = -1;
            java.sql.Statement preparedStatement = null;
            try {
                preparedStatement = connect().createStatement();
                result = preparedStatement.executeUpdate(SQL);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                disconnect();
            }
            return result;
        }

        private static int insertRecord(String table, int fileId, int lineNumber, int position, String keyword) {
            int id = -1;
            PreparedStatement preparedStatement = null;
            String insertTableSQL = "INSERT INTO `" + Config.DATABASE_NAME + "`.`" + table + "` "
                    + "(fileId, line_number, position, keyword) VALUES"
                    + "(?,?,?,?)";
            try {
                preparedStatement = connect().prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, fileId);
                preparedStatement.setInt(2, lineNumber);
                preparedStatement.setInt(3, position);
                preparedStatement.setString(4, keyword);
                preparedStatement.executeUpdate();
                // execute insert SQL stetement
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()){
                    fileId=rs.getInt(1);
                }
            } catch (SQLException e) {
                throw new Error(e);
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("table = [" + table + "], fileId = [" + fileId + "], lineNumber = [" + lineNumber + "], position = [" + position + "], keyword = [" + keyword + "]");
                    }
                }
                disconnect();
            }
            return id;
        }

        private static List<Integer> insertManyRecords(String table, int fileId, ArrayList<KeywordStructure> keywordStructures) {

            List<Integer> lineNumbers = keywordStructures.stream().map(kws -> kws.lineNumber).collect(Collectors.toList());
            List<Integer> positions = keywordStructures.stream().map(kws -> kws.position).collect(Collectors.toList());
            List<String> keywords = keywordStructures.stream().map(kws -> kws.keyword).collect(Collectors.toList());

            int index0 = 0;
            int index1 = 10000;
            ArrayList<Integer> result = new ArrayList<>();
            while (index1 < lineNumbers.size() - 1){
                result.addAll(insertRecords(table, fileId, lineNumbers.subList(index0, index1), positions.subList(index0, index1), keywords.subList(index0, index1)));
                index0 = index1 + 1;
                index1 += 10000;
            }
            index1 = lineNumbers.size() - 1;
            result.addAll(insertRecords(table, fileId, lineNumbers.subList(index0, index1), positions.subList(index0, index1), keywords.subList(index0, index1)));
            return result;
        }

        private static List<Integer> insertRecords(String table, int fileId, List<Integer> lineNumbers, List<Integer> positions, List<String> keywords) {
            if (lineNumbers.size() != positions.size() || keywords.size() != positions.size())
                throw new IllegalArgumentException();
            if (lineNumbers.size() > 25000) {
                throw new IllegalArgumentException("To big for 1 query");
            }
            List<Integer> results = new ArrayList<>();
            PreparedStatement preparedStatement = null;
            StringBuilder insertTableSQL = new StringBuilder("INSERT IGNORE INTO `" + Config.DATABASE_NAME + "`.`" + table + "` "
                    + "(fileId, line_number, position, keyword) VALUES");
            insertTableSQL.ensureCapacity(10 * lineNumbers.size() + insertTableSQL.length());
            for (int i = 0; i < lineNumbers.size(); i++) {
                insertTableSQL.append("(?,?,?,?)");
                if (i < lineNumbers.size() - 1) {
                    insertTableSQL.append(',');
                } else {
                    insertTableSQL.append(';');
                }
            }
            try {
                preparedStatement = connect().prepareStatement(insertTableSQL.toString(), Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < lineNumbers.size(); i++) {
                    preparedStatement.setInt(i * 4 + 1, fileId);
                    preparedStatement.setInt(i * 4 + 2, lineNumbers.get(i));
                    preparedStatement.setInt(i * 4 + 3, positions.get(i));
                    preparedStatement.setString(i * 4 + 4, keywords.get(i));
                }

                preparedStatement.executeUpdate();
                // execute insert SQL stetement
                ResultSet rs = preparedStatement.getGeneratedKeys();
                while (rs.next()) {
                    results.add(rs.getInt(1));
                }
                if (rs.next()) {
                    System.err.println("Not expected output: " + rs);
                }
            } catch (SQLException e) {
                throw new Error(e);
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("table = [" + table + "], fileId = [" + fileId + "], lineNumbers = [" + lineNumbers + "], positions = [" + positions + "], keywords = [" + keywords + "]");
                    }
                }
                disconnect();
            }
            return results;
        }

        private static int insertFilename(String fileName) {
            int fileId = -1;
            PreparedStatement preparedStatement = null;

            final String table = "filename";
            String insertTableSQL = "INSERT INTO `" + Config.DATABASE_NAME + "`.`" + table + "` "
                    + "(filename) VALUES"
                    + "(?)";
            try {
                preparedStatement = connect().prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, fileName);

                // execute insert SQL stetement
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    fileId = rs.getInt(1);
                    if (rs.next()) {
                        // Many added
                    }
                } else {
                    // None added
                }
            } catch (SQLException e) {
                throw new Error(e);
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                disconnect();
            }
            return fileId;
        }

        private static ArrayList<KeywordStructure> getKeywordStructures(String table, String keyword) {
            ArrayList<KeywordStructure> result = new ArrayList<>();
            PreparedStatement preparedStatement = null;
            String insertTableSQL = "" +
                    "select i.position, i.line_number, f.filename " +
                    "from `" + Config.DATABASE_NAME + "`.`" + table + "` as i, `" + Config.DATABASE_NAME + "`.`filename` as f " +
                    "where i.fileId = f.id and i.keyword = ?";
            try {
                preparedStatement = connect().prepareStatement(insertTableSQL);
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
                throw new Error(e);
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                disconnect();
            }
            return result;
        }

        private static KeywordStructure getKeywordStructure(String table, String keyword, String filename, int linenumber, int position) {
            KeywordStructure result = null;
            PreparedStatement preparedStatement = null;
            String insertTableSQL =
                    "select i.line_number, f.filename,i.position,i.keyword " +
                            "from `" + Config.DATABASE_NAME + "`.`" + table + "` as i, `" + Config.DATABASE_NAME + "`.`filename` as f " +
                            "where i.fileId=f.id " +
                            "and f.filename=? " +
                            "and i.line_number=? " +
                            "and i.keyword=? " +
                            "and i.position=?";
            try {
                preparedStatement = connect().prepareStatement(insertTableSQL);

                preparedStatement.setString(1, filename);
                preparedStatement.setInt(2, linenumber);
                preparedStatement.setString(3, keyword);
                preparedStatement.setInt(4, position);

                // execute insert SQL stetement
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    result = new KeywordStructure();
                    result.position = rs.getInt("position");
                    result.lineNumber = rs.getInt("line_number");
                    result.fileName = rs.getString("filename");
                    result.keyword = rs.getString("keyword");

                    if (rs.next()) {
                        // Many found
                        throw new Error("Multiple findKeywordInfoArticleIndexes found");
                    }
                }

            } catch (SQLException e) {
                throw new Error(e);
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                disconnect();
            }
            return result;
        }

        private static int find(String tablename, String column, String value) {
            PreparedStatement preparedStatement = null;
            SQLException exception = null;
            int id = -1;
            try {
                String insertTableSQL = "SELECT `" + column + "` FROM `" + Config.DATABASE_NAME + "`.`" + tablename + "` WHERE `" + column + "` = ?";
                preparedStatement = connect().prepareStatement(insertTableSQL);
                preparedStatement.setString(1, value);
                // execute insert SQL stetement
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("column");
                    if (rs.next()) {
                        // Many found
                        throw new Error("Multiple instances with " + column + "=" + value + " in table " + tablename + " found");
                    } else {
                        // One found
                    }
                } else {
                    // None found
                }
            } catch (SQLException e) {
                throw new Error(e);
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                disconnect();
            }
            return id;
        }
    }

    // init connection object
    private static Connection connection;
    // init properties object
    private static Properties properties;

    // create properties
    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", Config.USERNAME);
            properties.setProperty("password", Config.PASSWORD);
            properties.setProperty("MaxPooledStatements", Config.MAX_POOL);
        }
        return properties;
    }

    // connect database
    private static Connection connect() {
        if (connection == null) {
            try {
                Class.forName(Config.DATABASE_DRIVER);
                connection = DriverManager.getConnection(Config.DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // disconnect database
    private static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

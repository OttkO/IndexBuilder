package indexing;

import com.mysql.jdbc.Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OttkO on 04-Jan-17.
 */
public class DbHandler {
    static MysqlConnect mysqlConnect = new MysqlConnect();

    public DbHandler() {

    }

    public static int insertRecordIntoTable(int fileId, int lineNumber, int position, String keyword) throws SQLException {
        int id = -1;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO FILE_INFORMATION"
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

    public static int FindFilenameId(String fileName) throws SQLException {
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

    public static KeywordStructure FindKeywordInfo(String filename, String keyword, int position, int linenumber) throws SQLException {
        KeywordStructure result = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL =
                "select i.line_number, f.filename,i.position,i.keyword " +
                        "from file_information as i, filename as f " +
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

    public static ArrayList<KeywordStructure> GetKeywordPositions(String keyword) throws SQLException {
        ArrayList<KeywordStructure> result = new ArrayList<KeywordStructure>();
        int id = -1;
        PreparedStatement preparedStatement = null;
        String insertTableSQL = "select indexing.file_information.position,indexing.file_information.line_number,indexing.filename.filename,indexing.file_information.keyword\n" +
                "from indexing.file_information, indexing.filename\n" +
                "where indexing.file_information.fileId = indexing.filename.id and indexing.file_information.keyword = ?";


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

    public static int InsertFileName(String fileName) throws SQLException {
        int fileId = -1;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO FILENAME"
                + "(filename) VALUES"
                + "(?)";

        try {
            preparedStatement = mysqlConnect.connect().prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, fileName);

            // execute insert SQL stetement
            fileId = preparedStatement.executeUpdate();


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

    public static void TruncateFileInfoTable() throws SQLException {
        PreparedStatement preparedStatement = null;

        String truncate2 = "truncate indexing.file_information";
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

    public static void TruncateFilenameTable() throws SQLException {
        PreparedStatement preparedStatement = null;

        String truncate1 = "truncate indexing.filename";
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

    public static void TruncateAll() throws SQLException {
        TruncateFileInfoTable();
        TruncateFilenameTable();
    }
}

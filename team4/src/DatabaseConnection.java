import java.sql.*;

public class DatabaseConnection {
    protected static final String url = "jdbc:postgresql://localhost:5432/ascii4";
    protected static final String user = "postgres";
    protected static final String password = "Student_1234";

    public static void main() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection available!");
            Statement statement = connection.createStatement();
//            statement.executeUpdate("DROP TABLE IF EXISTS board_state;" +
//                                    "DROP TABLE IF EXISTS game_info;" +
//                                    "DROP TABLE IF EXISTS players;");
//            System.out.println("Table deleted!");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS players(" +
                    "    player_id INTEGER GENERATED BY DEFAULT AS IDENTITY" +
                    "        (START WITH 1 INCREMENT BY 1)" +
                    "            CONSTRAINT pk_players PRIMARY KEY," +
                    "    player_name VARCHAR(25)" +
                    "        CONSTRAINT ch_players_player_name" +
                    "            CHECK ( LENGTH(player_name) BETWEEN 2 AND 25 ) NOT NULL," +
                    "    player_country VARCHAR(30)" +
                    ");");
            System.out.println("Table players created!");
//            statement.executeUpdate("INSERT INTO players (player_name, player_country) VALUES ('Ade','Belgium')");
//            statement.executeUpdate("INSERT INTO players (player_name, player_country) VALUES ('Artem','Belgium')");
//            statement.executeUpdate("INSERT INTO players (player_name, player_country) VALUES ('Anna','Luxembourg')");
//            statement.executeUpdate("INSERT INTO players (player_name, player_country) VALUES ('Michael','Germany')");
//            System.out.println("Data entered in table players");
            ResultSet resultSet = statement.executeQuery("SELECT player_name, player_country FROM players");
            System.out.println("Following data retrieved from database:");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS game_info(" +
                    "    game_id INTEGER" +
                    "        GENERATED BY DEFAULT AS IDENTITY" +
                    "            (START WITH 1 INCREMENT BY 1)" +
                    "                CONSTRAINT pk_game_info PRIMARY KEY," +
                    "    player_id INTEGER" +
                    "        CONSTRAINT fk_game_info_player_id" +
                    "            REFERENCES players(player_id)" +
                    "                ON DELETE CASCADE," +
                    "    start_time TIMESTAMP(0) NOT NULL," +
                    "    end_time TIMESTAMP(0)," +
                    "    game_outcome VARCHAR(30)," +
                    "    score INTEGER," +
                    "    session_duration INTEGER" +
                    ");");
            System.out.println("Table game_info created!");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS board_state(" +
                    "    cell_id INTEGER" +
                    "        GENERATED BY DEFAULT AS IDENTITY" +
                    "            (START WITH 1 INCREMENT BY 1)" +
                    "                CONSTRAINT pk_board_state PRIMARY KEY," +
                    "    game_id INTEGER" +
                    "        CONSTRAINT fk_board_state_game_id" +
                    "            REFERENCES game_info(game_id)" +
                    "                ON DELETE CASCADE," +
                    "    cell_row SMALLINT NOT NULL," +
                    "    cell_column SMALLINT NOT NULL," +
                    "    value VARCHAR(10) NOT NULL," +
                    "    static BIT NOT NULL" +
                    ");");
            System.out.println("Table board_state created!");
            statement.close(); connection.close();

            System.out.println("Connection closed!");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}

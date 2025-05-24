package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Scores_Chapter10 {
    public static void main(String[] args) {

        Connection con = null;

        try {
            // データベース接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java?useSSL=false&serverTimezone=UTC",
                "root",
                "Mm-0921052"
            );
            System.out.println("データベース接続成功：" + con);

            // 主キー（id）による点数更新のSQL
            String updateSql = "UPDATE scores SET score_math = ?, score_english = ? WHERE id = ?";

            System.out.println("レコード更新を実行します");
            try (PreparedStatement updatePstmt = con.prepareStatement(updateSql)) {
                updatePstmt.setInt(1, 95);   // 数学
                updatePstmt.setInt(2, 80);   // 英語
                updatePstmt.setInt(3, 5); 

                int updatedRows = updatePstmt.executeUpdate();
                System.out.println(updatedRows + "件のレコードが更新されました");
            }

            // 並べ替え取得のSQL
            String selectSql = "SELECT * FROM scores ORDER BY score_math DESC, score_english DESC";

            System.out.println("数学・英語の点数が高い順に並べ替えました");
            try (PreparedStatement selectPstmt = con.prepareStatement(selectSql);
                 ResultSet rs = selectPstmt.executeQuery()) {

                int count = 1;
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int math = rs.getInt("score_math");
                    int english = rs.getInt("score_english");

                    System.out.printf("%d件目：生徒ID=%d／氏名=%s／数学=%d／英語=%d%n",
                                      count, id, name, math, english);
                    count++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}

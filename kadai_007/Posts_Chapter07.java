package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
    public static void main(String[] args) {

        Connection con = null;
        Statement stmt = null;
        ResultSet result = null;

        try {
            // データベース接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java?useSSL=false&serverTimezone=UTC",
                "root",
                "Mm-0921052"
            );
            System.out.println("データベース接続成功" + con);

            // ステートメント作成
            stmt = con.createStatement();

            // データ追加
            System.out.println("レコード追加を実行します");
            String insertSql = """
                INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES
                (1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13),
                (1002, '2023-02-08', 'お疲れ様です！', 12),
                (1003, '2023-02-09', '今日も頑張ります！', 18),
                (1001, '2023-02-09', '無理は禁物ですよ！', 17),
                (1002, '2023-02-10', '明日から連休ですね！', 20);
            """;

            int inserted = stmt.executeUpdate(insertSql);
            System.out.println(inserted + " 件のレコードが追加されました");

            // データ検索
            System.out.println("ユーザーIDが1002のレコードを検索しました");
            String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002";
            result = stmt.executeQuery(selectSql);

            int count = 1;
            while (result.next()) {
                Date postedAt = result.getDate("posted_at");
                String content = result.getString("post_content");
                int likes = result.getInt("likes");

                System.out.println(count + "件目：投稿日時=" + postedAt +
                                   "／投稿内容=" + content +
                                   "／いいね数=" + likes);
                count++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


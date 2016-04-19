/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;

/**
 *
 * @author c0662366
 */
@ApplicationScoped 
public class MessageController {
    private List<Message> msgs = new ArrayList<>();
    
    public MessageController() {
        getMessagesFromDatabase();
    }

    
    public Message getById(int id) {
        for (Message m : msgs) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }
    
    public List<Message> getByDate(Date from, Date to) {
        List<Message> result = new ArrayList<>();
       for (Message m : msgs) {
            if (m.getSentTime().after(from) && m.getSentTime().before(to)) {
                result.add(m);
            }
        }
        return result;
    }
    
    private void getMessagesFromDatabase() {
        try {
           // messageList = new ArrayList<>();
            Connection conn = Utils.getConnection();
            String sql = "SELECT * FROM messages";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Message m = new Message();
                m.setId(rs.getInt("id"));
                m.setTitle(rs.getString("title"));
                m.setContents(rs.getString("contents"));
                m.setAuthor(rs.getString("author"));
                m.setSentTime(rs.getTimestamp("sentTime"));
                msgs.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Message> getAll() {
       // List<Message> result = new ArrayList<>();
//        for (Message m : msgs) {
//            result.add(m);
//        }
        return msgs;
    }
    public void add(String title, String contents, String author, Date sentTime) {
        try {
            Connection conn = Utils.getConnection();
            String sql = "INSERT INTO messages (title, contents, author, sentTime) VALUES (?, ?, ?, NOW())";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, contents);
            pstmt.setString(3, author);
           //  pstmt.setString(4, sentTime);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        getMessagesFromDatabase();
    }

    public void edit(int id, String title, String contents) {
        try {
            Connection conn = Utils.getConnection();
            String sql = "UPDATE posts SET title = ?, contents = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, contents);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        getMessagesFromDatabase();
    }
    
    public void remove(int id) {
        try {
            Connection conn = Utils.getConnection();
            String sql = "DELETE FROM messages WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        getMessagesFromDatabase();
    }
}

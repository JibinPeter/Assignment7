/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author c0662366
 */
public class Message {
    private Integer id; 
    private String title; 
    private String contents; 
    private String author; 
    private Date sentTime; 
    private List<Message> todoList = new ArrayList<>();
    
//    public Message(Integer id, String title, String contents, String author, Date sentTime) {
//        this.id = id;
//        this.title = title;
//        this.contents = contents;
//        this.author = author;
//        this.sentTime = sentTime;
//    }

    public List<Message> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Message> todoList) {
        this.todoList = todoList;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }
    
    
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("title", title)
                .add("contents", contents)
                .add("author", author)
                .add("senttime", sentTime.toString())
                .build();
    }
    
    @Override
    public String toString() {
        return "{ \"id\" : " + id + ", \"title\" : \"" + title + "\", \"contents\" : \"" + contents +
                "\", \"author\" : \"" + author + "\", \"senttime\" : \"" + sentTime + "\" }";
    }
}

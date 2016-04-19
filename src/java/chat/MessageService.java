/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author c0662366
 */
@ApplicationScoped 
@Path("/messages")
public class MessageService {
    List<Message> lists = new ArrayList<Message>();
    private MessageController mc = new MessageController();
    
    @GET
    @Produces("application/json")    
    public JsonArray get() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (Message m : mc.getAll()) {
            json.add(m.toJSON());
        }
        return json.build();
    }
    
    
    @GET
    @Path("{id}")
    public JsonObject get(@PathParam("id") int id) {
        JsonObject json = mc.getById(id).toJSON();
        return json;
    }
    
    @GET
    @Path("{startDate}/{endDate}")
    @Produces("application/json")
    public JsonArray get(@PathParam("startDate") Date startDate, @PathParam("endDate") Date endDate) {
        JsonArrayBuilder json = Json.createArrayBuilder();
        for (Message msg : mc.getByDate(startDate, endDate)) {
            json.add(msg.toJSON());
        }
        return json.build();
    }
    
    @POST
    @Consumes("application/json")    
    @Produces("application/json")
    public JsonObject post(JsonObject json) {
        try {            
            mc.add(json.getString("title"), json.getString("contents"), json.getString("author"),
                   new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(json.getString("sentTime")));
        } catch (ParseException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    @PUT
    @Path("{id}")    
    @Produces("application/json")
    public JsonObject put(@PathParam("id") int id, JsonObject json) {
        mc.edit(id, json.getString("title"), json.getString("contents"));
        return json;
    } 
    
    @DELETE
    @Path("{id}")
    public String delete(@PathParam("id") int id) {
        mc.remove(id);
        return "200 OK";
    }
}

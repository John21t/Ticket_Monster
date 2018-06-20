/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restResources;

import intraholics.ticketmonster.Entities.User;
import intraholics.ticketmonster.Manager.UserDaoLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import intraholics.ticketmonster.Manager.CartDaoLocal;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Kostis Hatzistamatis
 * Class for mapping the Rest endpoints For the user resources.
 * 
 */
@Path("/users")
@Stateless
public class UserResource {
    
    
    @EJB
    private UserDaoLocal user;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllUsers(){
        List<User> users=user.findAllUsers();
        return Response.ok(users).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response findUserById(@PathParam("id") Integer id){
        User found=user.findUserById(id);
        return Response.ok(found).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{Username}&{Pass}")
    public Response logged_user(@PathParam("Username") String username,@PathParam("Pass") String pass ){
     User found=user.checkLoginCredentials(username, pass);
     if (found!=null) {
         JsonObject usertolog=Json.createObjectBuilder()
                 .add("userID",found.getUserID())
                 .add("username",found.getUsername())
                 .add("userRole", found.getUserRole()).build();
        return Response.ok(usertolog).build();
     }
     else{
         return Response.ok(null).build();
     }
    }
    
        
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user1){ 
        user.addUser(user1);
        return Response.ok(user1).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user1){
        user.updateUser(user1);
        return Response.ok(user1).build();
    }

    @DELETE
    @Path ("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Integer id){
        user.deleteUserById(id);
        return Response.ok().build();
    }
    
    
}
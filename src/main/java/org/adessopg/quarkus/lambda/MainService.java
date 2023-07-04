package org.adessopg.quarkus.lambda;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/native/lambda")
public class MainService {
    //private  static final MinIO minIO=new MinIO();
    @Inject
    MinIO minIO;
    @Inject
    UserRepository usersRepo;



    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String landing() {
        return "This is the landing page from lambda";
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> listUsers(){
        return usersRepo.listAll();
    }

    @POST
    @Path("/addUser")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String addUser(UserEntity user){
        try{
            usersRepo.persist(user);
            minIO.uploadObject(user.getUsername());
            return String.format("User %s created",user.getUsername());
        }catch (Exception err){
            return err.getMessage();
        }

    }
}

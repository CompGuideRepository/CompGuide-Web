/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.WebService;

import com.compguide.web.Persistence.Entities.GuideExec;
import com.compguide.web.Persistence.Entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
 * @author António
 */
@Stateless
@Path("com.compguide.web.persistence.entities.guideexec")
public class GuideExecFacadeREST extends AbstractFacade<GuideExec> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public GuideExecFacadeREST() {
        super(GuideExec.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(GuideExec entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, GuideExec entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public GuideExec find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    
    @GET
    @Path("/user/{id}")
    @Produces({"application/xml", "application/json"})
    public List<GuideExec> findByUser(@PathParam("id") Integer id) {
        User user = getEntityManager().find(User.class, id);
        List<GuideExec> guideExecs = new ArrayList<GuideExec>();
        
        Query query = em.createNamedQuery("GuideExec.findByUserCompletedGuidelines", GuideExec.class);
        
        query.setParameter("iduser", user);
        query.setParameter("completed", false);
        guideExecs = query.getResultList();
            
        return guideExecs;
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<GuideExec> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GuideExec> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

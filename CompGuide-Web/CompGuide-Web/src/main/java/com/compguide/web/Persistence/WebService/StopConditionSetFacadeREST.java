/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.WebService;

import com.compguide.web.Persistence.Entities.StopConditionSet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Path("com.compguide.web.persistence.entities.stopconditionset")
public class StopConditionSetFacadeREST extends AbstractFacade<StopConditionSet> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public StopConditionSetFacadeREST() {
        super(StopConditionSet.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(StopConditionSet entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, StopConditionSet entity) {
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
    public StopConditionSet find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<StopConditionSet> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<StopConditionSet> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("canask")
    @Produces("text/plain")
    public Boolean canAskOutcome() {
        List<StopConditionSet> stopConditionSets = super.findAll();

        for (StopConditionSet stopConditionSet : stopConditionSets) {
            if (stopConditionSet.getAsked() == false && stopConditionSet.getCanAsk() == true) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

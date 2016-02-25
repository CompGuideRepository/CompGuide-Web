/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.WebService;

import com.compguide.web.Persistence.Entities.Notification;
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
@Path("com.compguide.web.persistence.entities.notification")
public class NotificationFacadeREST extends AbstractFacade<Notification> {

    @PersistenceContext(unitName = "com.compguide_CompGuide-Web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public NotificationFacadeREST() {
        super(Notification.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Notification entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Notification entity) {
        super.edit(entity);
    }

    @PUT
    @Path("list/{viewed}")
    @Consumes({"application/xml", "application/json"})
    public void editViewedByID(@PathParam("viewed") boolean viewed) {
        List<Notification> notifications = super.findAll();

        for (Notification notification : notifications) {
            notification.setViewed(viewed);
            super.edit(notification);
        }

    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Notification find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Notification> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Notification> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("count/{viewed}")
    @Produces("text/plain")
    public String countViewed(@PathParam("viewed") boolean viewed) {
        List<Notification> notifications = super.findAll();
        Integer count = 0;

        for (Notification notification : notifications) {
            if (notification.getViewed() == viewed) {
                count++;
            }
        }
        return count.toString();
    }

    @GET
    @Path("countnonchecked/{viewed}")
    @Produces("text/plain")
    public String countNonChecked(@PathParam("viewed") boolean viewed) {
        List<Notification> notifications = super.findAll();
        Integer count = 0;

        for (Notification notification : notifications) {
            if (notification.getEventID() != null) {
                if (notification.getEventID().getChecked() == viewed) {
                    count++;
                }
            }
        }
        return count.toString();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}

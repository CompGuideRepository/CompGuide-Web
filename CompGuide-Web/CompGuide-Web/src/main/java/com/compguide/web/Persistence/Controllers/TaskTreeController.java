/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Persistence.Controllers;

import com.compguide.web.Persistence.Entities.GuideExec;
import com.compguide.web.Persistence.Entities.ScheduleTask;
import com.compguide.web.Persistence.SessionBeans.ScheduleTaskFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author António
 */
@Named("taskTreeController")
@SessionScoped
public class TaskTreeController implements Serializable {

    @EJB
    private com.compguide.web.Persistence.SessionBeans.ScheduleTaskFacade ejbFacade;

    private TreeNode root;

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode();
    }

    public TreeNode getRoot() {
        GuideExec guideExec = (GuideExec) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("guideexec");

        if (guideExec != null) {
            List<ScheduleTask> scheduleTasks = getFacade().findByGuideExecID(guideExec);
            Map<String, TreeNode> nodes = new HashMap<String, TreeNode>();

            for (ScheduleTask scheduleTask : scheduleTasks) {
                if (scheduleTask.getParentTaskID() == null) {
                    TreeNode node = nodes.get(scheduleTask.getTaskIdentifier());

                    if (node == null) {
                        root = new DefaultTreeNode(scheduleTask.getTaskIdentifier(), null);
                        nodes.put(scheduleTask.getTaskIdentifier(), root);
                    } else {
                        root.getChildren().add(node);
                    }

                } else {
                    TreeNode parentNode = nodes.get(scheduleTask.getParentTaskID().getTaskIdentifier());

                    if (parentNode == null) {
                        TreeNode parent = new DefaultTreeNode(scheduleTask.getParentTaskID().getTaskIdentifier(), root);

                        TreeNode childrenNode = new DefaultTreeNode(scheduleTask.getTaskIdentifier(), parent);
                        parent.getChildren().add(childrenNode);

                        nodes.put(scheduleTask.getParentTaskID().getTaskIdentifier(), parent);

                        root.getChildren().add(parent);

                        if (!nodes.containsKey(scheduleTask.getTaskIdentifier())) {
                            nodes.put(scheduleTask.getTaskIdentifier(), childrenNode);
                        }
                    } else {

                        TreeNode childrenNode = new DefaultTreeNode(scheduleTask.getTaskIdentifier(), parentNode);
                        parentNode.getChildren().add(childrenNode);

                        if (!nodes.containsKey(scheduleTask.getTaskIdentifier())) {
                            nodes.put(scheduleTask.getTaskIdentifier(), childrenNode);
                        }
                    }
                }
            }
        }

        return root;
    }

    private ScheduleTaskFacade getFacade() {
        return ejbFacade;
    }

}

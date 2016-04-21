package com.silbury.view.beans;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelDashboard;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.change.AttributeComponentChange;
import org.apache.myfaces.trinidad.change.ChangeManager;
import org.apache.myfaces.trinidad.event.AttributeChangeEvent;

public class ShowNHide {

    private final static String PROPERTY_RENDERED="rendered";
    private RichPanelDashboard bindPanelDashboard;
    private RichPanelBox bindMyNotificationsBox;
    private RichPanelBox bindAverageCompensationBox;
    private RichPanelBox bindAttritionHistoryBox;
    private RichPanelBox bindAboutMeBox;
    private boolean renderMyNotifications;
    private boolean renderAverageCompensation;
    private boolean renderAttritionHistory;
    private boolean renderAboutMe;
    private RichPanelBox bindAssignedTasksBox;
    private boolean renderAssignedTasks;
    private RichPanelBox bindCompleteTasksBox;
    private boolean renderCompleteTasks;
    private RichPanelBox bindDepartmentEmployees;
    private RichPanelBox bindDeptLocations;
    private boolean renderDepartmentEmployees;
    private boolean renderDepartmentLocations;

    public ShowNHide() {
        super();
    }
    //Binding paneldashboard and all the panelboxes
    public void setBindPanelDashboard(RichPanelDashboard bindPanelDashboard) {
        this.bindPanelDashboard = bindPanelDashboard;
    }

    public RichPanelDashboard getBindPanelDashboard() {
        return bindPanelDashboard;
    }
    
    public void setBindMyNotificationsBox(RichPanelBox bindMyNotificationsBox) {
        this.bindMyNotificationsBox = bindMyNotificationsBox;
    }

    public RichPanelBox getBindMyNotificationsBox() {
        return bindMyNotificationsBox;
    }

    public void setBindAverageCompensationBox(RichPanelBox bindAverageCompensationBox) {
        this.bindAverageCompensationBox = bindAverageCompensationBox;
    }

    public RichPanelBox getBindAverageCompensationBox() {
        return bindAverageCompensationBox;
    }

    public void setBindAttritionHistoryBox(RichPanelBox bindAttritionHistoryBox) {
        this.bindAttritionHistoryBox = bindAttritionHistoryBox;
    }

    public RichPanelBox getBindAttritionHistoryBox() {
        return bindAttritionHistoryBox;
    }

    public void setBindAboutMeBox(RichPanelBox bindAboutMeBox) {
        this.bindAboutMeBox = bindAboutMeBox;
    }

    public RichPanelBox getBindAboutMeBox() {
        return bindAboutMeBox;
    }
    
    //Setting rendered property for all the panelboxes and store the state in MDS state
    //
    public void snhMyNotifications(ActionEvent actionEvent) {
        
        if(this.getBindMyNotificationsBox().isRendered()){
            this.getBindMyNotificationsBox().setRendered(false);
        }else{
            this.getBindMyNotificationsBox().setRendered(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindPanelDashboard());
        
        boolean renderFlag = (Boolean)this.getBindMyNotificationsBox().getAttributes().get(PROPERTY_RENDERED);
        this.getBindMyNotificationsBox().getAttributes().put(PROPERTY_RENDERED, renderFlag);
        this.persistAttributeChange(PROPERTY_RENDERED,this.getBindMyNotificationsBox(),renderFlag);
    }
    
    public void snhAverageCompensation(ActionEvent actionEvent) {
        if(this.getBindAverageCompensationBox().isRendered()){
            this.getBindAverageCompensationBox().setRendered(false);
        }else{
            this.getBindAverageCompensationBox().setRendered(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindPanelDashboard());
        
        boolean renderFlag = (Boolean)this.getBindAverageCompensationBox().getAttributes().get(PROPERTY_RENDERED);
        this.getBindAverageCompensationBox().getAttributes().put(PROPERTY_RENDERED, renderFlag);
        this.persistAttributeChange(PROPERTY_RENDERED,this.getBindAverageCompensationBox(),renderFlag);
    }

    public void snhAttritionHistory(ActionEvent actionEvent) {
        if(this.getBindAttritionHistoryBox().isRendered()){
            this.getBindAttritionHistoryBox().setRendered(false);
        }else{
            this.getBindAttritionHistoryBox().setRendered(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindPanelDashboard());
        
        boolean renderFlag = (Boolean)this.getBindAttritionHistoryBox().getAttributes().get(PROPERTY_RENDERED);
        this.getBindAttritionHistoryBox().getAttributes().put(PROPERTY_RENDERED, renderFlag);
        this.persistAttributeChange(PROPERTY_RENDERED,this.getBindAttritionHistoryBox(),renderFlag);
    }

    public void snhAboutMe(ActionEvent actionEvent) {
        if(this.getBindAboutMeBox().isRendered()){
            this.getBindAboutMeBox().setRendered(false);
        }else{
            this.getBindAboutMeBox().setRendered(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindPanelDashboard());
        
        boolean renderFlag = (Boolean)this.getBindAboutMeBox().getAttributes().get(PROPERTY_RENDERED);
        this.getBindAboutMeBox().getAttributes().put(PROPERTY_RENDERED, renderFlag);
        this.persistAttributeChange(PROPERTY_RENDERED,this.getBindAboutMeBox(),renderFlag);
    }
    
    private void persistAttributeChange(String attribute,UIComponent component,Object value){
        AttributeComponentChange attributeChange = null;attributeChange = new AttributeComponentChange(attribute, value);
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        AdfFacesContext adfFacesCtx = null;
        adfFacesCtx = AdfFacesContext.getCurrentInstance();
        //ChangeManager.
        ChangeManager manager = adfFacesCtx.getPersistentChangeManager(); 
        manager.addComponentChange(facesCtx, component, attributeChange);
    }

    //Methods to know the rendered property of panel boxes

    public void setRenderMyNotifications(boolean renderMyNotifications) {
        this.renderMyNotifications = renderMyNotifications;
    }

    public boolean getRenderMyNotifications() {
        if(this.getBindMyNotificationsBox().isRendered()){
            renderMyNotifications=true;
        }else{
            renderMyNotifications=false;
        }       
        return renderMyNotifications;
    }
    
    public void setRenderAverageCompensation(boolean renderAverageCompensation) {
        this.renderAverageCompensation = renderAverageCompensation;
    }

    public boolean getRenderAverageCompensation() {
        if(this.getBindAverageCompensationBox().isRendered()){
            renderAverageCompensation=true;
        }else{
            renderAverageCompensation=false;
        }
        return renderAverageCompensation;
    }

    public void setRenderAttritionHistory(boolean renderAttritionHistory) {
        this.renderAttritionHistory = renderAttritionHistory;
    }

    public boolean getRenderAttritionHistory() {
        try {
            if (this.getBindAttritionHistoryBox().isRendered()) {
                renderAttritionHistory = true;
            } else {
                renderAttritionHistory = false;
            }

        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return renderAttritionHistory;
    }

    public void setRenderAboutMe(boolean renderAboutMe) {
        this.renderAboutMe = renderAboutMe;
    }

    public boolean getRenderAboutMe() {
        if(this.getBindAboutMeBox().isRendered()){
            renderAboutMe=true;
        }else{
            renderAboutMe=false;
        }
        return renderAboutMe;
    }
    
    public void snhAssignedTasks(ActionEvent actionEvent) {
        if(this.getBindAssignedTasksBox().isRendered()){
            this.getBindAssignedTasksBox().setRendered(false);
        }else{
            this.getBindAssignedTasksBox().setRendered(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindPanelDashboard());
        
        boolean renderFlag = (Boolean)this.getBindAssignedTasksBox().getAttributes().get(PROPERTY_RENDERED);
        this.getBindAssignedTasksBox().getAttributes().put(PROPERTY_RENDERED, renderFlag);
        this.persistAttributeChange(PROPERTY_RENDERED,this.getBindAssignedTasksBox(),renderFlag);
    }

    public void setBindAssignedTasksBox(RichPanelBox bindAssignedTasksBox) {
        this.bindAssignedTasksBox = bindAssignedTasksBox;
    }

    public RichPanelBox getBindAssignedTasksBox() {
        return bindAssignedTasksBox;
    }

    public void setRenderAssignedTasks(boolean renderAssignedTasks) {
        this.renderAssignedTasks = renderAssignedTasks;
    }

    public boolean getRenderAssignedTasks() {
        if (this.getBindAssignedTasksBox().isRendered()) {
            renderAssignedTasks = true;
        } else {
            renderAssignedTasks = false;
        }
        return renderAssignedTasks;
    }

    public void snhCompleteTasks(ActionEvent actionEvent) {
        if(this.getBindCompleteTasksBox().isRendered()){
            this.getBindCompleteTasksBox().setRendered(false);
        }else{
            this.getBindCompleteTasksBox().setRendered(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindPanelDashboard());
        
        boolean renderFlag = (Boolean)this.getBindCompleteTasksBox().getAttributes().get(PROPERTY_RENDERED);
        this.getBindCompleteTasksBox().getAttributes().put(PROPERTY_RENDERED, renderFlag);
        this.persistAttributeChange(PROPERTY_RENDERED,this.getBindCompleteTasksBox(),renderFlag);
    }

    public void setBindCompleteTasksBox(RichPanelBox bindCompleteTasksBox) {
        this.bindCompleteTasksBox = bindCompleteTasksBox;
    }

    public RichPanelBox getBindCompleteTasksBox() {
        return bindCompleteTasksBox;
    }

    public void setRenderCompleteTasks(boolean renderCompleteTasks) {
        this.renderCompleteTasks = renderCompleteTasks;
    }

    public boolean getRenderCompleteTasks() {
        if (this.getBindCompleteTasksBox().isRendered()) {
            renderCompleteTasks = true;
        } else {
            renderCompleteTasks = false;
        }
        return renderCompleteTasks;
    }

    public void snhDepartmentEmployees(ActionEvent actionEvent) {
        if(this.getBindDepartmentEmployeesBox().isRendered()){
            this.getBindDepartmentEmployeesBox().setRendered(false);
        }else{
            this.getBindDepartmentEmployeesBox().setRendered(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindPanelDashboard());
        
        boolean renderFlag = (Boolean)this.getBindDepartmentEmployeesBox().getAttributes().get(PROPERTY_RENDERED);
        this.getBindDepartmentEmployeesBox().getAttributes().put(PROPERTY_RENDERED, renderFlag);
        this.persistAttributeChange(PROPERTY_RENDERED,this.getBindDepartmentEmployeesBox(),renderFlag);
    }

    public void setBindDepartmentEmployeesBox(RichPanelBox bindDepartmentEmployees) {
        this.bindDepartmentEmployees = bindDepartmentEmployees;
    }

    public RichPanelBox getBindDepartmentEmployeesBox() {
        return bindDepartmentEmployees;
    }
    public void setRenderDepartmentEmployees(boolean renderDepartmentEmployees) {
        this.renderDepartmentEmployees = renderDepartmentEmployees;
    }

    public boolean getRenderDepartmentEmployees() {
        if (this.getBindDepartmentEmployeesBox().isRendered()) {
            renderDepartmentEmployees = true;
        } else {
            renderDepartmentEmployees = false;
        }
        return renderDepartmentEmployees;
    }

    public void setBindDeptLocationsBox(RichPanelBox bindDeptLocations) {
        this.bindDeptLocations = bindDeptLocations;
    }

    public RichPanelBox getBindDeptLocationsBox() {
        return bindDeptLocations;
    }


    public void snhDeptLocations(ActionEvent actionEvent) {
        if(this.getBindDeptLocationsBox().isRendered()){
            this.getBindDeptLocationsBox().setRendered(false);
        }else{
            this.getBindDeptLocationsBox().setRendered(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getBindPanelDashboard());
        
        boolean renderFlag = (Boolean)this.getBindDeptLocationsBox().getAttributes().get(PROPERTY_RENDERED);
        this.getBindDeptLocationsBox().getAttributes().put(PROPERTY_RENDERED, renderFlag);
        this.persistAttributeChange(PROPERTY_RENDERED,this.getBindDeptLocationsBox(),renderFlag);
        
    }

    public void setRenderDeptLocations(boolean renderDepartmentLocations) {
        this.renderDepartmentLocations = renderDepartmentLocations;
    }

    public boolean getRenderDeptLocations() {
        if (this.getBindDeptLocationsBox().isRendered()) {
            renderDepartmentLocations = true;
        } else {
            renderDepartmentLocations = false;
        }
        return renderDepartmentLocations;
    }

    public void onLogout(ActionEvent actionEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        String url = ectx.getRequestContextPath() + 
                   "/adfAuthentication?logout=true&end_url=login.jsp";     
        try {
          ectx.redirect(url);
        } catch (IOException e) {
          e.printStackTrace();
        }
        fctx.responseComplete();
    }
}


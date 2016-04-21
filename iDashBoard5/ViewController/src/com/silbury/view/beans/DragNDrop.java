package com.silbury.view.beans;


import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DropEvent;

import org.apache.myfaces.trinidad.change.ChangeManager;
import org.apache.myfaces.trinidad.change.ReorderChildrenComponentChange;
import org.apache.myfaces.trinidad.context.RequestContext;

public class DragNDrop {
    public DragNDrop() {
    }

    public DnDAction DnDHandler(DropEvent dropEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        UIComponent panelDashBoard = dropEvent.getDropComponent();
        Transferable transferable = dropEvent.getTransferable();
        UIComponent draggedPanelBox = transferable.getData(DataFlavor.UICOMPONENT_FLAVOR);
        List<UIComponent> panelBoxeList = panelDashBoard.getChildren();
        //the drop index is the position where the dragged panelBox
        //should be added in the list of dashBoard child components
        int dropIndex = dropEvent.getDropSiteIndex();
        //get the component that currently is in the position the dragged
        //component should be added to. If this component is found when iterating
        //over the dash board child list, the dragged component is added before
        UIComponent dropChildComponent = panelBoxeList.get(dropIndex);
        List<String> reorderedComponentStringList = new ArrayList<String>();
        List<UIComponent> reorderedComponentList = new ArrayList<UIComponent>();
        //iterate over children to create new component order list
        for (UIComponent currComponent : panelBoxeList){
            String currComponentId = currComponent.getId();
            if(!currComponentId.equals(draggedPanelBox.getId())){
                //if the current component is the child component that the
                //drop index points to then add the dragged component
                if(currComponentId.equals(dropChildComponent.getId())){
                    reorderedComponentStringList.add(draggedPanelBox.getId());
                    reorderedComponentList.add(draggedPanelBox);
                    reorderedComponentStringList.add(currComponentId);
                    reorderedComponentList.add(currComponent);
                }else{
                    reorderedComponentStringList.add(currComponentId);
                    reorderedComponentList.add(currComponent);
                }
                    
                }
            }
        //write the new child over to the change manager. If MDS is enabled, then this
        //change will perist beyond application restart
        ReorderChildrenComponentChange reorderedChildChange = new ReorderChildrenComponentChange(reorderedComponentStringList);
        ChangeManager cm = RequestContext.getCurrentInstance().getChangeManager();
        cm.addComponentChange(fctx,panelDashBoard,reorderedChildChange);
        //change the dashboard component child order displayed on th, screen.
        //first, clean up the old state
        panelDashBoard.getChildren().clear();
        //then, add the new reordered state
        panelDashBoard.getChildren().addAll(reorderedComponentList);
        return DnDAction.NONE;
    }
}

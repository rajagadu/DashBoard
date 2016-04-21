package com.silbury.view.beans;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;


import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;


import oracle.adf.view.rich.util.ResetUtils;

import oracle.jbo.RowSetIterator;
import org.apache.myfaces.trinidad.model.RowKeySet;
import oracle.jbo.Key;
import oracle.jbo.Row;

import org.apache.myfaces.trinidad.event.SelectionEvent;



public class BB_CRUD {
    private RichTable empDataTble;
    private RichTable empTble;
    private RichPopup editPopup;
    private RichPopup addPopup;
    private RichPopup duplicatePopup;
    private RichInputText dupliInpu;
    private RichSelectOneChoice pageSize;
    private RichPanelCollection panCol;

    final String OLD_CURR_KEY_VIEWSCOPE_ATTR = "__oldCurrentRowKey__";
    private RichButton buttonResetByBean;
    private RichPanelFormLayout addPopupForm;
    private RichButton downloadLink;
    private RichPopup delPopup;
    private RichInputListOfValues depNameEdit;
    private RichInputListOfValues manNameEdit;
    private RichInputListOfValues jbNameEdit;

    public BB_CRUD() {
        super();
    }
    
    public void editPopupFetchListener(PopupFetchEvent popupFetchEvent) {
//        if (popupFetchEvent.getLaunchSourceClientId().contains("b1")) {
//            BindingContainer bindings = getBindings();
//            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
//            operationBinding.execute();
//        }else{
//            System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("employeeID"));
//            System.out.println(this.getEmpTble().getSelectedRowData());
//        }
    }
    
    public void editDialogListener(DialogEvent dialogEvent) {
        
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();   
            
            //To refresh the table with new data
            DCBindingContainer bind =(DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            bind.findIteratorBinding("Employees_EVO1Iterator").executeQuery();
        }
 

    }

    public void editPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
        refreshTable();
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setEmpDataTble(RichTable empDataTble) {
        this.empDataTble = empDataTble;
    }

    public RichTable getEmpDataTble() {
        return empDataTble;
    }

    public void setEmpTble(RichTable empTble) {
        this.empTble = empTble;
    }

    public RichTable getEmpTble() {
        return empTble;
    }

    @SuppressWarnings("unchecked")
    public String editAction() {
        
        return null;
    }

    public void setEditPopup(RichPopup editPopup) {
        this.editPopup = editPopup;
    }

    public RichPopup getEditPopup() {
        return editPopup;
    }

    public void setDuplicatePopup(RichPopup duplicatePopup) {
        this.duplicatePopup = duplicatePopup;
    }

    public RichPopup getDuplicatePopup() {
        return duplicatePopup;
    }
    
    public void setAddPopup(RichPopup addPopup) {
        this.addPopup = addPopup;
    }

    public RichPopup getAddPopup() {
        return addPopup;
    }
    
    


    public void addAction(ActionEvent actionEvent) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        operationBinding.execute();
        
        showPopup(this.getAddPopup());
    }
    
    public void showPopup(RichPopup popupNm){
        try {
           RichPopup.PopupHints hints = new RichPopup.PopupHints();
           popupNm.show(hints);
       } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        
    }
    
    public void refreshTable(){
        try {
           BindingContainer bindings = getBindings();
           OperationBinding operationBinding = bindings.getOperationBinding("Execute");
           operationBinding.execute();
           
           AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEmpTble());
       } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    }


    public void DeleteDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();//Delete1
            OperationBinding DelBnd = bindings.getOperationBinding("callDel");
            DelBnd.getParamsMap().put("empId", ADFContext.getCurrent().getPageFlowScope().get("EmplID").toString());
           // OperationBinding ComBnd = bindings.getOperationBinding("Commit");
            DelBnd.execute(); 
            
            //To refresh the table with new data
            DCBindingContainer bind =(DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            bind.findIteratorBinding("Employees_EVO1Iterator").executeQuery();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEmpTble());
            //ComBnd.execute();  
            refreshTable();
        }
  
    }


    @SuppressWarnings("unchecked")
    public void editActionListner(ActionEvent actionEvent) {
        String depNm = null;
        String mngNm = null;
        String mgFnm = null;
        String mgLnm = null;
        String jbNm = null;
        try {
           // System.out.println(AdfFacesContext.getCurrentInstance().getPageFlowScope().get("employeeID"));
            String empID = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("employeeID").toString();
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("executeValue");
            operationBinding.getParamsMap().put("empid", empID);
            operationBinding.execute();
            
            DCBindingContainer bind =(DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            Row currentRow = bind.findIteratorBinding("EmpValuesInserEOView1Iterator").getCurrentRow();
            if(currentRow.getAttribute("DepartmentName")!=null){
            depNm = currentRow.getAttribute("DepartmentName").toString();
            }
            if(currentRow.getAttribute("JobTitle")!=null){
            jbNm = currentRow.getAttribute("JobTitle").toString();
            }
            if(currentRow.getAttribute("FirstName1")!=null){//LastName1
            mngNm = currentRow.getAttribute("FirstName1").toString();
            }
           
            
            
            this.getDepNameEdit().setValue(depNm);
            this.getJbNameEdit().setValue(jbNm);
            this.getManNameEdit().setValue(mngNm);
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(depNameEdit);
            AdfFacesContext.getCurrentInstance().addPartialTarget(jbNameEdit);
            AdfFacesContext.getCurrentInstance().addPartialTarget(manNameEdit);

            showPopup(this.getEditPopup());


           
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        } 
    }
    

    @SuppressWarnings("unchecked")
    public void DuplicateActListnr(ActionEvent actionEvent) {

//        try
//        {
//            RowKeySet selectedEmps = this.getEmpTble().getSelectedRowKeys();
//            Iterator selectedEmpIter = selectedEmps.iterator();
//
//            DCBindingContainer dcBindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
//            DCIteratorBinding empIter = dcBindings.findIteratorBinding("EmployeesEO_VIEW1Iterator");
//
//            RowSetIterator empRSIter = empIter.getRowSetIterator();
//
//            Row currentRow = null;
//            while(selectedEmpIter.hasNext())
//            {
//
//               // System.out.println("HERE IN WHILE LOOP");
//                Key key = (Key)((List)selectedEmpIter.next()).get(0);
//            //    System.out.println("key ------------" + key);
//                currentRow = empRSIter.getRow(key);
//            }
//
//            ViewObject vo = empIter.getViewObject();
//    //            System.out.println("Result VO " + vo);
//            Row nr = vo.createRow();
//
//            nr.setAttribute("FirstName", currentRow.getAttribute("FirstName"));
//            nr.setAttribute("LastName", currentRow.getAttribute("LastName"));
//            nr.setAttribute("Email", currentRow.getAttribute("Email"));
//            nr.setAttribute("PhoneNumber", currentRow.getAttribute("PhoneNumber"));
//            nr.setAttribute("HireDate", currentRow.getAttribute("HireDate"));
//            nr.setAttribute("JobId", currentRow.getAttribute("JobId"));
//            nr.setAttribute("Salary", currentRow.getAttribute("Salary"));
//            nr.setAttribute("CommissionPct", currentRow.getAttribute("CommissionPct"));
//            nr.setAttribute("ManagerId", currentRow.getAttribute("ManagerId"));
//            nr.setAttribute("DepartmentId", currentRow.getAttribute("DepartmentId"));
//
//            vo.insertRow(nr);
//            showPopup(this.getDuplicatePopup());
//
//        } catch (Exception e) {
//            // TODO: Add catch code
//            e.printStackTrace();
//        }
    }

    
    public void duplicateDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().name().equals("ok")) {
            BindingContainer bindings = getBindings();
            
            System.out.println("COMMIT: "+this.getDupliInpu().getValue());
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();  
            refreshTable();
        }
    //        } else if (dialogEvent.getOutcome().name().equals("cancel")) {
    //            BindingContainer bindings = getBindings();
    //            OperationBinding operationBinding = bindings.getOperationBinding("Execute");
    //            operationBinding.execute();
    //        }
    }

    public void setDupliInpu(RichInputText dupliInpu) {
        this.dupliInpu = dupliInpu;
    }

    public RichInputText getDupliInpu() {
        return dupliInpu;
    }


    
    public String onDeptRowCreate1() {
        
           try {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
         //access the name of the iterator the table is bound to. Its "DepartmentsEO22View11Iterator"
         DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("Departments_EVO1Iterator");
         //access the underlying RowSetIterator
    
         
           RowSetIterator rsi = dciter.getRowSetIterator();
           //get handle to the last row
           Row lastRow = rsi.first();
           //obtain the index of the last row
           int lastRowIndex = rsi.getRangeIndexOf(lastRow);
           //create a new row
           Row newRow = rsi.createRow();
           //initialize the row
           newRow.setNewRowState(Row.STATUS_INITIALIZED);
           //add row to last index + 1 so it becomes last in the range set
           rsi.insertRowAtRangeIndex(lastRowIndex, newRow); 
           //make row the current row so it is displayed correctly
           rsi.setCurrentRow(newRow); 
       } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
         
                         
         return null;
    } 
    
    
    
        
    private RichPanelBox treeBinding;


    public void setTreeBinding(RichPanelBox treeBinding) {
        this.treeBinding = treeBinding;
    }

    public RichPanelBox getTreeBinding() {
        return treeBinding;
    }
    


    public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }

    @SuppressWarnings("unchecked")
    public void selectionListner1(SelectionEvent selectionEvent) {
        invokeEL("#{bindings.RegionsView.treeModel.makeCurrent}", new Class[] { SelectionEvent.class },
                 new Object[] { selectionEvent });
        RowKeySet rowKeyset = selectionEvent.getAddedSet();
        Iterator rksIterator = rowKeyset.iterator();

        String region = "";
        String country = "";
        String location = "";
        Integer count = 0;

        while (rksIterator.hasNext()) {

            List myKey = (List) rksIterator.next();
            count = myKey.size();

            if (count == 1) {
                region = (((Key) myKey.get(myKey.size() - 1)).getRowHandle()).toString();
            } else if (count == 2) {
                country = (myKey.get(myKey.size() - 1)).toString();
                country = country.substring(country.indexOf("[") + 1, country.indexOf("]"));
                country = country.replaceAll("\\s+", "");
            } else if (count == 3) {
                location = (myKey.get(myKey.size() - 1)).toString();
                location = location.substring(location.indexOf("[") + 1, location.indexOf("]"));
                location = location.replaceAll("\\s+", "");
            }
        }
        Map pageFlowScope = ADFContext.getCurrent().getPageFlowScope();
        pageFlowScope.put("regionId", region);

//        DCBindingContainer bindingContainer =
//            (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
//        DCDataControl dc = bindingContainer.findDataControl("iDashBoardAmDataControl");
//        AppModuleImpl appM = (AppModuleImpl) dc.getDataProvider();
//        appM.executeSearchVo(region.toString(), country.toString(), location.toString());
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("executeSearchVo");
        operationBinding.getParamsMap().put("region", region.toString());
        operationBinding.getParamsMap().put("country", country.toString());
        operationBinding.getParamsMap().put("location", location.toString());
        operationBinding.execute();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(empDataTble);
    }



    public void setPageSize(RichSelectOneChoice pageSize) {
        this.pageSize = pageSize;
    }

    public RichSelectOneChoice getPageSize() {
        return pageSize;
    }

    public void changeRangeSize(ValueChangeEvent valueChangeEvent) {
        try
        {
            if(valueChangeEvent.getNewValue()!=valueChangeEvent.getOldValue()){
       //    System.out.println("HERE 1");
                if (valueChangeEvent.getNewValue()!=null) {
        //            System.out.println("HERE 2 "+ valueChangeEvent.getNewValue().toString());
                    this.getEmpTble().resetStampState();
                    this.getEmpTble().setFetchSize(Integer.parseInt(valueChangeEvent.getNewValue().toString()));
                }
        //  System.out.println(">>>>>>>>>>>>>>>>>>>" + this.getPanCol().isInView());
           AdfFacesContext.getCurrentInstance().addPartialTarget(this.getEmpTble());
    //           AdfFacesContext.getCurrentInstance().addPartialTarget(this.getPanCol());
            }
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    }

    public void setPanCol(RichPanelCollection panCol) {
        this.panCol = panCol;
    }

    public RichPanelCollection getPanCol() {
        return panCol;
    }

    public String SaveAction() {
        try {
           BindingContainer bindings = getBindings();
           OperationBinding operationBinding = bindings.getOperationBinding("Commit");
           operationBinding.execute();
            
           FacesMessage Message = new FacesMessage("Record Saved Successfully!");   
            Message.setSeverity(FacesMessage.SEVERITY_INFO);   
            FacesContext fc = FacesContext.getCurrentInstance();   
            fc.addMessage(null, Message);   
       } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return null;
    }

    public void onDialogCancel(ClientEvent clientEvent) {
        try {
            BindingContainer bindings = getBindings();

            RichPopup popup = this.getAddPopup();
            popup.hide();

            //the cancel operation is executed with immediate=true to bypass the
            //model update. Therefore we manually deletethe new row from the iterator
            DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("EmpValuesInserEOView1Iterator");

            Row currentRow = dciter.getCurrentRow();
            // dciter.remove(currentRow);
            dciter.removeCurrentRow();

            ADFContext adfCtx = ADFContext.getCurrent();
            String oldCurrentRowKey = (String) adfCtx.getViewScope().get(OLD_CURR_KEY_VIEWSCOPE_ATTR);
            dciter.setCurrentRowWithKey(oldCurrentRowKey);
            //     AdfFacesContext.getCurrentInstance().addPartialTarget(this.getAddPopupForm());
            FacesContext fctx = FacesContext.getCurrentInstance();

            fctx.renderResponse();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }

  

        }

    public void resetFormFieldsListener(ActionEvent actionEvent) {
        // check if hte action has a component attatched
           UIComponent uiComp = actionEvent.getComponent();
           
           if (uiComp == null)
           {
               // if not we use the button which we bound to this bean
               uiComp=getButtonResetByBean();
             //  _logger.info("reset fields: buttonID = " + uiComp.getId());
           }
           else
           {
             //  _logger.info("reset fields: CompID = " + uiComp.getId());
           }
           // pass component inside the UIForm, UIXForm, UIXSubform, UIXRegion, UIXPopup, RichCarousel
           // or RichPanelCollection which holds the components to reset
           ResetUtils.reset(uiComp);    
           }

    public void setButtonResetByBean(RichButton buttonResetByBean) {
        this.buttonResetByBean = buttonResetByBean;
    }

    public RichButton getButtonResetByBean() {
        return buttonResetByBean;
    }

    public void setAddPopupForm(RichPanelFormLayout addPopupForm) {
        this.addPopupForm = addPopupForm;
    }

    public RichPanelFormLayout getAddPopupForm() {
        return addPopupForm;
    }

    public void setDownloadLink(RichButton downloadLink) {
        this.downloadLink = downloadLink;
    }

    public RichButton getDownloadLink() {
        return downloadLink;
    }


    
    public void addMessage(FacesMessage.Severity type, String message) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(type, message, null);
        fctx.addMessage(null, fm);
    }

    public void fileDownloadBean(FacesContext facesContext, OutputStream out) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ServletContext context =
            (ServletContext)fctx.getExternalContext().getContext();

        System.out.println(context.getRealPath("/"));

        File f = new File(context.getRealPath("/") + "SampleFile.csv");

        FileInputStream fdownload;
        byte[] b;
        try {
        //    System.out.println(f.getCanonicalPath());
         //   System.out.println(f.getAbsolutePath());
            fdownload = new FileInputStream(f);

            int n;
            while ((n = fdownload.available()) > 0) {
                b = new byte[n];
                int result = fdownload.read(b);
                out.write(b, 0, b.length);
                if (result == -1)
                    break;
            }
            out.flush();
        } catch (IOException e) {
            this.addMessage(FacesMessage.SEVERITY_ERROR,
                            "File cannot be downloaded , Please contact administrator.");
        }

    }

    public String raiseAndDel() {
        String empID = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("EmplID").toString();
      //  System.out.println(empID);
         

        showPopup(this.getDelPopup());

        return null;
    }

    public void setDelPopup(RichPopup delPopup) {
        this.delPopup = delPopup;
    }

    public RichPopup getDelPopup() {
        return delPopup;
    }

    public void setDepNameEdit(RichInputListOfValues depNameEdit) {
        this.depNameEdit = depNameEdit;
    }

    public RichInputListOfValues getDepNameEdit() {
        return depNameEdit;
    }

    public void setManNameEdit(RichInputListOfValues manNameEdit) {
        this.manNameEdit = manNameEdit;
    }

    public RichInputListOfValues getManNameEdit() {
        return manNameEdit;
    }

    public void setJbNameEdit(RichInputListOfValues jbNameEdit) {
        this.jbNameEdit = jbNameEdit;
    }

    public RichInputListOfValues getJbNameEdit() {
        return jbNameEdit;
    }
}

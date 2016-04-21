package com.silbury.view.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.ResultSet;

import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class Emp_Import {
    private RichTable empUploadTable;

    public Emp_Import() {
    }

    @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
    public void fileUploaded(ValueChangeEvent valueChangeEvent) {
    // Add event code here...    
    UploadedFile file = (UploadedFile)valueChangeEvent.getNewValue();
    try {
      AdfFacesContext.getCurrentInstance().addPartialTarget(empUploadTable);
      parseFile(file.getInputStream());
      
    } catch (IOException e) {
      // TODO add more
    }
  }


    @SuppressWarnings({ "oracle.jdeveloper.java.nested-assignment", "oracle.jdeveloper.java.switch-fall-through" })
    public void parseFile(java.io.InputStream file) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(file));
    String strLine = "";
    StringTokenizer st = null;
    int lineNumber = 0, tokenNumber = 0;
    Row rw = null;
   
    CollectionModel _tableModel = (CollectionModel)empUploadTable.getValue();
    //the ADF object that implements the CollectionModel is JUCtrlHierBinding. It
    //is wrapped by the CollectionModel API
    JUCtrlHierBinding _adfTableBinding =
        (JUCtrlHierBinding)_tableModel.getWrappedData();
    //Acess the ADF iterator binding that is used with ADF table binding
    DCIteratorBinding it =
        _adfTableBinding.getDCIteratorBinding();
         
    //read comma separated file line by line
    try 
    {
      while ((strLine = reader.readLine()) != null)
      {
        lineNumber++;
        // create a new row skip the header  (header has linenumber 1)           
        if (lineNumber>1)        {
           rw = it.getNavigatableRowIterator().createRow();
           rw.setNewRowState(Row.STATUS_INITIALIZED);
           it.getNavigatableRowIterator().insertRow(rw);
        }
        
        //break comma separated line using ","
        st = new StringTokenizer(strLine, ",");
        while (st.hasMoreTokens())
        {
          //display csv values
          tokenNumber++;
          
          String theToken = st.nextToken();
          System.out.println("Line # " + lineNumber + ", Token # " +
              tokenNumber +
              ", Token : " + theToken);
          if (lineNumber>1){
         
            switch (tokenNumber) {
                case 1: rw.setAttribute("EmployeeId", theToken);
                case 2: rw.setAttribute("FirstName", theToken);
                case 3: rw.setAttribute("LastName", theToken);
              case 4: rw.setAttribute("DepartmentId", theToken); 
              case 5: rw.setAttribute("ManagerId", theToken);  
                case 6: rw.setAttribute("PhoneNumber", theToken);      
                case 7: rw.setAttribute("Salary", theToken);  
          }
        }
      }
      //reset token number        
      tokenNumber = 0;    
     } 
    }
    catch (IOException e) {
      // TODO add more
      FacesContext fctx = FacesContext.getCurrentInstance();
      fctx.addMessage(empUploadTable.getClientId(fctx), new FacesMessage(FacesMessage.SEVERITY_ERROR,
         "Content Error in Uploaded file", e.getMessage()));
    }
    catch (Exception e) {
    FacesContext fctx = FacesContext.getCurrentInstance();
    fctx.addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
       "Data Error in Uploaded file", e.getMessage()));
      
    }
  }

  public void setempUploadTable(RichTable empUploadTable) {
    this.empUploadTable = empUploadTable;
  }

  public RichTable getempUploadTable() {
    return empUploadTable;
  }

    public void removeAll(ActionEvent actionEvent) {

        try {
            DCBindingContainer dc = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();

            ViewObject myVo = dc.findIteratorBinding("EmpUploadVO_1Iterator").getViewObject();
            RowSetIterator rsI = myVo.createRowSetIterator(null);
            //System.out.println("COUNT>>>>"+rsI.getRowCount());
            while (rsI.hasNext()) {
                rsI.next().remove();
            }
            // System.out.println("COUNT 1111 >>>>"+rsI.getRowCount());
            rsI.closeRowSetIterator();

            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            ;
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        

    }

    public void setEmpUploadTable(RichTable empUploadTable) {
        this.empUploadTable = empUploadTable;
    }

    public RichTable getEmpUploadTable() {
        return empUploadTable;
    }
}

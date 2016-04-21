package com.silbury.view.beans;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class WorkflowProcess {
    private RichOutputFormatted bindTaskId;
    private RichInputText taskNameBinding;

    public WorkflowProcess() {
    }

    /**Method to get Binding Container of page
     * @return
     */
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public void setBindTaskId(RichOutputFormatted bindTaskId) {
        this.bindTaskId = bindTaskId;
    }

    public RichOutputFormatted getBindTaskId() {
        return bindTaskId;
    }
    
    public void setTaskNameBinding(RichInputText taskNameBinding) {
        this.taskNameBinding = taskNameBinding;
    }

    public RichInputText getTaskNameBinding() {
        return taskNameBinding;
    }
    
    // Methods for Workflow Create,Approve and Reject
    @SuppressWarnings("unchecked")
    public void createTask(DialogEvent dialogEvent) {
        try{	
            String taskName = null;
            if(dialogEvent.getOutcome() == DialogEvent.Outcome.ok){
                if(this.getTaskNameBinding() != null){
                    taskName = this.getTaskNameBinding().getValue().toString();
                    OperationBinding ob = getBindings().getOperationBinding("insertQuery");
                    //Passing parameter to method -Get parameter map and use paramater name as key
                    ob.getParamsMap().put("taskName", taskName );
                    //Execute this method
                    ob.execute();                
                }else{
                    //todo raise Message
                }
            } 	
        }
        catch(Exception e){
            // TODO: Add catch code
            e.printStackTrace();
        }    
    }
    
    /**Method to Execute DB SQL Query using DBTransaction
     * @param query
     * @return
     */
    @SuppressWarnings("unchecked")
    public void workflowApprove(ActionEvent actionEvent) {
        Integer taskIdValue = Integer.parseInt(ADFUtil.evaluateEL("#{item.bindings.Taskid.inputValue}").toString());
        Integer wfCompletionValue =
            Integer.parseInt(ADFUtil.evaluateEL("#{item.bindings.Wfcompletion.inputValue}").toString());
        System.out.println("Workflow Approval task id : " + taskIdValue + "Workflow Completion Value" + wfCompletionValue);
        String query;
        if (wfCompletionValue < 100) {
            Integer newWfCompletionValue = wfCompletionValue + 25;
            if (newWfCompletionValue == 100) {
                //Sr Manager approval query
                query ="update sil_workflow set WFACTIONDATE=sysdate,WFSTEP='',WFNAME='Completed',WFUSERINQUEUE='',WFCOMPLETION=100,WFLASTACTION='Approve',WFLASTACTIONSTEP='SrManager' where taskid='" +
                                      taskIdValue + "'";
                //Calling a method from AppModuleImpl class
                //Get OperationBinding of method
                OperationBinding ob = getBindings().getOperationBinding("updateQuery");
                //Passing parameter to method -Get parameter map and use paramater name as key
                ob.getParamsMap().put("query", query);
                //Execute this method
                ob.execute();
                //Check for errors
                if (ob.getErrors().isEmpty()) {
                    // Successfully Executed
                    System.out.println("Query is Sil_workflow succesfully executed !!! ");
                }

                query ="update sil_task set TASKSTATUS='Approved',TASKWORKFLOW='Completed' where taskid='" + taskIdValue +"'";
                ob.getParamsMap().put("query", query);
                ob.execute();
                //Check for errors
                if (ob.getErrors().isEmpty()) {
                    // Successfully Executed
                    System.out.println("Query is Sil_Task succesfully executed !!!");
                }
            } else {
                query =
                    "update sil_workflow set WFACTIONDATE=sysdate,WFSTEP='SrManager',WFUSERINQUEUE='Tom',WFCOMPLETION=" + newWfCompletionValue + " where taskid='" + taskIdValue +
                    "'";
                OperationBinding ob = getBindings().getOperationBinding("updateQuery");
                ob.getParamsMap().put("query", query);
                ob.execute();
                //Check for errors
                if (ob.getErrors().isEmpty()) {
                    // Successfully Executed
                    System.out.println("Query is Sil_workflow Manager Step succesfully executed !!! ");
                }
            }
        } else {
            query = null;
        }
    }

    @SuppressWarnings("unchecked")
    public void workflowReject(ActionEvent actionEvent) {
        try {
           Integer taskIdValue = Integer.parseInt(ADFUtil.evaluateEL("#{item.bindings.Taskid.inputValue}").toString());
           System.out.println("Workflow Reject task id : " + taskIdValue);
           String wfStepValue = ADFUtil.evaluateEL("#{item.bindings.Wfstep.inputValue}").toString();
           System.out.println("Workflow Step ::::::::::" + wfStepValue );
           String query = null;
           if(wfStepValue.equals("SrManager")){
               query ="UPDATE sil_workflow SET wfstep='Manager',wfuserinqueue='Bob',wfcompletion='50' WHERE taskid='" + taskIdValue +"'";
               OperationBinding ob = getBindings().getOperationBinding("updateQuery");
               ob.getParamsMap().put("query", query);
               ob.execute();
               //Check for errors
               if (ob.getErrors().isEmpty()) {
                   // Successfully Executed
                   System.out.println("Rejected by Senior Manager succesfully executed !!!!!! ");
               }
           }else{
               query ="UPDATE sil_task SET taskworkflow = 'Reject',taskstatus = 'Rejected',taskcreator='Contributor' WHERE taskid='" + taskIdValue +"'";
               OperationBinding ob = getBindings().getOperationBinding("updateQuery");
               ob.getParamsMap().put("query", query);
               ob.execute();
               //Check for errors
               if (ob.getErrors().isEmpty()) {
                   // Successfully Executed
                   System.out.println("Rejected by Manager succesfully executed(1st Step) !!!!!! ");
               }
               query ="UPDATE sil_workflow SET wfstep='',wfuserinqueue='',wfcompletion=100,wfname='Reject',wflastaction='Create',wflastactionstep='Entry' WHERE taskid='" + taskIdValue +"'";
               ob.getParamsMap().put("query", query);
               ob.execute();
               //Check for errors
               if (ob.getErrors().isEmpty()) {
                   // Successfully Executed
                   System.out.println("Rejected by Manager succesfully executed(2nd Step) !!!!!! ");
               }
           }
       } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        
    }

   
}

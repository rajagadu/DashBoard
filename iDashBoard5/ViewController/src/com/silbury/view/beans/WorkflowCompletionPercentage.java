package com.silbury.view.beans;


import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.component.rich.output.RichProgressIndicator;

import org.apache.myfaces.trinidad.model.BoundedRangeModel;


public class WorkflowCompletionPercentage extends BoundedRangeModel {


    private RichOutputFormatted bindTaskId;
    private String styleClass1;

    public WorkflowCompletionPercentage() {
        super();
    }

    @Override
    public long getMaximum() {
        // TODO Implement this method
        return 100L;
    }

    @Override
    public long getValue() {
        // TODO Implement this method
        // System.out.println(Integer.parseInt(ADFUtil.evaluateEL("#{row.Wfcompletion}").toString())+"Test");

        return (Integer.parseInt(ADFUtil.evaluateEL("#{item.bindings.Wfcompletion.inputValue}").toString()));
        //return (Integer.parseInt("20"));
        //        return (Integer.parseInt(getProVal().toString()));

    }

    public void setStyleClass(String styleClass1) {
        this.styleClass1 = styleClass1;
    }

    public String getStyleClass() {
        String styleClass = null;
        try {
            Integer wfCompValue =
                Integer.parseInt(ADFUtil.evaluateEL("#{item.bindings.Wfcompletion.inputValue}").toString());
            String wfTaskStatus = ADFUtil.evaluateEL("#{item.bindings.Taskstatus.inputValue}").toString();
            
            if (wfCompValue == 100 && wfTaskStatus.equals("Rejected")) {
                styleClass = "red";
            } else if (wfCompValue == 100 && wfTaskStatus.equals("Approved")) {
                styleClass = "green";
            } else {
                styleClass = "default";
            }
           

        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return styleClass;
    }


}

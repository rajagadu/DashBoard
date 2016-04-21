package com.silbury.view.beans;

import java.io.Serializable;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

public class ShowHide implements Serializable {
    
    private String xyz = null;
    private RichButton shwHidButton;

    public ShowHide() {
    }
    
    
    
    public void setxyz(String xyz){
        this.xyz = xyz;
        }
    
    public String getxyz(){
        return xyz;
        }


    public String ShowHide_action() {
        String status = getxyz();
         // get an ADF attributevalue from the ADF page definitions
      if (status=="unHide"){
            this.setxyz("Hide");
            this.getShwHidButton().setText("Show Fields");
            AdfFacesContext.getCurrentInstance().addPartialTarget(shwHidButton);            
        }   else{
            this.setxyz("unHide");
                this.getShwHidButton().setText("Hide Fields");
                AdfFacesContext.getCurrentInstance().addPartialTarget(shwHidButton);
            }
        return null;
    }

    public void setShwHidButton(RichButton shwHidButton) {
        this.shwHidButton = shwHidButton;
    }

    public RichButton getShwHidButton() {
        return shwHidButton;
    }
}

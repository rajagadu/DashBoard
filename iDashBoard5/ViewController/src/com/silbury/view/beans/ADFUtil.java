package com.silbury.view.beans;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFrame;


/**
 * Provides various utility methods that are handy to
 * have around when working with ADF.
 */
public class ADFUtil {
    public ADFUtil() {
        super();
    }
    
    /**
    * When a bounded task flow manages a transaction (marked as requires-transaction,.
    * requires-new-transaction, or requires-existing-transaction), then the
    * task flow must issue any commits or rollbacks that are needed. This is
    * essentially to keep the state of the transaction that the task flow understands
    * in synch with the state of the transaction in the ADFbc layer.
    *
    * Use this method to issue a commit in the middle of a task flow while staying
    * in the task flow.
    */
    public static void saveAndContinue() {
    Map sessionMap =
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    BindingContext context =
    (BindingContext)sessionMap.get(BindingContext.CONTEXT_ID);
    String currentFrameName = context.getCurrentDataControlFrame();
    DataControlFrame dcFrame =
    context.findDataControlFrame(currentFrameName);

    dcFrame.commit();
    dcFrame.beginTransaction(null);
    }

    /**
    * Programmatic evaluation of EL.
    *
    * @param el EL to evaluate
    * @return Result of the evaluation
    */
    public static Object evaluateEL(String el) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    ValueExpression exp =
    expressionFactory.createValueExpression(elContext, el,
    Object.class);

    return exp.getValue(elContext);
    }

    /**
    * Programmatic invocation of a method that an EL evaluates to.
    * The method must not take any parameters.
    *
    * @param el EL of the method to invoke
    * @return Object that the method returns
    */
    public static Object invokeEL(String el) {
    return invokeEL(el, new Class[0], new Object[0]);
    }

    /**
    * Programmatic invocation of a method that an EL evaluates to.
    *
    * @param el EL of the method to invoke
    * @param paramTypes Array of Class defining the types of the parameters
    * @param params Array of Object defining the values of the parametrs
    * @return Object that the method returns
    */
    public static Object invokeEL(String el, Class[] paramTypes,
    Object[] params) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    MethodExpression exp =
    expressionFactory.createMethodExpression(elContext, el,
    Object.class, paramTypes);

    return exp.invoke(elContext, params);
    }

    /**
    * Sets a value into an EL object. Provides similar functionality to
    * the < af:setActionListener> tag, except the from is
    * not an EL. You can get similar behavior by using the following...

    * setEL(to, evaluateEL(from))
    *
    * @param el EL object to assign a value
    * @param val Value to assign
    */
    public static void setEL(String el, Object val) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    ValueExpression exp =
    expressionFactory.createValueExpression(elContext, el,
    Object.class);

    exp.setValue(elContext, val);
    }
}

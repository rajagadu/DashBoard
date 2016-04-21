package com.silbury.view.beans;

import javax.faces.event.ActionEvent;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public class onSearch {
    
    private String searchString = "";
    private String searchType = "CONTAIN";
    private List searchAttributes = new ArrayList();
    private RichTree tree1 = null;
    ADFLogger logger = ADFLogger.createADFLogger(this.getClass());


    public onSearch() {
        super();
    }


    public void onTreeSearch(ActionEvent actionEvent) {
        JUCtrlHierBinding treeBinding = null;

        //get handle to tree if it does not exist. If tree component cannot be
        //found in view, exit this function
        if (tree1 == null) {
            this.findTreeInView();
            if (tree1 == null) {
                //tree not found
                log("The tree component could not be found in the view. Please check for naming containers. Search function cancelled");
                return;
            }
        }
        //Get the JUCtrlHierbinding reference from the PageDef
        CollectionModel model = (CollectionModel)tree1.getValue();
        treeBinding = (JUCtrlHierBinding)model.getWrappedData();

        //Read the attributes to search in from the SelectManyChoice component
        
        //String searchAttributeArray[] = (String[])searchAttributes.toArray(new String[searchAttributes.size()]);
        
        //hard coding value to 3
        String searchAttributeArray[] = (String[])searchAttributes.toArray(new String[3]);

        //Define a node to search in. In this example, the root node is used
        JUCtrlHierNodeBinding root = treeBinding.getRootNodeBinding();

        //However, if the user used the "Show as Top" context menu option to
        //shorten the tree display, then we only search starting from this top
        //mode

        List topNode = (List)tree1.getFocusRowKey();
        if (topNode != null) {
            //make top node the root node for the search
            root = treeBinding.findNodeByKeyPath(topNode);
        }

        //Select the tree items that match the search criteria and expand the
        //tree to display them
        System.out.println("searchAttributeArray===== "+searchAttributeArray.toString());
        RowKeySet resultRowKeySet =
        //  searchTreeNode(root, searchAttributeArray, searchType,searchString);
            
        searchTreeNode(root, searchAttributeArray, searchType, searchString.trim());
        
      //  System.out.println("resultRowKeySet==== "+resultRowKeySet);
        RowKeySet disclosedRowKeySet =
            buildDiscloseRowKeySet(treeBinding, resultRowKeySet);
        
      //  System.out.println("disclosedRowKeySet==== "+disclosedRowKeySet);
        tree1.getSelectedRowKeys().clear();
        tree1.getSelectedRowKeys().addAll(resultRowKeySet);
        //tree1.setSelectedRowKeys(resultRowKeySet);
        tree1.getDisclosedRowKeys().clear();
        tree1.getDisclosedRowKeys().addAll(disclosedRowKeySet);
        //tree1.setDisclosedRowKeys(disclosedRowKeySet);

        AdfFacesContext.getCurrentInstance().addPartialTarget(tree1);
    }

    /**
     * Method that parses an ADF bound ADF Faces tree component to find search string matches
     * in one of the specified attribute names. Attribute names are ignored if they don't exist
     * in the search node. The method performs a recursiv search and returns a RowKeySet with the
     * row keys of all nodes that contain the search string
     * @param  node The JUCtrlHierNodeBinding instance to search
     * @param  searchAttributes An array of attribute names to search in
     * @param  searchType defines where the search is started within the text. Valid values are
     *         START, CONTAIN, END. If NULL the "CONTAIN" is set as the default
     * @param  searchString  The search condition
     * @return RowKeySet row keys
     */
    private RowKeySet searchTreeNode(JUCtrlHierNodeBinding node,
                                     String[] searchAttributes,
                                     String searchType, String searchString) {
        RowKeySetImpl rowKeys = new RowKeySetImpl();
        //set default search
        String _searchType =
            searchType == null ? "CONTAIN" : searchType.length() > 0 ?
                                             searchType : "CONTAIN";
        
        //searchType = "CONTAIN";
        
       // System.out.println("searchType===== "+searchType);
        
        //Sanity checks
        if (node == null) {
            log("Node passed as NULL");
            return rowKeys;
        }
        
        //hard coding 3 values from selectManyChoice in NewEMView.jsff to search
        searchAttributes[0]="RegionName";
        searchAttributes[1]="CountryName";
        searchAttributes[2]="City";
        
        if (searchAttributes == null || searchAttributes.length < 1) {
            log(node.getName() +
                ": search attribute is NULL or has a ZERO length");
            return rowKeys;
        }
        if (searchString == null || searchString.length() < 1) {
            log(node.getName() + ": Search string cannot be NULL or EMPTY");
            return rowKeys;
        }
        
        
        System.out.println("searchString=====  "+searchString.trim());
        Row nodeRow = node.getRow();
        if (nodeRow != null) {
            for (int i = 0; i < searchAttributes.length; i++) {
                String compareString = "";
                try {
                    Object attribute =
                        nodeRow.getAttribute(searchAttributes[i]);
                    if (attribute instanceof String) {
                        compareString = (String)attribute;
                    } else {
                        //try the toString method as a simple fallback
                        compareString = attribute.toString();
                    }
                  
                  //  System.out.println("compareString======="+compareString);
                } catch (oracle.jbo.JboException attributeNotFound) {
                    //node does not have attribute. Exclude from search
                }
                //compare strings case insesitive.
                if (_searchType.equalsIgnoreCase("CONTAIN") &&
                    compareString.toUpperCase().indexOf(searchString.toUpperCase()) >
                    -1) {
                    //get row key
                    rowKeys.add(node.getKeyPath());
                } else if (_searchType.equalsIgnoreCase("START") &&
                           compareString.toUpperCase().startsWith(searchString.toUpperCase())) {
                    //get row key
                    rowKeys.add(node.getKeyPath());
                } else if (_searchType.equalsIgnoreCase("END") &&
                           compareString.toUpperCase().endsWith(searchString.toUpperCase())) {
                    //get row key
                    rowKeys.add(node.getKeyPath());
                }
            }
        }

        List<JUCtrlHierNodeBinding> children = node.getChildren();

        if (children != null) {
            for (JUCtrlHierNodeBinding _node : children) {
                //Each child search returns a row key set that must be added to the
                //row key set returned by the overall search
                RowKeySet rks =
                    searchTreeNode(_node, searchAttributes, this.searchType,
                                   searchString);
                if (rks != null && rks.size() > 0) {
                    rowKeys.addAll(rks);
                }
            }
        }
        return rowKeys;
    }

    /**
     * Helper method that returns a list of parent node for the RowKeySet passed
     * as the keys argument. The RowKeySet can be used to disclose the folders in
     * which the keys reside. Node that to disclose a full branch, all RowKeySet
     * that are in the path must be defined
     *
     * @param  treeBinding ADF tree binding instance read from the PageDef file
     * @param  keys  RowKeySet containing List entries of oracle.jbo.Key
     * @return RowKeySet of parent keys to disclose
     */
    private RowKeySet buildDiscloseRowKeySet(JUCtrlHierBinding treeBinding,
                                             RowKeySet keys) {
        RowKeySetImpl discloseRowKeySet = new RowKeySetImpl();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            List keyPath = (List)iter.next();
            JUCtrlHierNodeBinding node =
                treeBinding.findNodeByKeyPath(keyPath);
            if (node != null && node.getParent() != null &&
                !node.getParent().getKeyPath().isEmpty()) {
                //store the parent path
                discloseRowKeySet.add(node.getParent().getKeyPath());

                //call method recursively until no parents are found
                RowKeySetImpl parentKeySet = new RowKeySetImpl();
                parentKeySet.add(node.getParent().getKeyPath());
                RowKeySet rks =
                    buildDiscloseRowKeySet(treeBinding, parentKeySet);
                discloseRowKeySet.addAll(rks);
            }
        }
        return discloseRowKeySet;
    }

    private void log(String message) {
        logger.log(ADFLogger.WARNING, message);
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchAttributes(List searchAttributes) {
        this.searchAttributes = searchAttributes;
    }

    public List getSearchAttributes() {
        return searchAttributes;
    }

    //To learn what the code below is doing, please see Sample #58 on ADF Code Corner
    //http://www.oracle.com/technetwork/developer-tools/adf/learnmore/index-101235.html

    private void findTreeInView() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        UIViewRoot root = fctx.getViewRoot();
        //hard coding tree component Id with its surrounding naming container ID
        //PanelCollection
        root.invokeOnComponent(fctx, "pc1:tree1", new ContextCallback() {
                public void invokeContextCallback(FacesContext facesContext,
                                                  UIComponent uiComponent) {
                    tree1 = (RichTree)uiComponent;
                }
            });
    }

    public void setTree1(RichTree tree1) {
        this.tree1 = tree1;
    }

    public RichTree getTree1() {
        return tree1;
    }
    
}


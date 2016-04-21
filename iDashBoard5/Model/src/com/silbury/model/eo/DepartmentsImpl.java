package com.silbury.model.eo;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Mar 05 14:12:14 IST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DepartmentsImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        DepartmentId,
        DepartmentName,
        ManagerId,
        LocationId,
        Employees,
        Employees1,
        JobHistory,
        Locations,
        EmpValuesInserEO,
        EmpValuesInserEO1;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int DEPARTMENTID = AttributesEnum.DepartmentId.index();
    public static final int DEPARTMENTNAME = AttributesEnum.DepartmentName.index();
    public static final int MANAGERID = AttributesEnum.ManagerId.index();
    public static final int LOCATIONID = AttributesEnum.LocationId.index();
    public static final int EMPLOYEES = AttributesEnum.Employees.index();
    public static final int EMPLOYEES1 = AttributesEnum.Employees1.index();
    public static final int JOBHISTORY = AttributesEnum.JobHistory.index();
    public static final int LOCATIONS = AttributesEnum.Locations.index();
    public static final int EMPVALUESINSEREO = AttributesEnum.EmpValuesInserEO.index();
    public static final int EMPVALUESINSEREO1 = AttributesEnum.EmpValuesInserEO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DepartmentsImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("com.silbury.model.eo.Departments");
    }


    /**
     * Gets the attribute value for DepartmentId, using the alias name DepartmentId.
     * @return the value of DepartmentId
     */
    public Number getDepartmentId() {
        return (Number) getAttributeInternal(DEPARTMENTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DepartmentId.
     * @param value value to set the DepartmentId
     */
    public void setDepartmentId(Number value) {
        setAttributeInternal(DEPARTMENTID, value);
    }

    /**
     * Gets the attribute value for DepartmentName, using the alias name DepartmentName.
     * @return the value of DepartmentName
     */
    public String getDepartmentName() {
        return (String) getAttributeInternal(DEPARTMENTNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for DepartmentName.
     * @param value value to set the DepartmentName
     */
    public void setDepartmentName(String value) {
        setAttributeInternal(DEPARTMENTNAME, value);
    }

    /**
     * Gets the attribute value for ManagerId, using the alias name ManagerId.
     * @return the value of ManagerId
     */
    public Integer getManagerId() {
        return (Integer) getAttributeInternal(MANAGERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for ManagerId.
     * @param value value to set the ManagerId
     */
    public void setManagerId(Integer value) {
        setAttributeInternal(MANAGERID, value);
    }

    /**
     * Gets the attribute value for LocationId, using the alias name LocationId.
     * @return the value of LocationId
     */
    public Integer getLocationId() {
        return (Integer) getAttributeInternal(LOCATIONID);
    }

    /**
     * Sets <code>value</code> as the attribute value for LocationId.
     * @param value value to set the LocationId
     */
    public void setLocationId(Integer value) {
        setAttributeInternal(LOCATIONID, value);
    }

    /**
     * @return the associated entity oracle.jbo.server.EntityImpl.
     */
    public EntityImpl getEmployees() {
        return (EntityImpl) getAttributeInternal(EMPLOYEES);
    }

    /**
     * Sets <code>value</code> as the associated entity oracle.jbo.server.EntityImpl.
     */
    public void setEmployees(EntityImpl value) {
        setAttributeInternal(EMPLOYEES, value);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getEmployees1() {
        return (RowIterator) getAttributeInternal(EMPLOYEES1);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getJobHistory() {
        return (RowIterator) getAttributeInternal(JOBHISTORY);
    }

    /**
     * @return the associated entity oracle.jbo.server.EntityImpl.
     */
    public EntityImpl getLocations() {
        return (EntityImpl) getAttributeInternal(LOCATIONS);
    }

    /**
     * Sets <code>value</code> as the associated entity oracle.jbo.server.EntityImpl.
     */
    public void setLocations(EntityImpl value) {
        setAttributeInternal(LOCATIONS, value);
    }


    /**
     * @return the associated entity oracle.jbo.server.EntityImpl.
     */
    public EmpValuesInserEOImpl getEmpValuesInserEO() {
        return (EmpValuesInserEOImpl) getAttributeInternal(EMPVALUESINSEREO);
    }

    /**
     * Sets <code>value</code> as the associated entity oracle.jbo.server.EntityImpl.
     */
    public void setEmpValuesInserEO(EmpValuesInserEOImpl value) {
        setAttributeInternal(EMPVALUESINSEREO, value);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getEmpValuesInserEO1() {
        return (RowIterator) getAttributeInternal(EMPVALUESINSEREO1);
    }


    /**
     * @param departmentId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Number departmentId) {
        return new Key(new Object[] { departmentId });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
        setDepartmentId(getNextSequenceVal("DEPARTMENTS_SEQ"));
    }
    
    protected Number getNextSequenceVal(String sequenceName){        
        SequenceImpl s = new SequenceImpl(sequenceName, getDBTransaction());
        return s.getSequenceNumber();
    }
}


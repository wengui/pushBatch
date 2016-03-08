package com.push.bean.gen;


public class NanoCheckerReport {
  
	//病人名字
    private String PatientName;
 
	//病人年龄
    private Integer Age;
    //项目英文名字
    private String ItemId;
    //项目中文名字
    private String ItemName;
    //结果值
    private Integer Value;

    //最小值
    private Integer Min;
    //最大值
    private Integer Max;
    //参考值
    private String Reference;

    public String getPatientName() {
 		return PatientName;
 	}
 	public void setPatientName(String patientName) {
 		PatientName = patientName;
 	}
 	public Integer getAge() {
 		return Age;
 	}
 	public void setAge(Integer age) {
 		Age = age;
 	}
 	public String getItemId() {
 		return ItemId;
 	}
 	public void setItemId(String itemId) {
 		ItemId = itemId;
 	}
 	public String getItemName() {
 		return ItemName;
 	}
 	public void setItemName(String itemName) {
 		ItemName = itemName;
 	}
 	public Integer getValue() {
 		return Value;
 	}
 	public void setValue(Integer value) {
 		Value = value;
 	}
 	public Integer getMin() {
 		return Min;
 	}
 	public void setMin(Integer min) {
 		Min = min;
 	}
 	public Integer getMax() {
 		return Max;
 	}
 	public void setMax(Integer max) {
 		Max = max;
 	}
 	public String getReference() {
 		return Reference;
 	}
 	public void setReference(String reference) {
 		Reference = reference;
 	}
}
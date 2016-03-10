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
    private Float Value;

    //最小值
    private Float Min;
    //最大值
    private Float Max;
    //参考值
    private String Reference;
    //单位
    private String Unit;
    //颜色
    private String Color;

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
 	public Float getValue() {
 		return Value;
 	}
 	public void setValue(Float value) {
 		Value = value;
 	}
 	public Float getMin() {
 		return Min;
 	}
 	public void setMin(Float min) {
 		Min = min;
 	}
 	public Float getMax() {
 		return Max;
 	}
 	public void setMax(Float max) {
 		Max = max;
 	}
 	public String getReference() {
 		return Reference;
 	}
 	public void setReference(String reference) {
 		Reference = reference;
 	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
 	
}
package com.afauria.entity;

public class ClassScoreCount {
	private String clazz;
	private int m90;
	private int m80;
	private int m60;
	private int m0;
	private int total;

	public ClassScoreCount() {
		super();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public int getM90() {
		return m90;
	}

	public void setM90(int m90) {
		this.m90 = m90;
	}

	public int getM80() {
		return m80;
	}

	public void setM80(int m80) {
		this.m80 = m80;
	}

	public int getM60() {
		return m60;
	}

	public void setM60(int m60) {
		this.m60 = m60;
	}

	public int getM0() {
		return m0;
	}

	public void setM0(int m0) {
		this.m0 = m0;
	}
	@Override
	public String toString() {
		
		return m90+","+m80 + ","+m60;
	}
}
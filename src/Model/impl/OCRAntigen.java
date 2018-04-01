package Model.impl;

import java.io.Serializable;

import Model.Abstraction.Antigen;

public class OCRAntigen implements Antigen<boolean[]>{

	
	private boolean[] data;
	private double affinity;
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public boolean[] getData() {
		return data;
	}


	public void setData(boolean[] data) {
		this.data = data;
	}


	public double getAffinity() {
		return affinity;
	}


	public void setAffinity(double affinity) {
		this.affinity = affinity;
	}

}

package Model.impl;

import java.io.Serializable;

import Model.Abstraction.Antigen;

public class OCRAntigen implements Antigen<boolean[]>{

	
	private boolean[] data;
	
	public OCRAntigen(boolean[] data) {
		this.data = data;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	public boolean[] getData() {
		return data;
	}

	@Override
	public void setData(boolean[] data) {
		this.data = data;
	}

}

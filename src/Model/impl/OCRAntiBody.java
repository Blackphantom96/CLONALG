package Model.impl;

import java.util.Random;

import Model.Abstraction.AntiBody;

public class OCRAntiBody implements AntiBody<boolean[]>{
	
	
	private boolean[] data;
	private double affinity;
	
	public OCRAntiBody(boolean[] data) {
		this.data = data;
		this.affinity = Double.MIN_VALUE;
	}
	public OCRAntiBody(int x) {
		Random rand = new Random();
		data = new boolean[x];
		for(int i =0 ; i<x;i++)
			data[i] = rand.nextBoolean();
		this.affinity = Double.MIN_VALUE;
	}
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
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

	@Override
	public double getAffinity() {
		return affinity;
	}

	@Override
	public void setAffinity(double affinity) {
		this.affinity = affinity;
	}
	
}

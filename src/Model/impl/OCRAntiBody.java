package Model.impl;

import java.util.Arrays;
import java.util.Random;

import Model.Abstraction.AntiBody;

public class OCRAntiBody implements AntiBody<boolean[]>{
	
	
	private boolean[] data;
	private double affinity;
	
	public OCRAntiBody(boolean[] data) {
		this.data = data;
		this.affinity = Double.MIN_VALUE;
	}
	public OCRAntiBody(boolean[] data,double aff) {
		this.data = data;
		this.affinity = aff;
	}
	public OCRAntiBody(int x) {
		Random rand = new Random();
		data = new boolean[x];
		for(int i =0 ; i<x;i++)
			data[i] = rand.nextBoolean();
		this.affinity = Double.MAX_VALUE;
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
	@Override
	public int compareTo(AntiBody<boolean[]> o) {
		return affinity < o.getAffinity()?-1:0;
	}
	@Override
	public boolean equals(Object obj) {
		AntiBody<boolean[]> x = (AntiBody<boolean[]>) obj;
		return Arrays.equals(data, x.getData());
	}
	
	@Override
	public String toString() {
		return Double.toString(affinity);
	}
	
}

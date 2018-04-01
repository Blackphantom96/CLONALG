package Model.impl;

import Model.Abstraction.*;

public class ClonalgImpl extends Clonalg<boolean[]>{
	
	@Override
	public double[] affinity(AntiBody<boolean[]>[] ab, Antigen<boolean[]> sag){
		return null;
	}
	@Override
	public AntiBody<boolean[]> []  fillRandomMemory(AntiBody<boolean[]>[] ab, int x){
		return null;
	}

	@Override
	public AntiBody<boolean[]>[] select(AntiBody<boolean[]>[] ab, double[] aff, int n ){
		AntiBody<boolean[]>[] res = new AntiBody[n];
		for (int i=0; i<n ;i++) 
			res[i] = ab[i];
		return res;
	}
	@Override
	public AntiBody<boolean[]>[] clone(AntiBody<boolean[]>[] ab, double beta, double[] aff){
		AntiBody<boolean[]>[] res = new AntiBody[ab.length];
		for (int i=0; i<ab.length ;i++) 
			res[i] = ab[i].clone();
		return null;
	}
	@Override
	public void mutate(AntiBody<boolean[]>[] ab, double[] aff){
		
	}
	@Override
	public AntiBody<boolean[]>[] generate(int d, int L){
		return null;
	} 
	@Override
	public void replace (AntiBody<boolean[]>[] ab, AntiBody<boolean[]>[] ab1, double[][] affin){
		
	}
	@Override
	public double affinity(AntiBody<boolean[]> ab, Antigen<boolean[]> sag){
		return (Double) null;
	}
}

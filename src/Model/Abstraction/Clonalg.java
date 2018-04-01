package Model.Abstraction;

public abstract class Clonalg<E> {
	
	public void main(AntiBody<E>[] ab, Antigen<E>[] ag, int Ngen, int n, int d, int L, double beta, int M, int N) {
		AntiBody<E> [] abm = null ; //FIXME ver que se hace 
		AntiBody<E> [] abr = null ; //FIXME ver que se hace
		for(int i = 0; i<Ngen;i++) {
			double[][] affin = new double[M][N];
			AntiBody<E> [][] abn = null ;
			for(int j =0; j <M; j++) { //TODO Poner al antigeno y al anticuerpo .size()
				affin[j] = affinity(ab, ag[j]);
				abn[j] = select(ab, affin[j], n);
				AntiBody<E>[] C = clone(ab,beta, affin[j]);
				mutate(C,affin[j]);
				double[] affinP = affinity(C	, ag[j]);
				AntiBody<E> selected = select(C, affinP, 1)[0];
				if( affinity(selected,ag[j]) > affinity(abm[j],ag[j]) ) //TODO ver si m es el tamaño de la memoria o es M
					abm[j] = selected;
				AntiBody<E>[] abd = generate(d, L);
				replace(abr,abd,affin);
			}
		}
		
		
	}
	
	abstract void fillRandomMemory(AntiBody<E>[] ab, int x);
	abstract double[] affinity(AntiBody<E>[] ab, Antigen<E> sag);
	abstract AntiBody<E>[] select(AntiBody<E>[] ab, double[] aff, int n );
	abstract AntiBody<E>[] clone(AntiBody<E>[] ab, double beta, double[] aff);
	abstract void mutate(AntiBody<E>[] ab, double[] aff);
	abstract AntiBody<E>[] generate(int d, int L); 
	abstract void replace (AntiBody<E>[] ab, AntiBody<E>[] ab1, double[][] affin);
	abstract double affinity(AntiBody<E> ab, Antigen<E> sag);
	
}

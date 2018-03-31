package Model.Abstraction;

public abstract class Clonalg<E> {
	
	public void main(AntiBody<E> ab, Antigen<E> ag, int Ngen, int n, int d, int L, double beta, int M) {
		for(int i = 0; i<Ngen;i++) {
			double [] affi= new double[M];
			AntiBody<E>[] abn = null ; //FIXME inicializar la variable
			for(int j =0; j < M; j++) {
				affi[j] = affinity(ab, ag);
				abn[j] = select(ab, affi[j], n);
				
			}
		}
		
		
	}
	
	abstract double affinity(AntiBody<E> ab, Antigen<E> ag);
	abstract AntiBody<E> select(AntiBody<E> ab, double aff, int n );
	abstract AntiBody<E> clone(AntiBody<E> ab, double beta, double aff);
	abstract AntiBody<E> mutate(AntiBody<E> ab, double aff);
	abstract AntiBody<E> insert(AntiBody<E> ab, AntiBody<E>[] abs);
	abstract void generate(int d, int L); //FIXME revisar los tipos de d y L
	abstract void replace (AntiBody<E> ab, AntiBody<E> ab1, double[] aff); //FIXME revisar que returna replace, en el articulo de "de Castro"
	
	
}

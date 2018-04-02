package Model.Abstraction;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Clonalg<E> {

	public AntiBody<E>[] main(AntiBody<E>[] ab, Antigen<E>[] ag, int Ngen, int n, int d, int L, double beta) {
		AntiBody<E>[] abm = generate(ag.length, L);
		for(int g = 0; g< Ngen;g++) {
			for (int j = 0; j<ag.length;j++) {
				affinity(ab, ag[j]);
				Arrays.sort(ab);
				AntiBody<E>[] selected = select(ab, n);
				AntiBody<E>[] clone = clone(selected, beta, ab.length);
				mutate(clone);
				affinity(clone, ag[j]);
				Arrays.sort(clone);
				AntiBody<E> bestAB = select(clone,1)[0];
				if(bestAB.getAffinity() < abm[j].getAffinity() ) {
					abm[j] = bestAB;
					ArrayList<AntiBody<E>> temp = new ArrayList<>(Arrays.asList(ab));
					temp.add(bestAB);
					ab = new AntiBody[temp.size()];
					temp.toArray(ab);
				}
				replace(abm, ab, d, L);
			}
			
		}
		return abm;
	}

	public abstract double[] affinity(AntiBody<E>[] ab, Antigen<E> sag);

	/**
	 * @param ab
	 *            un arreglo de anticuerpos ORDENADO ASCENDENTEMENTE por su afinidad con el antigeno
	 *            en revision
	 * @param aff
	 * @param n
	 * @return
	 */

	public abstract AntiBody<E>[] select(AntiBody<E>[] ab, int n);

	public abstract AntiBody<E>[] clone(AntiBody<E>[] ab, double beta, int N);

	public abstract void mutate(AntiBody<E>[] ab);

	public abstract AntiBody<E>[] generate(int d, int L);

	public abstract void replace(AntiBody<E>[] memo, AntiBody<E>[] r, int d, int L);

	public abstract double calculateAffinity(AntiBody<E> ab, Antigen<E> sag);

}

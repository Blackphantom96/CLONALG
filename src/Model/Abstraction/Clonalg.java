package Model.Abstraction;

import java.util.*;

public abstract class Clonalg<E> {

	public AntiBody<E>[] main(AntiBody<E>[] ab, Antigen<E>[] ag, int Ngen, int n, int d, int L, double beta) {
		Random rand = new Random();
		d = Math.min(d, ab.length-ag.length);
		AntiBody<E>[] abm = new AntiBody[ag.length];
		boolean flag = true;
		for (int i = 0; i < ag.length; i++) {
			abm[i] = ab[rand.nextInt(ab.length)];
		}
		for (int g = 0; g < Ngen; g++) {
			System.out.println(g + " " + Arrays.toString(abm));
			flag = true;
			for (AntiBody<E> w : abm)
				if (w.getAffinity() != 0) {
					flag = false;
					break;
				}
			if (flag)
				return abm;
			for (int j = 0; j < ag.length; j++) {
				affinity(ab, ag[j]);
				Arrays.sort(ab);
				AntiBody<E>[] selected = select(ab, n);
				int affRank = 0;
				ArrayList<AntiBody<E>> muted = new ArrayList<AntiBody<E>>();
				for (AntiBody<E> antibody : selected) {
					affRank+=1;
					AntiBody<E>[] cloned1 = clone(antibody, beta, ab.length, affRank);
					for (AntiBody<E> antibody1 : cloned1) {
						mutate(antibody1, affRank);
						muted.add(antibody1);
					}
				}
				AntiBody<E>[] cloned = new AntiBody[muted.size()];
				muted.toArray(cloned);
				affinity(cloned, ag[j]);
				Arrays.sort(cloned);
				AntiBody<E> bestAB = cloned[0];
				//System.out.println(bestAB);
				if (bestAB.getAffinity() < abm[j].getAffinity()) {
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
	 *            un arreglo de anticuerpos ORDENADO ASCENDENTEMENTE por su afinidad
	 *            con el antigeno en revision
	 * @param aff
	 * @param n
	 * @return
	 */

	public abstract AntiBody<E>[] select(AntiBody<E>[] ab, int n);

	public abstract AntiBody<E>[] clone(AntiBody<E> ab, double beta, int N, int affRank);

	public abstract AntiBody<E> mutate(AntiBody<E> ab, int affRank);

	public abstract AntiBody<E>[] generate(int d, int L);

	public abstract void replace(AntiBody<E>[] memo, AntiBody<E>[] r, int d, int L);

	public abstract double calculateAffinity(AntiBody<E> ab, Antigen<E> sag);

}

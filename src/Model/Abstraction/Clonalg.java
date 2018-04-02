package Model.Abstraction;

import java.util.*;

public abstract class Clonalg<E> {

	public AntiBody<E>[] main(AntiBody<E>[] ab, Antigen<E>[] ag, int Ngen, int n, int d, int L, double beta) {
		Random rand = new Random();
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
				int affRank = 1;
				ArrayList<AntiBody<E>> muted = new ArrayList<AntiBody<E>>();
				for (int k = 0; k < n; k++) {
					AntiBody<E>[] cloned1 = clone(selected[k], beta, ab.length, affRank);
					for (int l = 0; l < cloned1.length; l++) {
						muted.add(mutate(cloned1[l], affRank));
					}
					affRank++;
				}
				AntiBody<E>[] cloned = new AntiBody[muted.size()];
				muted.toArray(cloned);
				affinity(cloned, ag[j]);
				Arrays.sort(cloned);
				System.out.println(Arrays.toString(cloned));
				AntiBody<E> bestAB = select(cloned, 1)[0];
				if (bestAB.getAffinity() < abm[j].getAffinity())
					abm[j] = bestAB;
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

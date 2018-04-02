package Model.impl;

import java.util.*;

import Model.Abstraction.*;

public class ClonalgImpl extends Clonalg<boolean[]> {

	public static final String DEFAULT_AFFINITY_FUNCTION = "hammingComplement";
	public static final double MUTATION_MULTIPLIER = 1;

	private interface AffinityFunction {
		public double calculate(AntiBody<boolean[]> ab, Antigen<boolean[]> ag);
	}

	static Map<String, AffinityFunction> functions;

	public ClonalgImpl() {
		generateAffinityFunctions();
	}

	public void generateAffinityFunctions() {
		functions = new HashMap<>();

		functions.put("hammingComplement", (ab, ag) -> {
			double r = 0.0;
			boolean abData[] = ab.getData();
			boolean agData[] = ag.getData();
			assert abData.length == agData.length;

			for (int i = 0; i < agData.length; i++) {
				r += (abData[i] == agData[i] ? 0 : 1);
			}
			return r;
		});
	}

	@Override
	public double[] affinity(AntiBody<boolean[]>[] ab, Antigen<boolean[]> sag) {
		int size = ab.length;
		double[] r = new double[size];
		for (int i = 0; i < size; i++) {
			r[i] = calculateAffinity(ab[i], sag);
		}
		return r;
	}

	@Override
	public AntiBody<boolean[]>[] select(AntiBody<boolean[]>[] ab, int n) {
		AntiBody<boolean[]>[] res = new AntiBody[n];
		for (int i = 0; i < n; i++) {
			res[i] = ab[i];
		}
		return res;
	}

	@Override
	public AntiBody<boolean[]>[] clone(AntiBody<boolean[]> ab, double beta, int N, int affinityRank) {
		ArrayList<AntiBody<boolean[]>> res = new ArrayList<>();
		int n = (int) Math.round(beta * N / affinityRank);
		for (int j = 0; j < n; j++) {
			res.add(new OCRAntiBody(clone(ab.getData()), ab.getAffinity()));
		}
		AntiBody<boolean[]>[] a = new AntiBody[res.size()];
		res.toArray(a);
		return a;
	}

	static Random rand = new Random();

	@Override
	public AntiBody<boolean[]> mutate(AntiBody<boolean[]> ab, int affRank) {
		int n = Math.min(affRank, ab.getData().length);
		Set<Integer> index = new TreeSet<>();
		while (index.size() != n) {
			int x = rand.nextInt(ab.getData().length);
			index.add(x);
		}
		for (int i : index)
			ab.getData()[i] = !ab.getData()[i];
		return ab;
	}

	@Override
	public AntiBody<boolean[]>[] generate(int d, int L) {
		AntiBody<boolean[]>[] res = new AntiBody[d];
		for (int i = 0; i < d; i++)
			res[i] = new OCRAntiBody(L);
		return res;
	}

	@Override
	public void replace(AntiBody<boolean[]>[] memo, AntiBody<boolean[]>[] r, int d, int L) {
		ArrayList<Integer> index = new ArrayList<>();
		if (d > 0) {
			for (int i = r.length - 1; i >= 0; i--) {
				if (d == 0)
					break;
				else if (Arrays.binarySearch(memo, r[i]) < 0) {
					d--;
					r[i] = null;
					index.add(i);
				}
			}
		}
		for (int i : index)
			r[i] = new OCRAntiBody(L);
	}

	@Override
	public double calculateAffinity(AntiBody<boolean[]> ab, Antigen<boolean[]> sag) {
		double r = functions.get(DEFAULT_AFFINITY_FUNCTION).calculate(ab, sag);
		ab.setAffinity(r);
		return r;
	}

	private boolean[] clone(boolean[] bs) {
		boolean[] res = new boolean[bs.length];
		for (int i = 0; i < bs.length; i++)
			res[i] = bs[i];
		return res;
	}

}

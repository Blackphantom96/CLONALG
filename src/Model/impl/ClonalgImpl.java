package Model.impl;

import java.util.*;

import Model.Abstraction.*;

public class ClonalgImpl extends Clonalg<boolean[]> {

	public static final String DEFAULT_AFFINITY_FUNCTION = "hammingComplement";
	public static final double MUTATION_MULTIPLIER = 1.0;

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
	public AntiBody<boolean[]>[] clone(AntiBody<boolean[]>[] ab, double beta, int N) {
		ArrayList<AntiBody<boolean[]>> res = new ArrayList<>();
		for (int i = 0; i < ab.length; i++) {
			int n = 0;
			for (int j = 1; j <= ab.length; j++) {
				n += Math.round((beta * N) / j);
			}
			for (int j = 0; j < n; j++) {
				res.add(new OCRAntiBody(clone(ab[i].getData()), ab[i].getAffinity()));
			}
		}
		AntiBody<boolean[]>[] a = new AntiBody[res.size()];
		res.toArray(a);
		// System.err.println(Arrays.toString(a));
		return a;
	}

	@Override
	public void mutate(AntiBody<boolean[]>[] ab) {

		for (int i = 0; i < ab.length; i++) {
			AntiBody<boolean[]> antiBody = ab[i];

			int numMutations = Math.min(antiBody.getData().length,
					(int) (MUTATION_MULTIPLIER * antiBody.getAffinity()));
			// System.err.println(numMutations);
			int dataLength = antiBody.getData().length;
			boolean data[] = antiBody.getData();
			int index = 0;
			Random rand = new Random();
			while (numMutations > 0) {
				index = rand.nextInt(ab[0].getData().length);
				data[index] = !data[index];
				numMutations--;
			}

		}

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
			for (int i = r.length-1; i >=0; i--) {
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

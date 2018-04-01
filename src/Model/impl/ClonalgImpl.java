package Model.impl;

import java.util.Map;

import Model.Abstraction.*;

public class ClonalgImpl extends Clonalg<boolean[]> {

	public static final String DEFAULT_AFFINITY_FUNCTION = "hammingComplement";
	
	private interface AffinityFunction {
		public double calculate(AntiBody<boolean[]> ab, Antigen<boolean[]> ag);
	}

	static Map<String, AffinityFunction> functions;

	public ClonalgImpl() {
		generateAffinityFunctions();
	}
	
	public void generateAffinityFunctions() {
		functions.put("hammingComplement", (ab, ag) -> {
			int r = 0;
			boolean abData[] = ab.getData();
			boolean agData[] = ag.getData();
			assert abData.length == agData.length;

			for (int i = 0; i < agData.length; i++) {
				r += (abData[i] == agData[i] ? 0 : 1);
			}
			return (double) r;
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
	public AntiBody<boolean[]>[] fillRandomMemory(AntiBody<boolean[]>[] ab, int x) {
		return null;
	}

	@Override
	public AntiBody<boolean[]>[] select(AntiBody<boolean[]>[] ab, double[] aff, int n) {
		AntiBody<boolean[]>[] res = new AntiBody[n];
		for (int i = 0; i < n; i++)
			res[i] = ab[i];
		return res;
	}

	@Override
	public AntiBody<boolean[]>[] clone(AntiBody<boolean[]>[] ab, double beta, double[] aff) {
		AntiBody<boolean[]>[] res = new AntiBody[ab.length];
		for (int i = 0; i < ab.length; i++)
			res[i] = ab[i].clone();
		return null;
	}

	@Override
	public void mutate(AntiBody<boolean[]>[] ab, double[] aff) {

	}

	@Override
	public AntiBody<boolean[]>[] generate(int d, int L) {
		return null;
	}

	@Override
	public void replace(AntiBody<boolean[]>[] ab, AntiBody<boolean[]>[] ab1, double[][] affin) {

	}

	@Override
	public double calculateAffinity(AntiBody<boolean[]> ab, Antigen<boolean[]> sag) {
		return functions.get(DEFAULT_AFFINITY_FUNCTION).calculate(ab, sag);
	}
}

package Model.Abstraction;


public interface AntiBody<E> extends Comparable<AntiBody<E>>{

	boolean[] getData();

	void setData(boolean[] data);

	double getAffinity();

	void setAffinity(double affinity);

}

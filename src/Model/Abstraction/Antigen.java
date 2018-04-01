package Model.Abstraction;

import java.io.Serializable;

public interface Antigen<E> extends Cloneable{

	boolean[] getData();

	void setData(boolean[] data);

	double getAffinity();

	void setAffinity(double affinity);
}

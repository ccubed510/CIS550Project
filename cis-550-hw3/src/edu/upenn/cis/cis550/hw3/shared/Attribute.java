package edu.upenn.cis.cis550.hw3.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Attribute implements IsSerializable, Comparable<Attribute> {
	public enum Type {IntType, StringType, DoubleType};
	
	public static Serializable getNewInstance(Type t, Serializable value) {
		switch (t) {
		case IntType:
			return Integer.valueOf(value.toString());
		case StringType:
			return new String(value.toString());
		case DoubleType:
			return Double.valueOf(value.toString());
		default:
			throw new RuntimeException("Unable to instantiate type");
		}
	}
	
	public static int compare(Type t, Object value1, Object value2) {
		switch (t) {
		case IntType:
			return Integer.valueOf(value1.toString()).compareTo(Integer.valueOf(value2.toString()));
		case StringType:
			return value1.toString().compareTo(value2.toString());
		case DoubleType:
			return Double.valueOf(value1.toString()).compareTo(Double.valueOf(value2.toString()));
		default:
			throw new RuntimeException("Unable to instantiate type");
		}
	}
	
	Type type;
	Serializable value;
	
	public Attribute() {
		type = Type.IntType;
		value = "";
	}
	
	public Attribute(Type t) {
		this.type = t;
		value = "";
	}
	
	public Attribute(Type t, Serializable value) {
		this.type = t;
		this.value = getNewInstance(type, value);
	}
	
	public Serializable getValue() {
		return value;
	}
	
	public Type getType() {
		return type;
	}

	@Override
	public int compareTo(Attribute value2) {
		return compare(type, value, value2.value);
	}
	
	public int compareValue(Serializable value2) {
		return compare(type, value, value2);
	}
	
	@Override
	public boolean equals(Object value2) {
		if (!(value2 instanceof Attribute))
			return false;
		
		return compareTo((Attribute)value2) == 0 && this.getClass().equals(value2.getClass());
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public String toString() {
		return this.value.toString();
	}
}

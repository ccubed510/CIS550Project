package edu.upenn.cis.cis550.hw3.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Tuple implements IsSerializable {
	Schema schema;
	List<Attribute> values;
	
	public Tuple() {
		
	}
	
//	public Tuple(Schema schema, List<Attribute> values) {
//		if (schema.size() != values.size())
//			throw new RuntimeException("Mismatched arity between schema and values");
//
//		this.schema = schema;
//		this.values = values;
//		
//		schema.check(values);
//	}
	
	public Tuple(Schema schema, List<Serializable> values) {
		if (schema.size() != values.size())
			throw new RuntimeException("Mismatched arity between schema and values");

		this.schema = schema;
		this.values = new ArrayList<Attribute>();
		
		for (int i = 0; i < schema.size(); i++) {
			this.values.add(new Attribute(schema.getType(i), values.get(i)));
		}
	}
	
	public Tuple(Schema schema, Serializable... values) {
		if (schema.size() != values.length)
			throw new RuntimeException("Mismatched arity between schema and values");

		this.schema = schema;
		this.values = new ArrayList<Attribute>();
		
		for (int i = 0; i < schema.size(); i++) {
			this.values.add(new Attribute(schema.getType(i), values[i]));
		}
	}
	
	public Attribute getValue(int inx) {
		return values.get(inx);
	}
	
	public Attribute getValue(String name) {
		int i = schema.getIndexOf(name);
		
		if (i < 0) {
			throw new RuntimeException("Unable to find " + name + " in schema " + schema);
		}
		return values.get(i);
	}
	
	public String getName(int inx) {
		return schema.getName(inx);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		
		boolean first = true;
		for (Attribute a: values) {
			if (first)
				first = false;
			else
				sb.append(",");
			
			sb.append(a.getValue().toString());
		}
		sb.append(")");
		return sb.toString();
	}

	public Schema getSchema() {
		return schema;
	}

	public List<Attribute> getValues() {
		return values;
	}
	
	
}

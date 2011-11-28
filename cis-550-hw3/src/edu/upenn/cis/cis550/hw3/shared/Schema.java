package edu.upenn.cis.cis550.hw3.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.view.client.ProvidesKey;

import edu.upenn.cis.cis550.hw3.client.widgets.PagedTable;

public class Schema implements IsSerializable {
	List<String> names;
	List<Attribute.Type> types;
	List<Integer> keys;
	
	public Schema() {
		names = new ArrayList<String>();
		types = new ArrayList<Attribute.Type>();
		keys = new ArrayList<Integer>();
	}
	
	public Schema(List<String> names, List<Attribute.Type> types) {
		if (names.size() != types.size())
			throw new RuntimeException("Mismatched arity between schema names and types");
		
		this.names = names;
		this.types = types;
		this.keys = new ArrayList<Integer>();
	}
	
	public Schema(List<String> names, List<Attribute.Type> types, List<String> keys) {
		if (names.size() != types.size())
			throw new RuntimeException("Mismatched arity between schema names and types");
		
		this.names = names;
		this.types = types;
		
		this.keys = new ArrayList<Integer>();
		for (String key: keys) {
			int pos = names.indexOf(key);
			if (pos < 0)
				throw new RuntimeException("Unable to find key " + key + " in schema " + names);
			this.keys.add(pos);
		}
	}
	
	public void addColumn(String name, Attribute.Type type, boolean isKey) {
		int pos = this.names.size();
		
		this.names.add(name);
		this.types.add(type);
		
		if (isKey)
			this.keys.add(pos);
	}
	
	public Attribute.Type getType(int column) {
		return this.types.get(column);
	}
	
	public String getName(int column) {
		return this.names.get(column);
	}

	public int getIndexOf(String name) {
		return this.names.indexOf(name);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		
		boolean first = true;
		for (int i = 0; i < names.size(); i++) {
			if (first)
				first = false;
			else
				sb.append(",");
			
			sb.append(names.get(i));
			if (keys.contains(i))
				sb.append("*");
			
			sb.append(":");
			sb.append(types.get(i).toString());
		}
		sb.append(")");
		return sb.toString();
	}
	
	public int size() {
		return this.names.size();
	}
	
	public void check(List<Attribute> values) {
		for (int i = 0; i < types.size(); i++)
			if (types.get(i) != values.get(i).getType())
				throw new RuntimeException("Mismatch between the and attribute for " + names.get(i));
	}
	
	public Tuple createTuple(List<Serializable> values) {
		return new Tuple(this, values);
	}

	public Tuple createTuple(Serializable... values) {
		return new Tuple(this, values);
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<Attribute.Type> getTypes() {
		return types;
	}

	public void setTypes(List<Attribute.Type> types) {
		this.types = types;
	}

	public List<Integer> getKeys() {
		return keys;
	}

	public void setKeys(List<Integer> keys) {
		this.keys = keys;
	}
	
	public ProvidesKey<Tuple> getKeyProvider() {
		ProvidesKey<Tuple> ret = new ProvidesKey<Tuple>() {

			public Object getKey(Tuple item) {
				if (item == null)
					return null;
				else if (keys.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					boolean first = true;
					for (int i = 0; i < names.size(); i++) {
						if (first)
							first = false;
						else
							sb.append("|");
						sb.append(item.getValue(i));
					}
					return sb.toString();
				} else {
					StringBuilder sb = new StringBuilder();
					boolean first = true;
					for (Integer key: keys) {
						if (first)
							first = false;
						else
							sb.append("|");
						sb.append(item.getValue(key));
					}
					return sb.toString();						
				}
			}
			
		};
		
		return ret;
	}

	public PagedTable<Tuple> getTable() {
		List<Tuple> relList = new ArrayList<Tuple>();
		
		List<TextColumn<Tuple>> columns = new ArrayList<TextColumn<Tuple>>(); 
		for (int i = 0; i < names.size(); i++) {
			final int j = i;
		
			columns.add(new TextColumn<Tuple>() {

				@Override
				public String getValue(Tuple object) {
					return object.getValue(j).getValue().toString();
				}
				
			});
		}
		
		PagedTable<Tuple> theTable = new PagedTable<Tuple>(20, false, false, relList, getKeyProvider(), names, columns);
		
		return theTable;
	}
}

package edu.upenn.cis.cis550.hw3.shared;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

import edu.upenn.cis.cis550.hw3.client.widgets.PagedTable;

public class Relation extends HashSet<Tuple> implements IsSerializable {
	private static final long serialVersionUID = 1L;
	
	String name;
	Schema schema;
	
	public Relation(String name, Schema schema) {
		this.name = name;
		this.schema = schema;
	}
	
	public Relation() {
		name = "";
		schema = new Schema();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
//		sb.append(name + schema.toString() + "\n");
		
		for (Tuple t: this) {
			sb.append(t.toString() + "\n");
		}
		
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public Schema getSchema() {
		return schema;
	}
	
	public List<Tuple> getTableAsList() {
		List<Tuple> relList = new ArrayList<Tuple>();
		relList.addAll(this);
		
		return relList;
	}
	
	public PagedTable<Tuple> getTableWidget() {
		PagedTable<Tuple> tab = schema.getTable();
		
		tab.addAll(getTableAsList());
		
		
		return tab;
	}
}

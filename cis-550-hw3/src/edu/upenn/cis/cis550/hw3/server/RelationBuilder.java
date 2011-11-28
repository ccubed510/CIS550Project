package edu.upenn.cis.cis550.hw3.server;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import edu.upenn.cis.cis550.hw3.shared.Attribute;
import edu.upenn.cis.cis550.hw3.shared.Relation;
import edu.upenn.cis.cis550.hw3.shared.Schema;
import edu.upenn.cis.cis550.hw3.shared.Tuple;

public class RelationBuilder {
	public static Relation createRelation(String name, ResultSet rs) throws SQLException {
		Schema schema = new Schema();

		ResultSetMetaData rsm = rs.getMetaData();
		
		int count = rsm.getColumnCount();
		
		for (int i = 1; i <= count; i++) {
			String colName = rsm.getColumnName(i);
			switch(rsm.getColumnType(i)) {
			case java.sql.Types.INTEGER:
				schema.addColumn(colName, Attribute.Type.IntType, false);
				break;
			case java.sql.Types.DOUBLE:
				schema.addColumn(colName, Attribute.Type.DoubleType, false);
				break;
			case java.sql.Types.FLOAT:
				schema.addColumn(colName, Attribute.Type.DoubleType, false);
				break;
			case java.sql.Types.REAL:
				schema.addColumn(colName, Attribute.Type.DoubleType, false);
				break;
			case java.sql.Types.CHAR:
				schema.addColumn(colName, Attribute.Type.StringType, false);
				break;
			case java.sql.Types.VARCHAR:
				schema.addColumn(colName, Attribute.Type.StringType, false);
				break;
			case java.sql.Types.DATE:
				schema.addColumn(colName, Attribute.Type.StringType, false);
				break;
			default:
				throw new RuntimeException("Database type not understood!");
			}
		}
		Relation ret = new Relation(name, schema);
		
		while (rs.next()) {
			Serializable[] ar = new Serializable[count];
			for (int i = 1; i <= count; i++) {
				switch(rsm.getColumnType(i)) {
				case java.sql.Types.INTEGER:
					ar[i-1] = rs.getInt(i);
					break;
				case java.sql.Types.DOUBLE:
					ar[i-1] = rs.getDouble(i);
					break;
				case java.sql.Types.FLOAT:
					ar[i-1] = rs.getFloat(i);
					break;
				case java.sql.Types.REAL:
					ar[i-1] = rs.getDouble(i);
					break;
				case java.sql.Types.CHAR:
					ar[i-1] = rs.getString(i);
					break;
				case java.sql.Types.VARCHAR:
					ar[i-1] = rs.getString(i);
					break;
				case java.sql.Types.DATE:
					ar[i-1] = rs.getString(i);
					break;
				default:
					throw new RuntimeException("Database type not understood!");
				}
			}
			Tuple t = new Tuple(schema, ar); 
			ret.add(t);
		}
		
		return ret;
	}
	
}

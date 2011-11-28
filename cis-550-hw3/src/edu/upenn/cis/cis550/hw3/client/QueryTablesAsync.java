package edu.upenn.cis.cis550.hw3.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.upenn.cis.cis550.hw3.shared.Relation;

public interface QueryTablesAsync {

	void getRelation(AsyncCallback<Relation> callback) throws IllegalArgumentException;

	void getImdb(AsyncCallback<Relation> callback);

	void getJoin(String movie, AsyncCallback<Relation> callback);

}

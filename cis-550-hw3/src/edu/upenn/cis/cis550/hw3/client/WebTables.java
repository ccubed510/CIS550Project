package edu.upenn.cis.cis550.hw3.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.upenn.cis.cis550.hw3.shared.Relation;

/**
 * This is the main class for generating Web table results for CIS 550 HW1
 * 
 * @author zives
 *
 */
public class WebTables implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side service.
	 */
	private final QueryTablesAsync queryService = GWT
			.create(QueryTables.class);

	/**
	 * This is the entry point method.
	 */

	@Override
	public void onModuleLoad() {
		Label l1 = new Label("Please click on a button below to see a table:");
		l1.addStyleName("instruction");
		Button rel = new Button("Sample relation from HW2");
		Button imdb = new Button("Original IMDB table");
		Button actors = new Button("Actors table");
		Button join = new Button("Show rejoined results");
		Label l2 = new Label("Movie name to show rejoined results for: ");
		final TextBox movie = new TextBox(); 
		
		// Create a panel in which we can place the controls, and add it
		// to the document root
		VerticalPanel thePanel = new VerticalPanel();
		RootPanel.get().add(thePanel);
		
		thePanel.add(l1);
		thePanel.add(new HTML("<p></p>"));	// Vertical spacer
		
		// Panel for a horizontal row of buttons
		HorizontalPanel buttons = new HorizontalPanel();
		thePanel.add(buttons);
		
		buttons.add(rel);
		buttons.add(imdb);
		buttons.add(actors);

		thePanel.add(new HTML("<p></p>"));	// Vertical spacer

		HorizontalPanel moviePanel = new HorizontalPanel();
		thePanel.add(moviePanel);
		moviePanel.add(l2);
		moviePanel.add(movie);
		moviePanel.add(join);
		
		// Add an *event handler* that responds when the user clicks
		// on the rel button.  When a click is encountered, call the
		// createRelationPopup() dialog.
		rel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				createRelationPopup();
			}
			
		});
		
		// Add an *event handler* that responds when the user clicks
		// on the join button.  When a click is encountered, check first
		// that the movie text box has some content.  If so, trigger
		// createJoinPopup() with its contents. Else show error dialog.
		join.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (movie.getText().isEmpty()) {
					Window.alert("Sorry, you must select a movie to see the join result");
				} else
					createJoinPopup(movie.getText());
			}
		});
		
		// TODO: add handlers for the actors and imdb buttons.
		// UNTIL THAT HAPPENS WE WILL DISABLE THEM.  REMOVE THESE TWO LINES WHEN READY.
		imdb.setEnabled(false);
		actors.setEnabled(false);
	}
	
	/**
	 * Creates a popup dialog box that asynchronously calls getRelation() and displays
	 * its results in a table control 
	 */
	private void createRelationPopup() {
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("CIS 550 Database - Query Results");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final HTML schemaLabel = new HTML();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<br><b>Relation schema:</b>"));
		dialogVPanel.add(schemaLabel);
		dialogVPanel.add(new HTML("<br><b>Returned data:</b>"));
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogBox.setWidget(dialogVPanel);
		
		final ScrollPanel scroller = new ScrollPanel();
		scroller.setHeight("400px");
		dialogVPanel.add(scroller);
		
		dialogVPanel.add(closeButton);

		// Here we are creating a dialog box to return the query results
		queryService.getRelation(new AsyncCallback<Relation>() {

			@Override
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				dialogBox
						.setText("Remote Procedure Call - Failure");
				serverResponseLabel
						.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML(SERVER_ERROR);
				dialogBox.show();
				dialogBox.center();
				
				closeButton.setFocus(true);
			}

			@Override
			public void onSuccess(Relation result) {
				// Add the relational schema to the output
				schemaLabel.setText(result.getSchema().toString());
				
				// Add the relation outputs to the output in a scrollable table
				scroller.add(result.getTableWidget());
				dialogBox.show();
				dialogBox.center();
				
			}
			
		});
		
		closeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
			
		});
	}

	/**
	 * TODO: create a popup dialog box that invokes the getJoin method
	 * with the movieName, and displays the results in a table control
	 * 
	 * @param movieName
	 */
	private void createJoinPopup(final String movieName) {
		
	}
	
}

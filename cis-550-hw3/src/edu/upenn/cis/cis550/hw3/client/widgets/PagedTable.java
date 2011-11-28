package edu.upenn.cis.cis550.hw3.client.widgets;

import java.util.Collection;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * Basic class for displaying a CellTable with a scroller
 * 
 * @author zives
 *
 * @param <T> type of cell element
 */
public class PagedTable<T> extends CellTable<T> {
	int page;
	ShowMorePagerPanel dataPager = new ShowMorePagerPanel();
	ListDataProvider<T> dataProvider = new ListDataProvider<T>();

	/**
	 * Create a new table
	 * 
	 * @param pageSize Default size of a page
	 * @param multiselect Allow the user to select multiple items?
	 * @param useChecks Add check boxes with the rows, for selection?
	 * @param dataList The actual data to use initially
	 * @param keyProvider Specification of the key
	 * @param columnNames List of column names
	 * @param columns List of column specifiers
	 */
	public PagedTable(int pageSize, boolean multiselect, boolean useChecks, List<T> dataList, ProvidesKey<T> keyProvider,
			List<String> columnNames, List<TextColumn<T>> columns) {
		super();
		
		setPageSize(pageSize);
		
		setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);//.BOUND_TO_SELECTION);
		setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		
		if (multiselect) {
			final MultiSelectionModel<T> seriesSelectionModel = new MultiSelectionModel<T>(keyProvider);
			
			if (useChecks) {
			    // Checkbox column. This table uses a checkbox column for selection.
			    Column<T, Boolean> checkColumn = new Column<T, Boolean>(
			        new CheckboxCell(true, false)) {
			      @Override
			      public Boolean getValue(T object) {
			        // Get the value from the selection model.
			        return seriesSelectionModel.isSelected(object);
			      }
			    };
			    addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
			    setColumnWidth(checkColumn, 10, Unit.PX);
				setSelectionModel(seriesSelectionModel, 
			      DefaultSelectionEventManager.<T> createCheckboxManager());
			} else {
				setSelectionModel(seriesSelectionModel);
			}
		} else {
			final SingleSelectionModel<T> seriesSelectionModel = new SingleSelectionModel<T>(keyProvider);
			setSelectionModel(seriesSelectionModel);
		}
		
		for (int i = 0; i < columnNames.size(); i++) {
			addColumn(columns.get(i), columnNames.get(i));
			addColumnStyleName(i, "seriesCol");
		}
		
		addColumnSortHandler(new ColumnSortEvent.ListHandler<T>(dataProvider.getList()) {
			@Override
			public void onColumnSort(ColumnSortEvent ev) {
				super.onColumnSort(ev);
				PagedTable.this.redraw();
			}
		});

//		setSelectionEnabled(true);
		dataPager.setDisplay(this);
		dataProvider.setList(dataList);
		setSize(dataList.size());
		dataProvider.addDataDisplay(this);
		dataProvider.flush();
		
	}
	
	public ShowMorePagerPanel getPager() {
		return dataPager;
	}
	
	@Override
	public int getRowCount() {
		int ret = dataProvider.getList().size();
		
		setPageStart(0);
		setPageSize(ret);
		setRowCount(ret);
		return ret;
	}
	
	public void setSize(int size) {
		setRowCount(size);
		setPageStart(0);
		setPageSize(size);
	}
	
	public List<T> getList() {
		return dataProvider.getList();
	}
	
	public void add(T item) {
		getList().add(item);
	}
	
	public void addAll(Collection<T> item) {
		getList().addAll(item);
	}
	
	public ListDataProvider<T> getProvider() {
		return dataProvider;
	}
}

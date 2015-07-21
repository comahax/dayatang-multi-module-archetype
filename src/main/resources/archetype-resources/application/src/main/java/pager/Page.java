#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pager;

import java.io.Serializable;

public final class Page implements Serializable {

	private static final long serialVersionUID = -7742988063989166270L;

	private static final int DEFAULT_PAGESIZE = 20;

	/** first page */
	private int firstPage = 1;

	/** last page */
	private int lastPage;

	/** next page */
	private int nextPage;

	/** prev page */
	private int prevPage;

	/** current page */
	private int currentPage;

	/** total page */
	private int totalPage;

	/** total count */
	private int rowCount;

	/** page size */
	private int pageSize = DEFAULT_PAGESIZE;

	/** exists next page */
	private boolean hasNext;

	/** exists prev page */
	private boolean hasPrev;

	/** exists first page */
	private boolean hasFirst;

	/** exists last page */
	private boolean hasLast;

	// 第一条记录的位置
	private int firstIndex;

	// 最后一条记录的位置
	private int lastIndex;

	public Page() {
	}

	public Page(int rowCount, int currentPage) {
		this(rowCount, DEFAULT_PAGESIZE, currentPage);
	}

	/** call this constructor method get instance */
	public Page(int rowCount, int pageSize, int currentPage) {
		this.rowCount = rowCount;
		this.currentPage = (currentPage < 0 ? 0 : currentPage);
		this.pageSize = (pageSize == 0 ? DEFAULT_PAGESIZE : pageSize);
		this.totalPage = this.rowCount % pageSize == 0 ? this.rowCount / pageSize : this.rowCount / pageSize + 1;
		this.firstIndex = this.currentPage * pageSize < this.rowCount ? this.currentPage * pageSize : this.rowCount ;
		this.lastIndex = (this.currentPage + 1) * pageSize < this.rowCount ? ((this.currentPage+1) * pageSize) : this.rowCount ;

		if (this.totalPage > 0) {
			this.hasFirst = true;
			this.firstPage = 1;
		}
		if (this.currentPage > 1) {
			this.hasPrev = true;
			this.prevPage = this.currentPage - 1;
		}
		if (this.totalPage > 0 && this.currentPage < this.totalPage) {
			this.hasNext = true;
			this.nextPage = this.currentPage + 1;
		}
		if (this.totalPage > 0) {
			this.hasLast = true;
			this.lastPage = this.totalPage;
		}
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	/** property firstPage getter method */
	public int getFirstPage() {
		return firstPage;
	}

	/** property lastPage getter method */
	public int getLastPage() {
		return lastPage;
	}

	/** property nextPage getter method */
	public int getNextPage() {
		return nextPage;
	}

	/** property prevPage getter method */
	public int getPrevPage() {
		return prevPage;
	}

	/** property currentPage getter method */
	public int getCurrentPage() {
		return currentPage;
	}

	/** property totalPage getter method */
	public int getTotalPage() {
		return totalPage;
	}

	/** property rowCount getter method */
	public int getRowCount() {
		return rowCount;
	}

	/** property pageSize getter method */
	public int getPageSize() {
		return pageSize;
	}

	/** property hasNext getter method */
	public boolean isHasNext() {
		return hasNext;
	}

	/** property hasPrev getter method */
	public boolean isHasPrev() {
		return hasPrev;
	}

	/** property hasFirst getter method */
	public boolean isHasFirst() {
		return hasFirst;
	}

	/** property hasLast getter method */
	public boolean isHasLast() {
		return hasLast;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public void setHasPrev(boolean hasPrev) {
		this.hasPrev = hasPrev;
	}

	public void setHasFirst(boolean hasFirst) {
		this.hasFirst = hasFirst;
	}

	public void setHasLast(boolean hasLast) {
		this.hasLast = hasLast;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

}

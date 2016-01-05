package uk.bl.wa.shine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private long currentPage = 1;
    private long totalItems;
    private int itemsPerPage;
    private int maxNumberOfLinksOnPage; // i.e 10 links per page
    private long maxViewablePages; // i.e 50 pages max

    private long totalPages = 0;
    
    DecimalFormat idf = new DecimalFormat("##,###");

    // The paging must limit the depth to which we allow the user to descend, as otherwise Solr starts to fall over. 
    // The Pager should only show ten page numbers at once, and should only allow 50 pages to be viewed. 
    // On the last page of results, you should instead get a warning that you can't page any deeper.

    public Pagination(int itemsPerPage, int maxNumberOfLinksOnPage, long maxViewablePages) {
    	this.itemsPerPage = itemsPerPage;
    	this.maxNumberOfLinksOnPage = maxNumberOfLinksOnPage; // 10
    	this.maxViewablePages = maxViewablePages; // 500
        if (this.itemsPerPage < 1) {
            this.itemsPerPage = 1;
        }
    }
    
    public void update(long totalItems, long pageNo) {

        this.totalItems = totalItems;
        // Place hard upper limit on paging
        this.totalPages = this.totalItems / this.itemsPerPage;
        if (this.totalItems % this.itemsPerPage > 0) {
            this.totalPages = this.totalPages + 1;
        }
        if (this.totalPages > maxViewablePages) this.totalPages = maxViewablePages;
        
        this.currentPage = pageNo;
    }

    public long getCurrentPage() {
        return currentPage;
    }
   
    public void setCurrentPage(long currentPage) {
        if (currentPage > totalPages) {
            currentPage = totalPages;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        this.currentPage = currentPage;
    }

    public long getTotalPages() {
        return this.totalPages;
    }

    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    public boolean hasNextPage() {
        return currentPage < totalPages;
    }

    public long getPreviousPage() {
        if (hasPreviousPage()) {
            return currentPage - 1;
        } else {
            return 1;
        }
    }

    public long getNextPage() {
        if (hasNextPage()) {
            return currentPage + 1;
        } else {
            return totalPages;
        }
    }

    public long getStartIndex() {
        return (this.currentPage - 1) * this.itemsPerPage + 1;
    }
    
    public long getNextIndex(long currentIndex) {
    	return getStartIndex()+currentIndex;
    }

    public long getEndIndex() {
        long endIndex = this.currentPage * this.itemsPerPage;
        if (endIndex > this.totalItems) {
            endIndex = this.totalItems;
        }
        return endIndex;
    }

    public long getTotalItems() {
        return totalItems;
    }
    
    public List<Long> getPagesList() {
    	long radius = this.maxNumberOfLinksOnPage / 2;
        List<Long> pageList = new ArrayList<Long>();
        
        long startPage = getCurrentPage() - radius;
        if (startPage < 1) {
            startPage = 1;
        }
        
        long endPage = getCurrentPage() + radius;
        if (endPage > getTotalPages()) {
            endPage = getTotalPages();
        }
        
        for (long page = startPage; page <= endPage; page++) {
            pageList.add(page);
        }
        

        return pageList;
    }
    
	public String getDisplayXtoYofZ(String to, String of) {
        long first = this.getStartIndex();
        long last = this.getEndIndex();
        long total = this.getTotalItems();

        String displayText = idf.format(first)+to+idf.format(last)+of+idf.format(total);
        if (first > total) {
        	displayText = " not found";
        }
        return displayText;
	}

	public int getMaxNumberOfLinksOnPage() {
		return maxNumberOfLinksOnPage;
	}

	public long getMaxViewablePages() {
		return maxViewablePages;
	}
	
	public boolean hasMaxViewablePagedReached() {
		return (this.currentPage == this.maxViewablePages);
	}
	
	
}

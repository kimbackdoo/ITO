package kr.co.metasoft.test.common.dto;

public class PageRequestDto {

    public static final String LIMIT_OFFSET_QUERY = " LIMIT #{pageRequestDto.limit} OFFSET #{pageRequestDto.offset} ";

    public static final String COUNT_START_QUERY = " SELECT COUNT(*) AS total_rows FROM ( ";

    public static final String COUNT_END_QUERY = " ) COUNT_QUERY ";

    public static final String SORT_START_QUERY = " SELECT * FROM ( ";

    public static final String SORT_END_QUERY = ""
            + " ) SORT_QUERY "
            + " <if test='pageRequestDto.sortBy != null and pageRequestDto.sortBy != \"\" and pageRequestDto.descending != null'> "
            + "     ORDER BY ${pageRequestDto.sortBy} "
            + "     <choose> "
            + "         <when test='pageRequestDto.descending'> DESC </when> "
            + "         <otherwise> ASC </otherwise> "
            + "     </choose> "
            + " </if> ";

    private Integer page = 1;

    private Integer rowSize = 10;

    private Integer pageSize = 10;

    private String sortBy = null;

    private Boolean descending = false;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if (page == null) {
            page = 1;
        }
        this.page = (page < 1) ? 1 : page;
    }

    public Integer getRowSize() {
        return rowSize;
    }

    public void setRowSize(Integer rowSize) {
        if (rowSize == null) {
            rowSize = 10;
        }
        this.rowSize = (rowSize < 1) ? 10 : rowSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            pageSize = 10;
        }
        this.pageSize = (pageSize < 1) ? 10 : pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Integer getLimit() {
        return rowSize;
    }

    public Integer getOffset() {
        return (page - 1) * rowSize;
    }

    public Integer getRowStart() {
        return ((page - 1) * rowSize) + 1;
    }

    public Integer getRowEnd() {
        return ((page - 1) * rowSize) + rowSize;
    }

}

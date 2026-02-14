package com.api.ems.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageDto<T> {
    private List<T> content;

    private int page;
    private int size;

    private Long totalElements;
    private int totalPages;

    private boolean first;
    private boolean last;
}

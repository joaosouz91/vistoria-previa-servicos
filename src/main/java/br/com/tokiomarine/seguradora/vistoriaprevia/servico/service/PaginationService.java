package br.com.tokiomarine.seguradora.vistoriaprevia.servico.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationService<T> {

    public Page<T> listToPageImpl(Pageable pageable, List<T> list) {
        if(pageable == null) return new PageImpl<T>(list);
        int total = list.size();
        int start = (int) pageable.getOffset(); //offset = size*page
        int end = Math.min((start + pageable.getPageSize()), total); // ponteiro + qtdd de paginas não podem ser maiores que o total de reg
        if (start <= end) list = list.subList(start, end);
        return new PageImpl<T>(list, pageable, total);
    }

    public Page<T> listToPageImpl(Integer size, Integer pageIndex, List<T> list) {
        Pageable pageable = null;
        if(pageIndex != null && size != null) pageable = PageRequest.of(pageIndex, size);
        return listToPageImpl(pageable, list);
    }
}

package com.atpco.audit.mapper.share;

import com.atpco.audit.model.CurrencyRegulation;
import java.util.List;

/**
 * Created by T440 on 2019/2/8.
 */
public interface CurrencyRegulationMapper {

    List<String> queryCurrency();

    List<CurrencyRegulation> queryRegulations();

    CurrencyRegulation queryRegulation(String currencyCode);
}

package com.envirtual.esf.ReportPages;

import java.util.List;

/**
 * Created by Salako on 28/04/2015.
 */
public interface ReportPageObjects {

    List<String> journeyList();

    List<String> searchTermAnalysisTableNames();

    List<String> productBasicsTableNames();

    int noOfLeftNavLinks();
}

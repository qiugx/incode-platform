package org.incode.example.document.demo.usage.fixture.seed;

import org.incode.example.docrendering.freemarker.fixture.RenderingStrategyFSForFreemarker;
import org.incode.example.docrendering.stringinterpolator.fixture.RenderingStrategyFSForStringInterpolator;
import org.incode.example.docrendering.stringinterpolator.fixture.RenderingStrategyFSForStringInterpolatorCaptureUrl;
import org.incode.example.docrendering.stringinterpolator.fixture.RenderingStrategyFSForStringInterpolatorPreviewAndCaptureUrl;
import org.incode.example.docrendering.xdocreport.fixture.RenderingStrategyFSForXDocReportToDocx;
import org.incode.example.docrendering.xdocreport.fixture.RenderingStrategyFSForXDocReportToPdf;
import org.incode.example.document.fixture.DocumentTemplateFSAbstract;

public class RenderingStrategy_create6 extends DocumentTemplateFSAbstract {

    public static final String REF_SIPC = RenderingStrategyFSForStringInterpolatorPreviewAndCaptureUrl.REF;
    public static final String REF_SINC = RenderingStrategyFSForStringInterpolatorCaptureUrl.REF;
    public static final String REF_SI = RenderingStrategyFSForStringInterpolator.REF;
    public static final String REF_FMK = RenderingStrategyFSForFreemarker.REF;
    public static final String REF_XDP = RenderingStrategyFSForXDocReportToPdf.REF;
    public static final String REF_XDD = RenderingStrategyFSForXDocReportToDocx.REF;


    @Override
    protected void execute(final ExecutionContext executionContext) {

        // prereqs

        executionContext.executeChild(this, new RenderingStrategyFSForStringInterpolatorPreviewAndCaptureUrl());
        executionContext.executeChild(this, new RenderingStrategyFSForStringInterpolatorCaptureUrl());
        executionContext.executeChild(this, new RenderingStrategyFSForStringInterpolator());
        executionContext.executeChild(this, new RenderingStrategyFSForFreemarker());
        executionContext.executeChild(this, new RenderingStrategyFSForXDocReportToPdf());
        executionContext.executeChild(this, new RenderingStrategyFSForXDocReportToDocx());

    }


}

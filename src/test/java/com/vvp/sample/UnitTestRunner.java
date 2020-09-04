package com.vvp.sample;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.netty.FeatureServer;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;

@ActiveProfiles("test")
public class UnitTestRunner {
    /**
     * Number of parallel threads.
     */
    private static final int NUMBER_OF_PARALLEL_THREADS = 5;
    /**
     * Mock server port.
     */
    private static final int SERVER_PORT = 9001;
    /**
     * Mock server.
     */
    private static FeatureServer server;

    /**
     * Start embedded mock application server using mock details.
     * @throws Exception exception thrown during embedded server starting
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        System.setProperty("karate.env", "test");
        server = FeatureServer.start(ResourceUtils.getFile("classpath:features/account_mock.feature"),
                SERVER_PORT, false, null);
    }

    /**
     * Stop embedded mock application server.
     */
    @AfterClass
    public static void afterClass() {
        server.stop();
    }

    /**
     * Run in NUMBER_OF_PARALLEL_THREADS parallel threads tests.
     */
    @Test
    public void parallelRunTest() {
        Results results = Runner.path("classpath:features/account.feature")
                .tags("~@ignore").parallel(NUMBER_OF_PARALLEL_THREADS);
        generateReport(results.getReportDir());
        if (results.getFailCount() > 0) {
            Assert.fail();
        }
    }

    /**
     * Generate rich format html report with unit tests results.
     * @param testResultsLocation test results location
     */
    private void generateReport(final String testResultsLocation) {
        Collection<File> jsonFiles = FileUtils.listFiles(
                new File(testResultsLocation), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(
                new File("target/output/testing-reports/unit"), "account");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}

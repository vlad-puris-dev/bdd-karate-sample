package com.vvp.sample;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ActiveProfiles("test")
public class IntegrationTestRunner {
    /**
     * Number of parallel threads.
     */
    private static final int NUMBER_OF_PARALLEL_THREADS = 5;
    /**
     * Start embedded application server using integration test application properties.
     */
    @BeforeClass
    public static void beforeClass() {
        String[] args = {};
        System.setProperty("karate.env", "integration-test");
        System.setProperty("spring.config.location", "classpath:/application-integration-test.yml");
        SpringApplication.run(AccountApplication.class, args);
    }

    /**
     * Run in NUMBER_OF_PARALLEL_THREADS parallel threads tests.
     */
    @Test
    public void parallelRunTest() {
        Results results = Runner.path("classpath:features/account_integration.feature")
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
    public static void generateReport(final String testResultsLocation) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(testResultsLocation), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(
                new File("target/output/testing-reports/integration"), "account");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}

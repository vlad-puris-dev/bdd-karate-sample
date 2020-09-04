function fn() {
    var config = {
        baseUrl: 'http://localhost:9000'    
    };
    karate.configure('connectTimeout', 10000);
    karate.configure('readTimeout', 10000);
    karate.configure('logPrettyRequest', false);
    karate.configure('logPrettyResponse', false);
    karate.configure('printEnabled', false);
    karate.configure('report', { showLog: false, showAllSteps: true });
    return config;
}
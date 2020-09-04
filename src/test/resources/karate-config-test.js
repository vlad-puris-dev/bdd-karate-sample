function fn() {
    var config = {
        baseUrl: 'http://localhost:9001'	
    };
    karate.configure('connectTimeout', 5000);
    karate.configure('readTimeout', 5000);
    karate.configure('logPrettyRequest', false);
    karate.configure('logPrettyResponse', false);
    karate.configure('printEnabled', false);
    karate.configure('report', { showLog: false, showAllSteps: true });
    return config;
}
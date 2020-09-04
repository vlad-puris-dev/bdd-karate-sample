Feature: stateful mock server

    Background:

    Scenario: pathMatches('/wrong') && methodIs('get')
        * def responseStatus = 404

    Scenario: pathMatches('/error') && methodIs('get')
        * def responseStatus = 500

    Scenario: pathMatches('/v1/accounts/0123456789') && methodIs('get')
        * def responseStatus = 200
        * def response = read('../data/unitAccountResponse.json')

    Scenario: pathMatches('/v1/accounts') && methodIs('get')
        * def responseStatus = 200
        * def response = read('../data/unitAccountsResponse.json')
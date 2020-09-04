Feature: Unit tests

    Background:
        * url baseUrl

    Scenario: Testing an internal server error - 500
        Given path 'error'
        When method GET
        Then status 500

    Scenario: Testing exact response of account GET endpoint read json
        Given path 'v1/accounts/0123456789'
        When method GET
        Then status 200
        And match response == read('../data/unitAccountResponse.json')

    Scenario: Testing exact response of accounts GET endpoint read json
        Given path 'v1/accounts'
        When method GET
        Then status 200
        And match response == read('../data/unitAccountsResponse.json')

    Scenario: Testing exact response of account GET endpoint for {accountId: '0123456789'}
        Given path 'v1/accounts/0123456789'
        When method GET
        Then status 200
        * set expected_account
        | path             | value        |
        | accountId        | '0123456789' |
        | accountType      | 'debit'      |
        | accountOpenDate  | '01/01/2020' |
        | accountCloseDate | '31/12/2020' |
        And match response.accounts contains expected_account

    Scenario: Testing exact response of accounts GET endpoint
        Given path 'v1/accounts'
        When method GET
        Then status 200
        * set expected_accounts
        | path             | value        |
        | accountId        | '0123456789' |
        | accountType      | 'debit'      |
        | accountOpenDate  | '01/01/2020' |
        | accountCloseDate | '31/12/2020' |
        And match response.accounts contains expected_accounts

    Scenario: Testing that account GET response contains specific field
        Given path 'v1/accounts/0123456789'
        When method GET
        Then status 200
        And match $ contains {accountId:"0123456789"}

    Scenario: Testing that accounts GET response contains specific field
        Given path 'v1/accounts'
        When method GET
        Then status 200
        And match $ contains {accountId:null}

    Scenario: Testing that account GET response using markers
        Given path 'v1/accounts/0123456789'
        When method GET
        Then status 200
        And match $ contains {accountId:"#notnull"}

    Scenario: Testing that account GET response using markers
        Given path 'v1/accounts'
        When method GET
        Then status 200
        And match $ contains {accountId:"#null"}

    Scenario: Testing conversion from JSON to POJO
        * def accountJson = read('../data/account.json')
        * def accountPojo = karate.toBean(accountJson, 'com.vvp.sample.model.Account')
        * assert accountPojo.accountId == '0123456789'

    Scenario: Testing conversion from JSON to POJO
        * def accountResponseJson = read('../data/unitAccountResponse.json')
        * def accountResponsePojo = karate.toBean(accountResponseJson, 'com.vvp.sample.model.AccountResponse')
        * assert accountResponsePojo.accountId == '0123456789'
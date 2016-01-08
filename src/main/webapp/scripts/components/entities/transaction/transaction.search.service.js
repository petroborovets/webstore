'use strict';

angular.module('webstoreApp')
    .factory('TransactionSearch', function ($resource) {
        return $resource('api/_search/transactions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

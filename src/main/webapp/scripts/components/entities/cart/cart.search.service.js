'use strict';

angular.module('webstoreApp')
    .factory('CartSearch', function ($resource) {
        return $resource('api/_search/carts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

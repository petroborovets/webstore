'use strict';

angular.module('webstoreApp')
    .factory('RatingSearch', function ($resource) {
        return $resource('api/_search/ratings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

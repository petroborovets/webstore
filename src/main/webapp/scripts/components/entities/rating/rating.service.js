'use strict';

angular.module('webstoreApp')
    .factory('Rating', function ($resource, DateUtils) {
        return $resource('api/ratings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });

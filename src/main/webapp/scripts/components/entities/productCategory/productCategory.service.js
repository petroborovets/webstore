'use strict';

angular.module('webstoreApp')
    .factory('ProductCategory', function ($resource, DateUtils) {
        return $resource('api/productCategorys/:id', {}, {
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

'use strict';

angular.module('webstoreApp')
    .factory('ProductCategorySearch', function ($resource) {
        return $resource('api/_search/productCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

'use strict';

angular.module('webstoreApp')
    .controller('ProductCategoryDetailController', function ($scope, $rootScope, $stateParams, entity, ProductCategory) {
        $scope.productCategory = entity;
        $scope.load = function (id) {
            ProductCategory.get({id: id}, function(result) {
                $scope.productCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('webstoreApp:productCategoryUpdate', function(event, result) {
            $scope.productCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });

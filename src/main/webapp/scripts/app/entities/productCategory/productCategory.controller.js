'use strict';

angular.module('webstoreApp')
    .controller('ProductCategoryController', function ($scope, $state, ProductCategory, ProductCategorySearch) {

        $scope.productCategorys = [];
        $scope.loadAll = function() {
            ProductCategory.query(function(result) {
               $scope.productCategorys = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ProductCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.productCategorys = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.productCategory = {
                name: null,
                id: null
            };
        };
    });

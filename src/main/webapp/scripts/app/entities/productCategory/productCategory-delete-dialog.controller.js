'use strict';

angular.module('webstoreApp')
	.controller('ProductCategoryDeleteController', function($scope, $uibModalInstance, entity, ProductCategory) {

        $scope.productCategory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProductCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });

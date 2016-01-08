'use strict';

angular.module('webstoreApp').controller('ProductCategoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductCategory',
        function($scope, $stateParams, $uibModalInstance, entity, ProductCategory) {

        $scope.productCategory = entity;
        $scope.load = function(id) {
            ProductCategory.get({id : id}, function(result) {
                $scope.productCategory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('webstoreApp:productCategoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.productCategory.id != null) {
                ProductCategory.update($scope.productCategory, onSaveSuccess, onSaveError);
            } else {
                ProductCategory.save($scope.productCategory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);

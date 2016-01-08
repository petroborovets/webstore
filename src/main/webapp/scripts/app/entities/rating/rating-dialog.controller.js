'use strict';

angular.module('webstoreApp').controller('RatingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rating', 'User', 'Product',
        function($scope, $stateParams, $uibModalInstance, entity, Rating, User, Product) {

        $scope.rating = entity;
        $scope.users = User.query();
        $scope.products = Product.query();
        $scope.load = function(id) {
            Rating.get({id : id}, function(result) {
                $scope.rating = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('webstoreApp:ratingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.rating.id != null) {
                Rating.update($scope.rating, onSaveSuccess, onSaveError);
            } else {
                Rating.save($scope.rating, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);

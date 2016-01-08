'use strict';

angular.module('webstoreApp').controller('CommentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Comment', 'User', 'Product',
        function($scope, $stateParams, $uibModalInstance, entity, Comment, User, Product) {

        $scope.comment = entity;
        $scope.users = User.query();
        $scope.products = Product.query();
        $scope.load = function(id) {
            Comment.get({id : id}, function(result) {
                $scope.comment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('webstoreApp:commentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.comment.id != null) {
                Comment.update($scope.comment, onSaveSuccess, onSaveError);
            } else {
                Comment.save($scope.comment, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);

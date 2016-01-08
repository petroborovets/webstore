'use strict';

angular.module('webstoreApp')
	.controller('RatingDeleteController', function($scope, $uibModalInstance, entity, Rating) {

        $scope.rating = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Rating.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });

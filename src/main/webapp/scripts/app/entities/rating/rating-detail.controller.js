'use strict';

angular.module('webstoreApp')
    .controller('RatingDetailController', function ($scope, $rootScope, $stateParams, entity, Rating, User, Product) {
        $scope.rating = entity;
        $scope.load = function (id) {
            Rating.get({id: id}, function(result) {
                $scope.rating = result;
            });
        };
        var unsubscribe = $rootScope.$on('webstoreApp:ratingUpdate', function(event, result) {
            $scope.rating = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });

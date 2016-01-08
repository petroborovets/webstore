'use strict';

angular.module('webstoreApp')
    .controller('CommentDetailController', function ($scope, $rootScope, $stateParams, entity, Comment, User, Product) {
        $scope.comment = entity;
        $scope.load = function (id) {
            Comment.get({id: id}, function(result) {
                $scope.comment = result;
            });
        };
        var unsubscribe = $rootScope.$on('webstoreApp:commentUpdate', function(event, result) {
            $scope.comment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });

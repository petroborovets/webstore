'use strict';

angular.module('webstoreApp')
    .controller('TransactionDetailController', function ($scope, $rootScope, $stateParams, entity, Transaction, User, Product) {
        $scope.transaction = entity;
        $scope.load = function (id) {
            Transaction.get({id: id}, function(result) {
                $scope.transaction = result;
            });
        };
        var unsubscribe = $rootScope.$on('webstoreApp:transactionUpdate', function(event, result) {
            $scope.transaction = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });

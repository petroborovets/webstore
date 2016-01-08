'use strict';

angular.module('webstoreApp')
    .controller('RatingController', function ($scope, $state, Rating, RatingSearch) {

        $scope.ratings = [];
        $scope.loadAll = function() {
            Rating.query(function(result) {
               $scope.ratings = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            RatingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.ratings = result;
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
            $scope.rating = {
                value: null,
                id: null
            };
        };
    });

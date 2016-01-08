'use strict';

angular.module('webstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rating', {
                parent: 'entity',
                url: '/ratings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'webstoreApp.rating.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rating/ratings.html',
                        controller: 'RatingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rating');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rating.detail', {
                parent: 'entity',
                url: '/rating/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'webstoreApp.rating.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rating/rating-detail.html',
                        controller: 'RatingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rating');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Rating', function($stateParams, Rating) {
                        return Rating.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rating.new', {
                parent: 'rating',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rating/rating-dialog.html',
                        controller: 'RatingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    value: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rating', null, { reload: true });
                    }, function() {
                        $state.go('rating');
                    })
                }]
            })
            .state('rating.edit', {
                parent: 'rating',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rating/rating-dialog.html',
                        controller: 'RatingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Rating', function(Rating) {
                                return Rating.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rating', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rating.delete', {
                parent: 'rating',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/rating/rating-delete-dialog.html',
                        controller: 'RatingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Rating', function(Rating) {
                                return Rating.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rating', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

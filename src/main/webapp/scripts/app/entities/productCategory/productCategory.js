'use strict';

angular.module('webstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productCategory', {
                parent: 'entity',
                url: '/productCategorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'webstoreApp.productCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productCategory/productCategorys.html',
                        controller: 'ProductCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('productCategory.detail', {
                parent: 'entity',
                url: '/productCategory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'webstoreApp.productCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productCategory/productCategory-detail.html',
                        controller: 'ProductCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProductCategory', function($stateParams, ProductCategory) {
                        return ProductCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('productCategory.new', {
                parent: 'productCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productCategory/productCategory-dialog.html',
                        controller: 'ProductCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('productCategory', null, { reload: true });
                    }, function() {
                        $state.go('productCategory');
                    })
                }]
            })
            .state('productCategory.edit', {
                parent: 'productCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productCategory/productCategory-dialog.html',
                        controller: 'ProductCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProductCategory', function(ProductCategory) {
                                return ProductCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('productCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('productCategory.delete', {
                parent: 'productCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productCategory/productCategory-delete-dialog.html',
                        controller: 'ProductCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProductCategory', function(ProductCategory) {
                                return ProductCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('productCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

'use strict';

describe('Controller Tests', function() {

    describe('Rating Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRating, MockUser, MockProduct;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRating = jasmine.createSpy('MockRating');
            MockUser = jasmine.createSpy('MockUser');
            MockProduct = jasmine.createSpy('MockProduct');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Rating': MockRating,
                'User': MockUser,
                'Product': MockProduct
            };
            createController = function() {
                $injector.get('$controller')("RatingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'webstoreApp:ratingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

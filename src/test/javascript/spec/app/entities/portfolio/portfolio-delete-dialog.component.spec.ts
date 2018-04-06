/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { StockTrackerTestModule } from '../../../test.module';
import { PortfolioDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/portfolio/portfolio-delete-dialog.component';
import { PortfolioService } from '../../../../../../main/webapp/app/entities/portfolio/portfolio.service';

describe('Component Tests', () => {

    describe('Portfolio Management Delete Component', () => {
        let comp: PortfolioDeleteDialogComponent;
        let fixture: ComponentFixture<PortfolioDeleteDialogComponent>;
        let service: PortfolioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [StockTrackerTestModule],
                declarations: [PortfolioDeleteDialogComponent],
                providers: [
                    PortfolioService
                ]
            })
            .overrideTemplate(PortfolioDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PortfolioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PortfolioService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

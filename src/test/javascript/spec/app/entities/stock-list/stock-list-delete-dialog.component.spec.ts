/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { StockTrackerTestModule } from '../../../test.module';
import { StockListDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/stock-list/stock-list-delete-dialog.component';
import { StockListService } from '../../../../../../main/webapp/app/entities/stock-list/stock-list.service';

describe('Component Tests', () => {

    describe('StockList Management Delete Component', () => {
        let comp: StockListDeleteDialogComponent;
        let fixture: ComponentFixture<StockListDeleteDialogComponent>;
        let service: StockListService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [StockTrackerTestModule],
                declarations: [StockListDeleteDialogComponent],
                providers: [
                    StockListService
                ]
            })
            .overrideTemplate(StockListDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StockListDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockListService);
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

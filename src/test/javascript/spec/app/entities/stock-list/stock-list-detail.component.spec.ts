/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { StockTrackerTestModule } from '../../../test.module';
import { StockListDetailComponent } from '../../../../../../main/webapp/app/entities/stock-list/stock-list-detail.component';
import { StockListService } from '../../../../../../main/webapp/app/entities/stock-list/stock-list.service';
import { StockList } from '../../../../../../main/webapp/app/entities/stock-list/stock-list.model';

describe('Component Tests', () => {

    describe('StockList Management Detail Component', () => {
        let comp: StockListDetailComponent;
        let fixture: ComponentFixture<StockListDetailComponent>;
        let service: StockListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [StockTrackerTestModule],
                declarations: [StockListDetailComponent],
                providers: [
                    StockListService
                ]
            })
            .overrideTemplate(StockListDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StockListDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new StockList(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.stockList).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

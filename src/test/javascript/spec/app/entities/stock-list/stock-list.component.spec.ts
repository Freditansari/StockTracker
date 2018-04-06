/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StockTrackerTestModule } from '../../../test.module';
import { StockListComponent } from '../../../../../../main/webapp/app/entities/stock-list/stock-list.component';
import { StockListService } from '../../../../../../main/webapp/app/entities/stock-list/stock-list.service';
import { StockList } from '../../../../../../main/webapp/app/entities/stock-list/stock-list.model';

describe('Component Tests', () => {

    describe('StockList Management Component', () => {
        let comp: StockListComponent;
        let fixture: ComponentFixture<StockListComponent>;
        let service: StockListService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [StockTrackerTestModule],
                declarations: [StockListComponent],
                providers: [
                    StockListService
                ]
            })
            .overrideTemplate(StockListComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StockListComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockListService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new StockList(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.stockLists[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { StockTrackerTestModule } from '../../../test.module';
import { PortfolioDetailComponent } from '../../../../../../main/webapp/app/entities/portfolio/portfolio-detail.component';
import { PortfolioService } from '../../../../../../main/webapp/app/entities/portfolio/portfolio.service';
import { Portfolio } from '../../../../../../main/webapp/app/entities/portfolio/portfolio.model';

describe('Component Tests', () => {

    describe('Portfolio Management Detail Component', () => {
        let comp: PortfolioDetailComponent;
        let fixture: ComponentFixture<PortfolioDetailComponent>;
        let service: PortfolioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [StockTrackerTestModule],
                declarations: [PortfolioDetailComponent],
                providers: [
                    PortfolioService
                ]
            })
            .overrideTemplate(PortfolioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PortfolioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PortfolioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Portfolio(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.portfolio).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

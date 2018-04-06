/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { StockTrackerTestModule } from '../../../test.module';
import { PortfolioComponent } from '../../../../../../main/webapp/app/entities/portfolio/portfolio.component';
import { PortfolioService } from '../../../../../../main/webapp/app/entities/portfolio/portfolio.service';
import { Portfolio } from '../../../../../../main/webapp/app/entities/portfolio/portfolio.model';

describe('Component Tests', () => {

    describe('Portfolio Management Component', () => {
        let comp: PortfolioComponent;
        let fixture: ComponentFixture<PortfolioComponent>;
        let service: PortfolioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [StockTrackerTestModule],
                declarations: [PortfolioComponent],
                providers: [
                    PortfolioService
                ]
            })
            .overrideTemplate(PortfolioComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PortfolioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PortfolioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Portfolio(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.portfolios[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

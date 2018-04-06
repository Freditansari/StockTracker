import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Portfolio } from './portfolio.model';
import { PortfolioPopupService } from './portfolio-popup.service';
import { PortfolioService } from './portfolio.service';
import { User, UserService } from '../../shared';
import { StockList, StockListService } from '../stock-list';

@Component({
    selector: 'jhi-portfolio-dialog',
    templateUrl: './portfolio-dialog.component.html'
})
export class PortfolioDialogComponent implements OnInit {

    portfolio: Portfolio;
    isSaving: boolean;

    users: User[];

    stocklists: StockList[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private portfolioService: PortfolioService,
        private userService: UserService,
        private stockListService: StockListService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.stockListService.query()
            .subscribe((res: HttpResponse<StockList[]>) => { this.stocklists = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.portfolio.id !== undefined) {
            this.subscribeToSaveResponse(
                this.portfolioService.update(this.portfolio));
        } else {
            this.subscribeToSaveResponse(
                this.portfolioService.create(this.portfolio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Portfolio>>) {
        result.subscribe((res: HttpResponse<Portfolio>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Portfolio) {
        this.eventManager.broadcast({ name: 'portfolioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackStockListById(index: number, item: StockList) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-portfolio-popup',
    template: ''
})
export class PortfolioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private portfolioPopupService: PortfolioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.portfolioPopupService
                    .open(PortfolioDialogComponent as Component, params['id']);
            } else {
                this.portfolioPopupService
                    .open(PortfolioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

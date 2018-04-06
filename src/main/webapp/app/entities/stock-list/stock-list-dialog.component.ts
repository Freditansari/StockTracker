import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StockList } from './stock-list.model';
import { StockListPopupService } from './stock-list-popup.service';
import { StockListService } from './stock-list.service';

@Component({
    selector: 'jhi-stock-list-dialog',
    templateUrl: './stock-list-dialog.component.html'
})
export class StockListDialogComponent implements OnInit {

    stockList: StockList;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private stockListService: StockListService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stockList.id !== undefined) {
            this.subscribeToSaveResponse(
                this.stockListService.update(this.stockList));
        } else {
            this.subscribeToSaveResponse(
                this.stockListService.create(this.stockList));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<StockList>>) {
        result.subscribe((res: HttpResponse<StockList>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: StockList) {
        this.eventManager.broadcast({ name: 'stockListListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-stock-list-popup',
    template: ''
})
export class StockListPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stockListPopupService: StockListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.stockListPopupService
                    .open(StockListDialogComponent as Component, params['id']);
            } else {
                this.stockListPopupService
                    .open(StockListDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

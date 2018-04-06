import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StockList } from './stock-list.model';
import { StockListPopupService } from './stock-list-popup.service';
import { StockListService } from './stock-list.service';

@Component({
    selector: 'jhi-stock-list-delete-dialog',
    templateUrl: './stock-list-delete-dialog.component.html'
})
export class StockListDeleteDialogComponent {

    stockList: StockList;

    constructor(
        private stockListService: StockListService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stockListService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'stockListListModification',
                content: 'Deleted an stockList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stock-list-delete-popup',
    template: ''
})
export class StockListDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stockListPopupService: StockListPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.stockListPopupService
                .open(StockListDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { StockList } from './stock-list.model';
import { StockListService } from './stock-list.service';

@Component({
    selector: 'jhi-stock-list-detail',
    templateUrl: './stock-list-detail.component.html'
})
export class StockListDetailComponent implements OnInit, OnDestroy {

    stockList: StockList;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private stockListService: StockListService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStockLists();
    }

    load(id) {
        this.stockListService.find(id)
            .subscribe((stockListResponse: HttpResponse<StockList>) => {
                this.stockList = stockListResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStockLists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'stockListListModification',
            (response) => this.load(this.stockList.id)
        );
    }
}

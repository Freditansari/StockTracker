import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StockTrackerSharedModule } from '../../shared';
import {
    StockListService,
    StockListPopupService,
    StockListComponent,
    StockListDetailComponent,
    StockListDialogComponent,
    StockListPopupComponent,
    StockListDeletePopupComponent,
    StockListDeleteDialogComponent,
    stockListRoute,
    stockListPopupRoute,
} from './';

const ENTITY_STATES = [
    ...stockListRoute,
    ...stockListPopupRoute,
];

@NgModule({
    imports: [
        StockTrackerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StockListComponent,
        StockListDetailComponent,
        StockListDialogComponent,
        StockListDeleteDialogComponent,
        StockListPopupComponent,
        StockListDeletePopupComponent,
    ],
    entryComponents: [
        StockListComponent,
        StockListDialogComponent,
        StockListPopupComponent,
        StockListDeleteDialogComponent,
        StockListDeletePopupComponent,
    ],
    providers: [
        StockListService,
        StockListPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StockTrackerStockListModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { StockTrackerSharedModule } from '../../shared';
import { StockTrackerAdminModule } from '../../admin/admin.module';
import {
    PortfolioService,
    PortfolioPopupService,
    PortfolioComponent,
    PortfolioDetailComponent,
    PortfolioDialogComponent,
    PortfolioPopupComponent,
    PortfolioDeletePopupComponent,
    PortfolioDeleteDialogComponent,
    portfolioRoute,
    portfolioPopupRoute,
} from './';

const ENTITY_STATES = [
    ...portfolioRoute,
    ...portfolioPopupRoute,
];

@NgModule({
    imports: [
        StockTrackerSharedModule,
        StockTrackerAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PortfolioComponent,
        PortfolioDetailComponent,
        PortfolioDialogComponent,
        PortfolioDeleteDialogComponent,
        PortfolioPopupComponent,
        PortfolioDeletePopupComponent,
    ],
    entryComponents: [
        PortfolioComponent,
        PortfolioDialogComponent,
        PortfolioPopupComponent,
        PortfolioDeleteDialogComponent,
        PortfolioDeletePopupComponent,
    ],
    providers: [
        PortfolioService,
        PortfolioPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StockTrackerPortfolioModule {}

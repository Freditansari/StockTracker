import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { StockTrackerStockListModule } from './stock-list/stock-list.module';
import { StockTrackerPortfolioModule } from './portfolio/portfolio.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        StockTrackerStockListModule,
        StockTrackerPortfolioModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StockTrackerEntityModule {}
